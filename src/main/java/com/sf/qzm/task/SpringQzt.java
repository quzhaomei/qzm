package com.sf.qzm.task;

import org.springframework.stereotype.Component;

@Component(value="springQzt")
public class SpringQzt {
	private int count=0;
	public void execute(){
		count++;
		System.out.println("定时任务执行中！当前第"+count+"次……");
	}
}
