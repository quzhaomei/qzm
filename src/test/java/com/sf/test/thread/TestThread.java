package com.sf.test.thread;

public class TestThread {
	public static void main(String[] args) {
		final StringBuilder str=new StringBuilder("hello");
		
		Thread a=new Thread(){
			@Override
			public void run() {
				for(int i=0;i<10;i++){
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("准备完毕！");
				
				synchronized (str) {
					try {
						str.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int i=0;i<10;i++){
						str.append(" a");
						System.out.println("a 开始播放！--"+str);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		};
		
		Thread b=new Thread(){
			@Override
			public void run() {
				try {
					synchronized (str) {
					for(int i=0;i<10;i++){
						if(i==5){
							str.notify();
						}
						str.append(" b");
						System.out.println("b 开始执行！--"+str);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		};
		
		a.start();
		b.start();
	}
}
