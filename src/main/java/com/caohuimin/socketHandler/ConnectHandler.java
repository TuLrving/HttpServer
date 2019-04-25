package com.caohuimin.socketHandler;



import com.caohuimin.dispatcher.Dispatcher;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * 连接处理器，
 */
public class ConnectHandler{

    protected Socket socket;
    //Socket请求连接池
    protected static List<Socket> pool = new LinkedList<Socket>();


    public ConnectHandler()  {

    }

    @SuppressWarnings("unchecked")
    public void handleSocket() {
        //为每一个请求开启一个新的线程用于处理请求
        new Thread(new Dispatcher(socket)).start();
    }

    //将请求先存入请求连接池中
    public static void processRequest(Socket requestToHandle) {
        synchronized (pool) {
            pool.add(pool.size(), requestToHandle);
            //唤醒所有处理线程
            pool.notifyAll();
        }
    }

    public void run() {
        while (true) {
            synchronized (pool) {
                while (pool.isEmpty()) {
                    try {
                        //当连接池为空中，所有线程进入等待状态
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //FCFS方式处理请求
                socket = (Socket) pool.remove(0);
            }
            //处理请求
            handleSocket();
        }
    }
}
