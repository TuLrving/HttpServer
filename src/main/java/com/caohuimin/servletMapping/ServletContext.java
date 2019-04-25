package com.caohuimin.servletMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类用于储存xml文件中读取到的所有servlet映射和servlet-mapping映射
 */
public class ServletContext {
    private Map<String, String> servlet ;
    private Map<String, String> servletMapping;

    public ServletContext(){
        servlet = new HashMap<String, String>();
        servletMapping = new HashMap<String, String>();
    }

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getServletMapping() {
        return servletMapping;
    }

    public void setServletMapping(Map<String, String> servletMapping) {
        this.servletMapping = servletMapping;
    }
}
