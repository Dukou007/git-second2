package com.example;

import java.io.IOException;

/**
 * 具体的servlet实现
 */
public class HelloWorldServlet extends MyServlet{

    public void doGet(MyRequest request, MyResponse response) {
        try {
            response.write("get --- this is hello world get servlet~~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(MyRequest request, MyResponse response) {
        try {
            response.write("post --- this is hello world post servlet~~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
