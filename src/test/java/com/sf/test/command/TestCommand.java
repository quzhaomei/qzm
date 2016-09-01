package com.sf.test.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestCommand {
	
	
	public static void main(String[] args) {
		Runtime runtime=Runtime.getRuntime(); 
		String command="/usr/local/mysql/bin/mysqldump -uroot -p19911212 talk -r /Users/quzhaomei/Desktop/dasd/as.sql";
		String command_1="";
		try {
//			Process p =runtime.exec(new String[]{"csh","-c","cat /Users/quzhaomei/Desktop/dasd/hello.txt"});
			Process p =runtime.exec(new String[]{"csh","-c",command});//unlix调用
			InputStream is=p.getInputStream();
			InputStreamReader re=new InputStreamReader(is);
			BufferedReader red=new BufferedReader(re);
			System.out.println(red.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
