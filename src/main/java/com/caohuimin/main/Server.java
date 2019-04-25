package com.caohuimin.main;

import com.caohuimin.socketHandler.SocketListener;

public class Server {
    public static void main(String[] args) {
        Server server = new Server();
        //开启服务器，监听端口号8888
        server.start(8888);
    }

    //启动指定端口号的服务器
    private void start(int port){
            this.receive(port);
    }

    //接收请求
    private void receive(int port){
        try {
            //开始监听客户端的请求
              new SocketListener().initialized(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //关闭服务器
    private void stop(){
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
