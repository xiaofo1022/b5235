package com.xiaofo1022.b5235.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

  public static final String WX_USER_COOKIE_NAME = "_WXUID_";
  public static final String WX_USER_COOKIE_PATH = "/";
  
  public static void addCookie(String name, String value, HttpServletResponse response) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(Integer.MAX_VALUE);
    cookie.setPath(WX_USER_COOKIE_PATH);
    response.addCookie(cookie);
  }

  public static String getCookieValue(String name, HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : request.getCookies()) {
        if (name.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
  
  public static void showCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : request.getCookies()) {
        String name = cookie.getName();
        String value = cookie.getValue() == null ? "null" : cookie.getValue();
        String path = cookie.getPath() == null ? "null" : cookie.getPath();
        String domain = cookie.getDomain() == null ? "null" : cookie.getDomain();
        System.out.println("Cookie info[ name = " + name + ", value = " + value + ", path = " + path + ", domain = " + domain + ", maxAge = " + cookie.getMaxAge() + " ]");
      }
    }
  }
}
