package com.sf.qzm.util.other;

import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtils {
	/**
	 * 判断字符串是否为空，包括null和"";
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 隐藏手机号码,中间四位
	 * @param str
	 * @return
	 */
	public static String hideMobile(String phone){
		if(!isEmpty(phone)){
			String start=phone.substring(0,3);
			String end=phone.substring(phone.length()-4,phone.length());
			return start+"****"+end;
			
		}
		return "";
	}
	
	
	public static String getFirstChar(String str){
		String result="";
		if(str!=null){
			for(char temp:str.toCharArray()){
				result+=PinyinHelper.toHanyuPinyinStringArray(temp)[0].toUpperCase().toCharArray()[0];
			}
		}
		return result;
	}
}
