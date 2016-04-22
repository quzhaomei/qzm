package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.dto.PageDTO;

/**
 * 负责注入dao，以及定义模板方法
 *
 */
public interface  BaseService<Bean,DTO> {
	/**
	 * 保存或更新
	 * @param bean
	 * @throws Exception
	 */
	 void saveOrUpdate(Bean bean) throws Exception;
	 /**
	  * 删除.-不建议使用
	  * @param bean
	  */
	 @Deprecated
	 void delete(Bean bean);
	 /**
	  * 根据id获取对象
	  * @param id
	  * @return
	  */
	 DTO getById(Integer id);
	 /**
	  * 根据参数获取相应对象
	  * @param bean
	  * @return
	  */
	 DTO getByParam(Bean bean);
	 /**
	  * 根据参数获取相应对象数组
	  * @param bean
	  * @return
	  */
	 List<DTO> getListByParam(Bean bean);
	 /**
	  * 根据分页对象bean 获取分页数据dto
	  * @param bean
	  * @return
	  */
	 PageDTO<List<DTO>> getPageByParam(PageDTO<Bean> bean);
}
