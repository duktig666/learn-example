package cn.duktig.downbit.utils;

import java.io.File;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/7 16:12
 **/
public class FileUtils {

    /**
     * 获取文件内容长度
     *
     * @param name
     * @return
     */
    public static long getFileContentLength(String name) {
        File file = new File(name);
        return file.exists() && file.isFile() ? file.length() : 0;
    }

}

