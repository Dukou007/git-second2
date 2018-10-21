package com.example;

import java.io.IOException;
import java.io.InputStream;

/**
 * 封装请求对象
 */
public class MyRequest {

    private String url;
    private String method;

    public MyRequest(InputStream inputStream) throws IOException {
        String httpRequest = "";
        byte[] httpRequestBytes = new byte[1024];
        int length = inputStream.read(httpRequestBytes);

        if(length > 0) {
            httpRequest = new String(httpRequestBytes, 0, length);
        }

        // 对HTTP协议进行解析
        // 从请求头获取方法和url
        String httpHead = httpRequest.split("\n")[0];
        url = httpHead.split("\\s")[1];
        method = httpHead.split("\\s")[0];


        System.out.println("请求信息------>" + this.toString());
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
