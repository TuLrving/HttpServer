package com.caohuimin.servlet.imp;

import com.caohuimin.dispatcher.Request;
import com.caohuimin.dispatcher.Response;
import com.caohuimin.servlet.Servlet;

/**
 * 测试注册的Servlet
 */
public class RegistServlet implements Servlet {

    public void service(Request request, Response response) {
        response.println("<html><head><title>");
        response.println("客户端</title></head><body>" );
        response.println("欢迎").println(request.getParameter("username")).println("回来！" );
        response.println("</body></html>");
    }
}
