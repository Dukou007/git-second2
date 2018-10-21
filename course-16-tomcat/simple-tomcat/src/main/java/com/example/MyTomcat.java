package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 关于项目的详细需求分析与说明看 READMA.md
 */
public class MyTomcat {

    private int port = 8080;

    //用于存放url与servlet的对应关系
    private Map<String, String> urlServletMap = new HashMap<String, String>();

    public MyTomcat(int port) {
        this.port = port;
    }

    public void start() {
        initServletMapping();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("myTomcat is start ...");

            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                MyRequest request = new MyRequest(inputStream);
                MyResponse response = new MyResponse(outputStream);

                dispatch(request, response);

                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化url与servlet
     */
    private void initServletMapping() {
        for (ServletMapping servletMapping : ServletMappingConfig.servletMappings) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
        }

    }

    /**
     * 调度servlet处理
     *
     * @param request
     * @param response
     */
    private void dispatch(MyRequest request, MyResponse response) {
        String clazz = urlServletMap.get(request.getUrl());

        //通过反射方式运行servlet代码
        try {
            Class<MyServlet> myServletClass = (Class<MyServlet>) Class.forName(clazz);
            MyServlet myServlet = myServletClass.newInstance();

            myServlet.service(request, response);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new MyTomcat(8080).start();
    }
}
