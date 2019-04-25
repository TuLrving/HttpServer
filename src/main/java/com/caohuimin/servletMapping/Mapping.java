package com.caohuimin.servletMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于存储servlet-mapping映射的实体类
 * 其中一个servletName可以对应多个urlPattern
 * <servlet-mapping>
 *     <servlet-name></servlet-name>
 *     <url-pattern></url-pattern>
 *     <url-pattern></url-pattern>
 * </servlet-mapping>
 */
public class Mapping {
    private String servletName;
    private List<String> urlPatterns;

    public Mapping(){
        servletName = "";
        urlPatterns = new ArrayList<String>();
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public List<String> getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(List<String> urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
