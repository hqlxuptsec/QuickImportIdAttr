package com.hql.utils;

import com.google.gson.Gson;

public class ParseXmlMain {
    public static void main(String[] args) {

        System.out.println(new Gson()
                .toJson(ParseXmlUtil
                        .parseXmlFile("E://elog-wpm-core-app//app//src//main//res//layout//activity_check.xml")));
    }
}
