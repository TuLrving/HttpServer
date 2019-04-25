package com.caohuimin.servletMapping;

/**
 * 该类用于存储Servlet映射的实体类
 * <servlet>
 *     <servlet-name></servlet-name>
 *     <servlet-class></servlet-class>
 * </servlet>
 */
public class Entity {
    private String servletName;
    private String servletClass;

    public Entity(){
        servletName = "";
        servletClass = "";
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getServletClass() {
        return servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }
}
