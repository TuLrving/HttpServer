package com.caohuimin.servlet.imp;

import com.caohuimin.dispatcher.Request;
import com.caohuimin.dispatcher.Response;
import com.caohuimin.servlet.Servlet;

public class TestServlet implements Servlet {

    /**
     * 控制器，向前端发出响应
     * @param request
     * @param response
     */
    public void service(Request request, Response response) {
        response.println("<html><head><title>");
        response.println("客户端</title></head><body>" );
        response.println("Hello").println(request.getParameter("username")).println("back！" );
        response.println("</body></html>");
    }
}
