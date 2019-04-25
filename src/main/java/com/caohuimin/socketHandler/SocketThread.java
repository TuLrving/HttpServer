package com.caohuimin.socketHandler;


import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 真正的监听器，监听客户端请求并创建线程池用于处理客户端请求
 */
public class SocketThread extends Thread {
    //端口号
    private int port;
    //最大并发数
    private int maxConcent;

    public SocketThread(int aListenPort, int maxConcent) {
        this.port = aListenPort;
        this.maxConcent = maxConcent;
    }

    public void run() {
        //创建ServerSocket工厂
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        try {
            //通过工厂创建ServerSocket
            ServerSocket serverSocket = serverSocketFactory.createServerSocket(port);
            Socket request = null;
            System.out.println("++++++ ServerSocket已经启动 ++++++");
            //启动线程池
            this.setUpHandlers();
            while (true) {
                //监听端口，接收客户端请求
                request = serverSocket.accept();
                System.out.println("++++++ 客户请求成功 ++++++");
                // 收到请求后将请求存入连接池
                ConnectHandler.processRequest(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //处理请求的线程池的创建
    public void setUpHandlers() {
        ExecutorService executorService = Executors.newFixedThreadPool(maxConcent);
        for (int i = 0; i < maxConcent; i++) {
            final ConnectHandler currentHandler = new ConnectHandler();
            System.out.println("++++++ Thread-Handler" + i + "已启动 ++++++");
            executorService.execute(new Runnable() {
                public void run() {
                    currentHandler.run();
                }
            });
        }
    }
}