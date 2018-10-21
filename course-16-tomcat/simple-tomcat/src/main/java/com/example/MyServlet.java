package com.example;

/**
 * 抽象Servlet的两种请求方式
 * 为tomcat提供servlet规范的容器处理
 */
public abstract class MyServlet {

    public abstract void doGet (MyRequest request, MyResponse response);

    public abstract void doPost (MyRequest request, MyResponse response);

    public void service (MyRequest request, MyResponse response) {

        if("POST".equalsIgnoreCase(request.getMethod())) {
            doPost(request, response);

        } else if("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        }
    }

}
