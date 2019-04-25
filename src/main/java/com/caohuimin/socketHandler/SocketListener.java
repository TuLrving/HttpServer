package com.caohuimin.socketHandler;

import java.io.IOException;
import java.util.Properties;

/**
 * 监听器，创建监听线程并读取最大并发数
 */
public class SocketListener {
    public void initialized(int port) throws IOException {
        //从配置文件中读取到最大并发数
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("listener.properties"));
        String maxConcent = properties.getProperty("maxConcent");
        //开始处理客户端请求
        new SocketThread(port,Integer.parseInt(maxConcent)).start();
    }
}
