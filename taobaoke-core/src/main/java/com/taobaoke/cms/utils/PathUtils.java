package com.taobaoke.cms.utils;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class PathUtils {
    private static String END_URL_BCS = "http://bcs.duapp.com/outin-root/taose/";
    
    public static String generateBasePath(String fileName) {
        StringBuilder sb = new StringBuilder();
        Date date = new Date();
        sb.append(DateUtils.getYear(date)).append("/")
                .append(DateUtils.getMonth(date)).append("/")
                .append(DateUtils.getDay(date)).append("/")
                .append(DateUtils.getHour(date)).append("/")
                .append(DateUtils.getMinute(date)).append("/")
                .append(DateUtils.getSecond(date)).append("/")
                .append("%s").append(RandomStringUtil.getRadmonStr(10))
                ;
        int fileSuffixIndex = fileName.lastIndexOf('.');
        if( fileSuffixIndex != -1 ){
            sb.append(fileName.substring(fileSuffixIndex));
        }
        return sb.toString();
    }
    
    public static String getFileUrl(String path){
        if( StringUtils.isEmpty(path) ){
            return "";
        }
        return END_URL_BCS + path;
    }
	
}