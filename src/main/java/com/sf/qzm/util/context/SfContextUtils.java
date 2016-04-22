package com.sf.qzm.util.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 上下文工具类
 * @author Administrator
 *
 */
@Component
public class SfContextUtils implements ApplicationContextAware {
	private static  ApplicationContext context;
	
	public static <T> T getComponent(Class<T> clazz){
		return (T) context.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext contextParam) throws BeansException {
		context=contextParam;
	}
	public static String getWebXmlParam(HttpServletRequest request,String param){
		return request.getSession().getServletContext()
		.getInitParameter(param);
	}
	public static String getRootUrl(HttpServletRequest request){
		String host=request.getRequestURL().toString();
		while(host.lastIndexOf("/")>7){
			host=host.substring(0,host.lastIndexOf("/"));
		}
		String contextPath=request.getContextPath();
		if(!"".equals(contextPath)){
			host=host+contextPath;
		}
		return host;
	}
}
