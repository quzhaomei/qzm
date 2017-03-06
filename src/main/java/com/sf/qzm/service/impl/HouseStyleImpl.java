package com.sf.qzm.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.biz.CompanyType;
import com.sf.qzm.bean.constant.CompanyToStyle;
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
			if(houseStyle.getDefaultSet()!=null&&houseStyle.getDefaultSet().size()>0){//保存默认设置
				List<CompanyToStyle> lists=houseStyle.getDefaultSet();
				for(CompanyToStyle temp:lists){
					temp.setCreateDate(new Date());
					temp.setStyleId(houseStyle.getStyleId());
				}
				styleDao.savePrice(lists);
			}
			
			if(houseStyle.getCompanySet()!=null&&houseStyle.getCompanySet().size()>0){//保存默认设置
				List<CompanyToStyle> lists=houseStyle.getCompanySet();
				for(CompanyToStyle temp:lists){
					temp.setCreateDate(new Date());
					temp.setStyleId(houseStyle.getStyleId());
				}
				styleDao.savePrice(lists);
			}
		}else{
			if(houseStyle.getDefaultSet()!=null&&houseStyle.getDefaultSet().size()>0){//保存默认设置
				styleDao.clearPrice(houseStyle.getStyleId(),null);
				List<CompanyToStyle> lists=houseStyle.getDefaultSet();
				for(CompanyToStyle temp:lists){
					temp.setCreateDate(new Date());
					temp.setStyleId(houseStyle.getStyleId());
				}
				styleDao.savePrice(lists);
			}
			
			if(houseStyle.getCompanySet()!=null&&houseStyle.getCompanySet().size()>0){//保存默认设置
				styleDao.clearPrice(houseStyle.getStyleId(),houseStyle.getCompanySet().get(0).getCompanyId());
				List<CompanyToStyle> lists=houseStyle.getCompanySet();
				for(CompanyToStyle temp:lists){
					temp.setCreateDate(new Date());
					temp.setStyleId(houseStyle.getStyleId());
				}
				styleDao.savePrice(lists);
			}
			
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

	@Override
	public List<Map<String, Object>> getDefaultPrice(List<CompanyType> types) {
		if(types==null||types.size()==0){return null;}
		return styleDao.getDefaultPrice(types);
	}

	@Override
	public List<Map<String, Object>> getCompanyPriceByStyle(List<CompanyType> types, Integer styleId) {
		if(types==null||types.size()==0){return null;}
		return styleDao.getCompanyPriceByStyle(types,styleId);
	}

}
