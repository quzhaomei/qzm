package com.sf.qzm.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import com.sf.qzm.service.SystemSourceService;

/**
 * AOP编程，试用于日志，还有缓存
 * @author qzm
 * @since 2015-5-13
 */
//@Component
//@Aspect
public class LogAspect {
	protected Logger logger = Logger.getLogger(this.getClass());

	//切面配置
	@Pointcut("execution(* com.sf.qzm.service.*.*(..))")
	public void methodCachePointcut() {
	}

	@Around("methodCachePointcut()")//环绕通知
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object result=point.proceed();//如果没有缓存拦截。则直接运行
		//如果聊天端口被更改，则重置
		if(point.getThis() instanceof SystemSourceService){
		}
		return result;
	}
	
}
