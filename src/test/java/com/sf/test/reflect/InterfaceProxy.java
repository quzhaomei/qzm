package com.sf.test.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class InterfaceProxy implements InvocationHandler {
	private Object aim;
	public InterfaceProxy(Object aim) {
		super();
		this.aim = aim;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		doBefore();
		result = method.invoke(aim, args);
		System.out.println(method.getName());
		doAfter();
		return result;
	}

	private void doBefore() {
		System.out.println("do before");
	}

	private void doAfter() {
		System.out.println("do after");
	}

	public static Object factory(Object obj) {
		Class cls = obj.getClass();
		return Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), new InterfaceProxy(obj));
	}
	public static void main(String[] args) {
		char c='c';
		System.out.println((int)c);
	}
}
