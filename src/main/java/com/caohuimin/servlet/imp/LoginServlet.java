package com.caohuimin.servlet.imp;

import com.caohuimin.dispatcher.Request;
import com.caohuimin.dispatcher.Response;
import com.caohuimin.servlet.Servlet;

/**
 * 测试登录的Servlet
 */
public class LoginServlet implements Servlet {

    public void service(Request request, Response response) {
        response.println("<html><head><title>");
        response.println("客户端</title></head><body>" );
        response.println(request.getParameter("username")).println("登录成功！" );
        response.println("</body></html>");
    }
}
