package cn.duktig.downbit.thread;

import cn.duktig.downbit.DownloadMain;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/7 16:11
 **/
public class LogThread implements Callable<Boolean> {

    public static AtomicLong LOCAL_FINISH_SIZE = new AtomicLong();
    public static AtomicLong DOWNLOAD_SIZE = new AtomicLong();
    public static AtomicLong DOWNLOAD_FINISH_THREAD = new AtomicLong();
    private long httpFileContentLength;

    public LogThread(long httpFileContentLength) {
        this.httpFileContentLength = httpFileContentLength;
    }

    @Override
    public Boolean call() throws Exception {
        int[] downSizeArr = new int[5];
        int i = 0;
        double size = 0;
        double mb = 1024d * 1024d;
        // 文件总大小
        String httpFileSize = String.format("%.2f", httpFileContentLength / mb);
        while (DOWNLOAD_FINISH_THREAD.get() != DownloadMain.DOWNLOAD_THREAD_NUM) {
            double downloadSize = DOWNLOAD_SIZE.get();
            downSizeArr[++ i % 5] = Double.valueOf(downloadSize - size).intValue();
            size = downloadSize;

            // 每秒速度
            double fiveSecDownloadSize = Arrays.stream(downSizeArr).sum();
            int speed = (int) ((fiveSecDownloadSize / 1024d) / (i < 5d ? i : 5d));

            // 剩余时间
            double surplusSize = httpFileContentLength - downloadSize - LOCAL_FINISH_SIZE.get();
            String surplusTime = String.format("%.1f", surplusSize / 1024d / speed);
            if ("Infinity".equals(surplusTime)) {
                surplusTime = "-";
            }

            // 已下大小
            String currentFileSize = String.format("%.2f", downloadSize / mb + LOCAL_FINISH_SIZE.get() / mb);
            String speedLog = String.format("> 已下载 %smb/%smb,速度 %skb/s,剩余时间 %ss", currentFileSize, httpFileSize,
                    speed, surplusTime);
            System.out.print("\r");
            System.out.print(speedLog);
            Thread.sleep(1000);
        }
        System.out.println();
        return true;
    }
}

