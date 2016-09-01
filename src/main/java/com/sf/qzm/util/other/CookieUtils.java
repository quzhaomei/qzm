package com.sf.qzm.util.other;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	
	public static  String getCookie(HttpServletRequest request,String name){
		String result=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				if(cookies[i].getName().equals(name)){
					result=cookies[i].getValue();
					break;
				}
			}
		}
		return result;
	}
	
	public static void saveCookie(String key,String value,HttpServletResponse response,String path){
		Cookie cookie=new Cookie(key, value);//默认
		cookie.setMaxAge(30*24*60*60);
		if(path!=null){
			cookie.setPath(path);
		}else{
			cookie.setPath("/");
		}
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(String key,HttpServletResponse response,String path){
		Cookie cookie=new Cookie(key, null);//默认
		cookie.setMaxAge(0);
		if(path!=null){
			cookie.setPath(path);
		}
		response.addCookie(cookie);
	}
	
	public static void saveCookie(String key,String value,HttpServletResponse response){
		Cookie cookie=new Cookie(key, value);//默认
		cookie.setMaxAge(30*24*60*60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(String key,HttpServletResponse response){
		Cookie cookie=new Cookie(key, null);//默认
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
