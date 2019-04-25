package com.caohuimin.xmlToBeanHanlder;

import com.caohuimin.servlet.Servlet;
import com.caohuimin.servletMapping.Entity;
import com.caohuimin.servletMapping.Mapping;
import com.caohuimin.servletMapping.ServletContext;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import java.util.Map;

/**
 * 该类用于读取xml配置文件，将xml文件中的信息映射到ServletContext实体类中
 */
public class WebApp {
    private static ServletContext context ;
    static {
        context = new ServletContext();
        //创建Servlet-mapping映射
        Map<String, String> mappings = context.getServletMapping();
        //创建Servlet映射
        Map<String, String> servlets = context.getServlet();

        try {
            //1.创建解析工厂
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            //2.创建解析器
            SAXParser parser = saxParserFactory.newSAXParser();
            //3.注册解析器并读取xml文件流
            ParserHandler parserHandler = new ParserHandler();
            parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),parserHandler);

            //添加servlets
            for(Entity entity:parserHandler.getEntityList()){
                servlets.put(entity.getServletName(),entity.getServletClass());
            }
            //添加mappings
            for(Mapping mapping:parserHandler.getMappingList()){
                List<String> urls = mapping.getUrlPatterns();
                for(String url:urls){
                    mappings.put(url,mapping.getServletName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //根据全限定类名反射创建相应的类
    public static Servlet getServlet(String url) throws Exception{
        String servletPath = context.getServlet().get(context.getServletMapping().get(url));
        if(url != null && !url.trim().equals("") && servletPath != null && !servletPath.trim().equals("")) {
            return (Servlet) Class.forName(servletPath).newInstance();
        }
        return null;
    }
}
