package com.caohuimin.xmlToBeanHanlder;

import com.caohuimin.servletMapping.Entity;
import com.caohuimin.servletMapping.Mapping;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析器，用于解析xml文件，得到相应的配置信息
 */
public class ParserHandler extends DefaultHandler {
    private List<Mapping> mappingList ;//存储Mapping信息
    private List<Entity> entityList;//存储Entity信息
    private Entity entity;//将servlet封装到entity实体类中
    private Mapping mapping;//将servlet-mapping封装到mapping实体类中
    private boolean isMap;
    private String beginTag;//用于标记每一次读取到的标签

    //开始解析，可用于初始化相应的信息
    @Override
    public void startDocument() throws SAXException {
        mappingList = new ArrayList<Mapping>();
        entityList = new ArrayList<Entity>();
        entity = null;
        mapping = null;
        isMap = false;
        beginTag = "";
    }

    //开始标签
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName != null && !qName.trim().equals("")){
            beginTag = qName;
            if(beginTag.equals("servlet")){
                isMap = false;
                entity = new Entity();
            }else if(beginTag.equals("servlet-mapping")){
                isMap = true;
                mapping = new Mapping();
            }
        }
    }

    //标签中的内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(beginTag != null){
            String param = new String(ch,start,length);
            if(beginTag.equals("url-pattern")){
                mapping.getUrlPatterns().add(param);
            }
            if(!isMap){
                if(beginTag.equals("servlet-name")){
                    entity.setServletName(param);
                }else if(beginTag.equals("servlet-class")){
                    entity.setServletClass(param);
                }
            }else {
                if(beginTag.equals("servlet-name")) {
                    mapping.setServletName(param);
                }else if(beginTag.equals("url-pattern")){
                    mapping.getUrlPatterns().add(param);
                }
            }
        }
    }

    //结束标签
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName != null){
            if(qName.equals("servlet")){
                entityList.add(entity);
            }else if(qName.equals("servlet-mapping")){
                mappingList.add(mapping);
            }
        }
        beginTag = "";
        isMap = false;
    }

    //文档读取结束
    @Override
    public void endDocument() throws SAXException {
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

}
