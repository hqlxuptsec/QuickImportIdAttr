package com.hql.utils;

import com.hql.entity.ElementBean;
import org.apache.http.util.TextUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParseXmlUtil {
    /**
     * 解析xml文件，将对应的标签id列表保存返回
     * @param fileDir       读对应的文件
     * @param encodeFormat          编码格式
     * @return                      xml id列表
     */
    private static List<ElementBean> parseXmlFile2(String fileDir, String encodeFormat) {
        //1.获取xml解析器
        XmlPullParserFactory xmlPullParserFactory;
        XmlPullParser xmlPullParser;
        List<ElementBean> elementBeans = new LinkedList<>();
        FileInputStream inputStream;
        try {

            System.out.println("begin");
            inputStream = new FileInputStream(fileDir);
            //获得一个XMLPULL工厂类的实例
            xmlPullParserFactory = XmlPullParserFactory.newInstance();
            System.out.println("xmlPullParserFactory:" + xmlPullParserFactory);
            if (xmlPullParserFactory == null) {
                return elementBeans;
            }
            //获得一个XML解析器的实例
            xmlPullParser = xmlPullParserFactory.newPullParser();
            //设置解析器的输入，使用inputStream流式数据。
            xmlPullParser.setInput(inputStream, encodeFormat);
            System.out.println("xmlPullParser:" + xmlPullParser);
            //判断当前的事件类型
            int eventType = xmlPullParser.getEventType();
            //循环读取，知道事件类型为文档结束
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = xmlPullParser.getName();
                if (!TextUtils.isEmpty(name))
                    if (eventType == XmlPullParser.START_TAG) {
                        String attributeValue = xmlPullParser.getAttributeValue(null, "android:id");
                        if (!TextUtils.isEmpty(attributeValue)) {
                            attributeValue = attributeValue.substring(attributeValue.indexOf("/") + 1);
                            elementBeans.add(new ElementBean(attributeValue, name));
                        }
                    }
                //读取下一个标签
                eventType = xmlPullParser.next();
            }
            inputStream.close();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return elementBeans;
    }

    /**
     * 解析xml文件
     * @param dir            文件
     * @param encodeFormat   编码格式
     * @return
     */
    private static List<ElementBean> parseXmlFile(String dir, String encodeFormat) {
        if (TextUtils.isEmpty(dir)) {
            return Collections.emptyList();
        }
        return parseXmlFile2(dir, encodeFormat);
    }

    /**
     * 解析xml文件 默认编码格式 utf-8
     * @param dir   文件
     * @return
     */
    public static List<ElementBean> parseXmlFile(String dir) {
        return parseXmlFile(dir, "utf-8");
    }
}
