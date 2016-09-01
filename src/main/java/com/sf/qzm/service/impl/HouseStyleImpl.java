package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.constant.HouseStyle;
import com.sf.qzm.dao.HouseStyleDao;
import com.sf.qzm.service.HouseStyleService;

@Transactional
@Service
public class HouseStyleImpl implements HouseStyleService {

	@Resource
	private HouseStyleDao styleDao;
	@Override
	public void saveOrUpdate(HouseStyle houseStyle) {
		if(houseStyle.getStyleId()==null){
			styleDao.save(houseStyle);
		}else{
			styleDao.update(houseStyle);
		}
	}

	@Override
	public HouseStyle getById(Integer styleId) {
		if(styleId==null){return null;}
		HouseStyle param=new HouseStyle();
		param.setStyleId(styleId);
		return styleDao.get(param);
	}

	@Override
	public List<HouseStyle> all() {
		return styleDao.list(new HouseStyle());
	}

	@Override
	public void delete(Integer styleId) {
		if(styleId==null){return;}
		HouseStyle param=new HouseStyle();
		param.setStyleId(styleId);
		param.setIsDelete(1);
		styleDao.update(param);
	}

}
