package com.example;

import java.util.ArrayList;
import java.util.List;

public class ServletMappingConfig {

    public static List<ServletMapping> servletMappings = new ArrayList<ServletMapping>();

    // 初始化servlet
    // 实际应该是扫码web.xml配置的servlet初始化
    // <servlet></servlet>和<servlet-mapping></servlet-mapping>来进行指定哪个URL交给哪个servlet进行处理
    static {
        servletMappings.add(new ServletMapping("helloWorld", "/hello", "com.example.HelloWorldServlet"));
    }
}
