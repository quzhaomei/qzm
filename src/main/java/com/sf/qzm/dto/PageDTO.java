package com.sf.qzm.dto;

import java.io.Serializable;

/**
 * 分页业务对象 默认 第一页，每页十条
 * 
 * @author qzm
 *
 * @since 2015-5-14
 */
public class PageDTO<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 页容量
	 */
	private Integer pageSize = 10;
	/**
	 * 页码
	 */
	private Integer pageIndex = 1;// 页码
	/**
	 * 总页数
	 */
	private Integer totalPage;
	/**
	 * 总条数
	 */
	private Integer count;// 总条数
	/**
	 * 排序字段
	 */
	private String orderBy;
	
	private String direction;
	
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * 数据对象
	 */
	private T param;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public T getParam() {
		return param;
	}

	public void setParam(T param) {
		this.param = param;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	
}
