package com.sf.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.sf.qzm.util.other.PasswordUtils;
import com.sf.test.reflect.Business;
import com.sf.test.reflect.BusinessImpl;

import sun.security.provider.MD5;

public class TestProxy implements InvocationHandler {
	private Object oringl;
	
	public TestProxy(Object oringl) {
		super();
		this.oringl = oringl;
	}
	public TestProxy() {
		super();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("代理执行前");
		Object result= method.invoke(oringl, args);
		System.out.println("代理执行后");
		return result;
	}
	public static void main(String[] args) {
		System.out.println(PasswordUtils.MD5("123456"));
		BusinessImpl orignl=new BusinessImpl();
		Business proxy=(Business) Proxy.newProxyInstance(Business.class.getClassLoader(), orignl.getClass().getInterfaces(),new TestProxy(orignl));
		proxy.doAction();
	}
}
