package com.caohuimin.dispatcher;

import com.caohuimin.xmlToBeanHanlder.WebApp;
import com.caohuimin.servlet.Servlet;

import java.io.IOException;
import java.net.Socket;

/**
 * 请求处理器，用于处理请求
 */
public class Dispatcher implements Runnable{
    private Response response;//响应体
    private Request request;//请求体
    private Socket client;
    private int code = 200;//默认状态码为200

    public Dispatcher(Socket client){
        try {
            this.client = client;
            this.request = new Request(client.getInputStream());
            this.response = new Response(client.getOutputStream());
        } catch (IOException e) {
            this.code = 500;
            return;
        }
    }

    public void run() {
        try {
            //通过请求路径得到相应的Servlet对象
            Servlet servlet = WebApp.getServlet(request.getUrl());
            if(servlet == null){
                this.code = 404;
                return;
            }else{
                //请求路径正确且服务器处理未发生异常，向客户端发送响应
                servlet.service(request,response);
                response.pushToClient(code);
                //释放资源
                close();
            }
        } catch (Exception e) {
            this.code = 500;
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
