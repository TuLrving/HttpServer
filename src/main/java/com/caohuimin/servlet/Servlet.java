package com.caohuimin.servlet;

import com.caohuimin.dispatcher.Request;
import com.caohuimin.dispatcher.Response;

/**
 * 定义Servlet的规范,所有的Servlet必须实现该接口
 */
public interface Servlet {
    /**
     * 服务方法
     * @param request
     * @param response
     */
    void service(Request request, Response response);
}
