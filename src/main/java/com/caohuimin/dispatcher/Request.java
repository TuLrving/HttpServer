package com.caohuimin.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装请求体并分析
 */
public class Request {

    private static final String CRLF = "\r\n";//换行

    //获得请求方式
    private String method;
    //获得请求资源的路径
    private String url;
    //获得请求参数
    private String parameterInfo;

    //获得整个请求体
    private String requestInfo;

    //将请求参数封装到map中
    private Map<String, List<String>> map;

    //输入流
    private InputStream inputStream;

    private Request(){
        this.method = "";
        this.url = "";
        this.parameterInfo = "";
        this.map = new HashMap<String,List<String>>();
    }

    public Request(InputStream inputStream){
        this();
        try {
            this.inputStream = inputStream;
            //读取请求体
            byte[] bytes = new byte[20480];
            int len = inputStream.read(bytes);
            if(len > 0){
                requestInfo = new String(bytes, 0, len);
            }else{
                return;
            }
            //分析请求体
            parseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //分析请求体
    public void parseMessage(){
        //第一行,从第一个字符到第一次换行
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
        //第一个斜杠的位置
        int i = firstLine.indexOf("/");
        //第一个http的位置
        int j = firstLine.indexOf("HTTP/");
        //请求资源路径和请求参数
        String param = firstLine.substring(i,j).trim();
        //获得请求的方法
        method = firstLine.substring(0, i).trim();
        //根据不同的方法获得请求资源路径和参数
        if(method.equalsIgnoreCase("post")){
            url = param;
            parameterInfo = requestInfo.substring(requestInfo.lastIndexOf(CRLF));
        }else if(method.equalsIgnoreCase("get")){
            String[] params = new String[2];
            System.out.println(param);
            if(param.contains("?")){
                params[0] = param.split("\\?")[0];
                params[1] = param.split("\\?")[1];
                url = params[0];
                if(params[1] != null && !params[1].trim().equals("") ){
                    parameterInfo = params[1];
                }
            }
        }

        //将请求参数封装到map中
        pushToMap(parameterInfo);
    }

    //将请求参数封装到map中
    private void pushToMap(String parameterInfo){
        if(parameterInfo == null || parameterInfo.trim().equals("")){
            return;
        }

        String[] strings = parameterInfo.split("&");
        for(int i = 0;i < strings.length;i++){
            //用于接收的字符串数组
            String[] strings1 = new String[2];
            //用于接收分割的字符串数组
            String[] strings2 = strings[i].split("=");
            strings1[0] = strings2[0];
            if(strings2.length == 2){
                //属性已经传值
                strings1[1] = strings2[1];
            }
            //如果是多个属性
            if(map.containsKey(strings1[0])){
                map.get(strings1[0]).add(strings1[1]);
            }else{
                ArrayList<String> list = new ArrayList<String>();
                list.add(decode(strings1[1],"gbk"));
                map.put(strings1[0],list);
            }
        }
    }

    //获得多个参数
    public String[] getParameters(String key){
        List<String> values = null;
        if((values=map.get(key)) == null){
            return null;
        }else{
            return (String[]) values.toArray();
        }
    }

    //获得一个参数
    public String getParameter(String key){
        List<String> values = null;
        if((values=map.get(key)) == null){
            return null;
        }else{
            return values.get(0);
        }
    }

    //解码,避免产生中文乱码现象
    private String decode(String value,String code){
        try {
            return URLDecoder.decode(value,code);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
