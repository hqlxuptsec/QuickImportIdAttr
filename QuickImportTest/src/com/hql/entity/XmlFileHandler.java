package com.hql.entity;

import org.apache.http.util.TextUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * 比对解析Layout文件
 */
public class XmlFileHandler extends DefaultHandler {
    private List<ElementBean> elementBeanList = new ArrayList<>();

    public List<ElementBean> getElementBeanList() {
        return elementBeanList;
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) {
        handleAdapterView(qualifiedName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        //
    }

    /**
     * @param qualifiedName      eg:控件名
     * @param attributes
     */
    private void handleAdapterView(String qualifiedName, Attributes attributes) {
        String attributesIdValue = attributes.getValue("android:id");
        if (!TextUtils.isEmpty(attributesIdValue)) {
            attributesIdValue = attributesIdValue.substring(attributesIdValue.indexOf("/") + 1);
            elementBeanList.add(new ElementBean(attributesIdValue, qualifiedName));
        }
    }
}
