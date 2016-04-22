package com.sf.qzm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 后台权限拦截拦截标签 🏷
 * 以Method为单位
 * @author qzm
 * @since 2015-9-2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLimit {
	/**
	 * 登录拦截
	 * @return 是否拦截
	 */
	boolean adminLogin() default true;
	/**
	 * uri 权限拦截  ../user/info.html
	 */
	boolean uri() default false;
}
