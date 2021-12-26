package com.epidemic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnUtil {

    //      用java代码发送一个请求   接收并返回请求的相应信息
    public static String doGet(String urlStr) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        System.out.println("java代码发送请求");
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

//            设置连接超时时间      15s
            conn.setConnectTimeout(15000);

//            设置读取超时时间         60s
            conn.setReadTimeout(60000);

//              请求参数
            conn.setRequestProperty("Accept", "application/json");

//            请求发送
            conn.connect();

            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (Exception e) {
            System.out.println("捕获异常--- 数据没拿到");
//            e.printStackTrace();
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
//          关闭连接
            conn.disconnect();
        }
        return builder.toString();
    }
}
