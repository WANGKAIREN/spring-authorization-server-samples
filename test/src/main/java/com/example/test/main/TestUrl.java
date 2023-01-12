package com.example.test.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WANGKairen
 * @since 2023-01-09 17:35:53
 **/
public class TestUrl {

    public static void main(String[] args) throws MalformedURLException {

        String url = "http://admin.pre.btone.cn:8501/iot/admin/order/make-card";

        /*
        1. 例 url = http://admin.pre.btone.cn:8501/iot/admin/order/make-card
        2. url == null 则 配置文件 可配置默认 url
        3. 通道 new URL() 格式化 url 去掉 http:// 为 admin.pre.btone.cn/iot/admin/order/make-card
        4. 取数据库
        5. 取数据库如果为空则 while 循环 取数据库
            admin.pre.btone.cn/iot/admin/order/make-card
            admin.pre.btone.cn/iot/admin/order
            admin.pre.btone.cn/iot/admin
            admin.pre.btone.cn/iot
            admin.pre.btone.cn
         6. 取 /iot/admin/order/make-card
            http://admin.pre.btone.cn:8501/iot/admin?redirectUrl=/admin/order/make-card
         */

        URL urlObj = new URL(url);
        String urlStr = urlObj.getHost() + urlObj.getPath();
        System.out.println(urlStr);

        while (urlStr.contains("/")) {
            urlStr = urlStr.substring(0, urlStr.lastIndexOf("/"));
            System.out.println(urlStr);
        }

        Pattern pattern = Pattern.compile("/(?<=/).+?(?=/)");

        String path = urlObj.getPath();
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            // find 一次算是找一次匹配内容 /iot /admin /order
            System.out.println(matcher.group(0)); // /iot
            path = path.replace(matcher.group(0), "");
            System.out.println(path);
        }else {
            path = "";
        }
        System.out.println(path);

        //indexPageConfig.setSsoUrl(indexPageConfig.getSsoUrl() + "?redirectUrl=" + path);
        //indexPageConfig.setIndexUrl(indexPageConfig.getIndexUrl() + "?redirectUrl=" + path);

    }
}
