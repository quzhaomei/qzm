package com.sf.qzm.util.page;

import java.util.List;

import com.sf.qzm.bean.SfBean;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.SfDto;

/**
 * 分页工具类
 * 
 * @author quzhaomei
 *
 */
public class PageUtils {
	/**
	 * 封装返回的数据，
	 * @param data 数据库查询的对象集合
	 * @param count 总共条数
	 * @param page 原来的参数
	 * @return
	 */
	public static <T extends SfDto> PageDTO<List<T>>
	parseFrom(List<T> data, int count, PageDTO<? extends SfBean> page) {
		PageDTO<List<T>> pageDate = new PageDTO<List<T>>();
		pageDate.setParam(data);
		pageDate.setPageIndex(page.getPageIndex());
		pageDate.setPageSize(page.getPageSize());
		int total = count % page.getPageSize() == 0 ? count / page.getPageSize() : count / page.getPageSize() + 1;
		pageDate.setTotalPage(total);
		pageDate.setCount(count);
		return pageDate;
	}
}
