package com.sf.test.email;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;

public class TestEmail {
	private final String host="smtp.sina.com";
	private final String authorName="veraqu@sina.cn";
	private final String password="qzm19911212";
	//测试简单邮件
	@Test
	public void testSimpleEmail()  {
		SimpleEmail email=new SimpleEmail();
		email.setHostName(host);
		email.setAuthentication(authorName, password);
		try {
			email.addTo("veraqu@sina.cn");
			email.setFrom(authorName,"这是新浪微博");
			email.setSubject("测试邮件");
			email.setMsg("这是一封邮件，发的出去吗？");
			email.setCharset("utf-8");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	//测试html格式的邮件
	@Test
	public void testHtmlEmail(){
		HtmlEmail email=new HtmlEmail();
		email.setHostName(host);
		email.setAuthentication(authorName, password);
		email.setCharset("utf-8");
		try {
			email.addTo("veraqu@sina.cn","柚子");
			email.setFrom("veraqu@sina.cn","瞿大大");
			email.setSubject("测试测试");
			File file=new File("/Users/quzhaomei/Desktop/cess.jpg");
			email.setHtmlMsg(""
					+ "<h1>sdsadsd</h1>"
					+"<ul>"
					+ "<li>dsd</li>"
					+ "<li>ccc</li>"
					+ "</ul>"
					+ "");
			 email.embed(file, "cesss.jpg");
			 email.send();
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
		
	}
	
	//测试有附件的邮件
	public void testAttachEamil(){
		
	}
	public static void main(String[] args) {
		String a="1540";
		String b="546";
		char[] arrs=a.toCharArray();
		char[] brrs=b.toCharArray();
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<arrs.length||i<brrs.length;i++){
			int temp=0;
			if(i<arrs.length){
				temp+=Integer.parseInt(arrs[i]+"");
			}
			if(i<brrs.length){
				temp+=Integer.parseInt(brrs[i]+"");
			}
			list.add(temp);
		}
		String result="";
		int up=0;
		for(int last=list.size()-1;last>=0;last--){
			int temp=list.get(last);
			if(up>0){
				temp++;
			}
			if(temp>=10){
				up=1;
			}else{
				up=0;
			}
			result+=temp%10;
		}
		String info="";
		for(int last=result.toCharArray().length-1;last>=0;last--){
			info+=result.toCharArray()[last];
		}
		System.out.println(info);
	}
	
}
