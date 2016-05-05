package com.sf.qzm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.dto.PageDTO;
import com.sf.qzm.dto.bis.HistoryCountDTO;
import com.sf.qzm.dto.bis.TalkHistoryDTO;

public interface TalkHistoryDao {
	void save(@Param(value="history") TalkHistory history);//增
	void update(@Param(value="history")TalkHistory history);//改
	void delete(TalkHistory history);//删
	List<TalkHistoryDTO> getListByParam(@Param(value="history")TalkHistory history);//查询数组
	TalkHistoryDTO getByParam(@Param(value="history")TalkHistory history);//查询单个
	int getCountByParam(@Param(value="history")TalkHistory history);//查询数量
	
	List<TalkHistoryDTO> getPageByParam(@Param(value="page")PageDTO<TalkHistory> page);//查询数组
	
	List<HistoryCountDTO> loadHistory(@Param(value="toId")String toId);
}
