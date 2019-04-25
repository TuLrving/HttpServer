package com.caohuimin.dispatcher;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * 封装响应体
 */
public class Response {

    private static final String CRLF = "\r\n";//换行
    private static final String BLANK = " ";//空格

    //保存头信息
    private StringBuilder headInfo;

    //保存响应体
    private StringBuilder content;

    //创建高效输出流
    private BufferedWriter bufferedWriter;

    //响应正文长度
    private int len;

    private OutputStream outputStream;
    public Response(OutputStream outputStream){
        this.outputStream = outputStream;
        this.headInfo = new StringBuilder();
        this.content = new StringBuilder();
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        this.len = 0;
    }


    //创建响应头
    private void createHeadInfo(int code){
        //http协议版本、状态码、描述
        headInfo.append("HTTP/1.1").append(BLANK).append(200).append(BLANK);
        switch(code){
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("Not Found");
                break;
            case 500:
                headInfo.append("Server Error");
                break;
        }
        headInfo.append(CRLF);
        //响应头
        headInfo.append("Server:LrvingTc").append(BLANK).append("Tu/1.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }

    //创建响应体,不带空格
    public Response print(String msg){
        try {
            content.append(msg);
            len += content.toString().getBytes().length;
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //创建响应体,带空格
    public Response println(String msg){
        content.append(msg);
        content.append(CRLF);
        len += (CRLF+msg).getBytes().length;
        return this;
    }

    //将响应发送给客户端
    public void pushToClient(int code){
        try {
            if(headInfo == null){
                code = 500;
            }
            //创建响应体
            createHeadInfo(code);
            bufferedWriter.append(headInfo);
            bufferedWriter.append(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            close();
        }
    }

    //关闭
    public void close(){
        if(bufferedWriter != null){
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
