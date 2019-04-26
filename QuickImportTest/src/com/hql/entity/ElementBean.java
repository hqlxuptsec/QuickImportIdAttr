package com.hql.entity;

public class ElementBean {
    private String id;

    private String viewName;

    private String attrsName;

    private boolean isSelect = true;

    public ElementBean(String id, String viewName) {
        this.id = id;
        this.viewName = viewName;
        attrsName = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getAttrsName() {
        return attrsName;
    }

    public void setAttrsName(String attrsName) {
        this.attrsName = attrsName;
    }
}
