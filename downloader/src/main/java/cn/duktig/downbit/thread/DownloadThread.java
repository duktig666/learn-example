package cn.duktig.downbit.thread;

import cn.duktig.downbit.DownloadMain;
import cn.duktig.downbit.utils.FileUtils;
import cn.duktig.downbit.utils.HttpUtls;
import cn.duktig.downbit.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/7 16:11
 **/
public class DownloadThread implements Callable<Boolean> {

    /**
     * 每次读取的数据块大小
     */
    private static final int BYTE_SIZE = 1024 * 1000;
    /**
     * 下载链接
     */
    private String url;
    /**
     * 下载开始位置
     */
    private long startPos;
    /**
     * 要下载的文件区块大小
     */
    private Long endPos;
    /**
     * 标识多线程下载切分的第几部分
     */
    private Integer part;
    /**
     * 文件总大小
     */
    private Long contentLength;

    public DownloadThread(String url, long startPos, Long endPos, Integer part, Long contentLength) {
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.part = part;
        this.contentLength = contentLength;
    }

    @Override
    public Boolean call() throws Exception {
        if (url == null || "".equals(url.trim())) {
            throw new RuntimeException("下载路径不正确");
        }
        // 文件名
        String httpFileName = HttpUtls.getHttpFileName(url);
        if (part != null) {
            httpFileName = httpFileName + DownloadMain.FILE_TEMP_SUFFIX + part;
        }

        // 本地文件大小
        long localFileContentLength = FileUtils.getFileContentLength(httpFileName);
        LogThread.LOCAL_FINISH_SIZE.addAndGet(localFileContentLength);
        if (localFileContentLength >= endPos - startPos) {
            LogUtils.info("{} 已经下载完毕，无需重复下载", httpFileName);
            LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
            return true;
        }
        if (endPos.equals(contentLength)) {
            endPos = null;
        }

        HttpURLConnection httpUrlConnection = HttpUtls.getHttpUrlConnection(url, startPos + localFileContentLength,
                endPos);
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis =
                new BufferedInputStream(input);
             RandomAccessFile oSavedFile = new RandomAccessFile(httpFileName, "rw")) {
            oSavedFile.seek(localFileContentLength);
            byte[] buffer = new byte[BYTE_SIZE];
            int len = - 1;
            // 读到文件末尾则返回-1
            while ((len = bis.read(buffer)) != - 1) {
                oSavedFile.write(buffer, 0, len);
                LogThread.DOWNLOAD_SIZE.addAndGet(len);
            }
        } catch (FileNotFoundException e) {
            LogUtils.error("ERROR! 要下载的文件路径不存在 {} ", url);
            return false;
        } catch (Exception e) {
            LogUtils.error("下载出现异常");
            e.printStackTrace();
            return false;
        } finally {
            httpUrlConnection.disconnect();
            LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
        }
        return true;
    }


}

