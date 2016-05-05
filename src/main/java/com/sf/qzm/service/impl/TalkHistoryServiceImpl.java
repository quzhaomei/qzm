package com.sf.qzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.dao.TalkHistoryDao;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.bis.HistoryCountDTO;
import com.sf.qzm.dto.bis.TalkHistoryDTO;
import com.sf.qzm.service.TalkHistoryService;
import com.sf.qzm.util.page.PageUtils;

@Service
@Transactional
public class TalkHistoryServiceImpl implements TalkHistoryService {
	@Resource
	private TalkHistoryDao talkHistoryDao;
	@Override
	public void saveOrUpdate(TalkHistory bean) throws Exception {
		if(bean.getHistoryId()!=null){
			talkHistoryDao.update(bean);
		}else{
			talkHistoryDao.save(bean);
		}
	}

	@Override
	public void delete(TalkHistory bean) {
		
	}

	@Override
	public TalkHistoryDTO getById(Integer id) {
		TalkHistory bean =new TalkHistory();
		if(id!=null){
			bean.setHistoryId(id);
			return talkHistoryDao.getByParam(bean);
		}
		return null;
	}

	@Override
	public TalkHistoryDTO getByParam(TalkHistory bean) {
		return talkHistoryDao.getByParam(bean);
	}

	@Override
	public List<TalkHistoryDTO> getListByParam(TalkHistory bean) {
		return talkHistoryDao.getListByParam(bean);
	}

	@Override
	public PageDTO<List<TalkHistoryDTO>> getPageByParam(PageDTO<TalkHistory> bean) {
		List<TalkHistoryDTO> datas=talkHistoryDao.getPageByParam(bean);
		Integer count=talkHistoryDao.getCountByParam(bean.getParam());
		return PageUtils.parseFrom(datas, count, bean);
	}

	@Override
	public List<HistoryCountDTO> loadHistory(String toId) {
		return talkHistoryDao.loadHistory(toId);
	}

}
