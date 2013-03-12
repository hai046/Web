package com.taobaoke.www.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookieUtils {

    public static final String COOKIE_NAME = "c_id";
    public static final String COOKIE_PATH = "/taose/c_id/";

    private CookieUtils() {
    }

    public static Cookie generateConnectIdCookie(String connectId) {
        Cookie cookie = new Cookie(COOKIE_NAME, connectId);
        cookie.setMaxAge(60 * 60 * 24 * 30);
//        cookie.setPath(COOKIE_PATH);
        return cookie;
    }
    
    public static Cookie getConnectIdCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if( cookies == null || cookies.length < 1 ){
            return null;
        }
        for( Cookie c : cookies ){
            if( COOKIE_NAME.equals(c.getName()) ){
                System.out.println( c.getPath() );
                return c;
            }
        }
        return null;
    }
}