package com.sf.qzm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 后台菜单注册标签 🏷
 * 以Method,Bean为单位
 * @author qzm
 * @since 2016-5-10
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuTag {
	/**
	 * 菜单名字
	 * @return 链接名字
	 */
	String name();
	/**
	 * 父菜单code
	 * 当在method上时。默认是bean的code
	 * @return
	 */
	String parentCode() default "";
	/**
	 * 菜单特殊标志，唯一
	 * @return
	 */
	String code();//菜单编号
	
	/**
	 * 菜单类型 
	 * 0-菜单组（默认在bean）
	 * 1-页面。
	 * 2-操作
	 * @return
	 */
	int type();
	
	/**
	 * 菜单展示的顺序
	 * @return
	 */
	int sequence();//顺序
	
	/**
	 * 菜单icon
	 * @return
	 */
	String icon() default "";
}
