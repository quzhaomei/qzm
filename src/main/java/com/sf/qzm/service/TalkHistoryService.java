package com.sf.qzm.service;

import java.util.List;

import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.dto.bis.HistoryCountDTO;
import com.sf.qzm.dto.bis.TalkHistoryDTO;

public interface TalkHistoryService extends BaseService<TalkHistory, TalkHistoryDTO>{
	List<HistoryCountDTO> loadHistory(String toId);
}
