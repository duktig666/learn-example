package cn.duktig.downbit.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * description:
 *
 * @author RenShiWei
 * Date: 2021/8/7 16:13
 **/
public class ThunderUtils {

    private static String THUNDER = "thunder://";

    /**
     * 判断是否是迅雷链接
     *
     * @param url
     * @return
     */
    public static boolean isThunderLink(String url) {
        return url.startsWith(THUNDER);
    }

    /**
     * 转换成 HTTP URL
     *
     * @param url
     * @return
     */
    public static String toHttpUrl(String url) {
        if (! isThunderLink(url)) {
            return url;
        }
        LogUtils.info("当前链接是迅雷链接，开始转换...");
        url = url.replaceFirst(THUNDER, "");
        try {
            // base 64 转换
            url = new String(Base64.getDecoder().decode(url.getBytes()), "UTF-8");
            // url 解码
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 去头去尾
        if (url.startsWith("AA")) {
            url = url.substring(2);
        }
        if (url.endsWith("ZZ")) {
            url = url.substring(0, url.length() - 2);
        }
        return url;
    }

}

