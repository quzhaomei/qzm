package com.sf.qzm.util.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sf.qzm.service.SystemSourceService;

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

	
	/**
	 * 例如 http://www.exasd.cn/{contentPath}/log.html. 
	 * 最后处理返回 <p>http://www.exasd.cn/{contentPath}</p>
	 * <b>contentPath</b> 为当前项目的上下文路径，没有则为空格
	 * <br/>
	 * @param request 当前请求的request
	 * @return http 请求的根路径
	 */
	public static String getContentRootUrl(HttpServletRequest request){
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
	
	/**
	 * 获取网站的根目录
	 * @param request
	 * @return
	 */
	public static String getServerRootUrl(HttpServletRequest request){
		String host=request.getRequestURL().toString();
		while(host.lastIndexOf("/")>7){
			host=host.substring(0,host.lastIndexOf("/"));
		}
		return host;
	}
	
	/**
	 * 获取系统资源
	 * @param key
	 * @return
	 */
	public static String getSystemSourceByKey(String key){
		return getComponent(SystemSourceService.class).getByKey(key);
	}
}
