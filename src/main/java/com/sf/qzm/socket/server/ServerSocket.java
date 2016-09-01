package com.sf.qzm.socket.server;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.dto.bis.CooperationDTO;
import com.sf.qzm.dto.bis.HistoryCountDTO;
import com.sf.qzm.dto.bis.TalkHistoryDTO;
import com.sf.qzm.service.CooperationService;
import com.sf.qzm.service.TalkHistoryService;
import com.sf.qzm.socket.entity.PersionTalkInfo;
import com.sf.qzm.socket.entity.UserInfo;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.PasswordUtils;

@Repository
public class ServerSocket {
	@Resource
	private TalkHistoryService historyService;
	@Resource
	private CooperationService cooperationService;
	public static final Integer DEFAULT_SOCKET_PORT=14080;
	public static final String WELCOME="您好！有什么需要帮助的吗？";
	private boolean start=false;
	private static Map<String,SocketIOClient> socketMap=new Hashtable<String, SocketIOClient>();
	public static boolean isOnline(Integer getMoreId){
		if(getMoreId!=null){
			return socketMap.get(getMoreId)!=null;
		}
		return false;
	}
	private SocketIOServer server=null;
	@PreDestroy
	public void close(){
		server.stop();
	}
	public void flesh(){
		server.stop();
		initSocket();
	}
	
	public void initSocket(){
		String portStr=SfContextUtils.getSystemSourceByKey(Constant.SOCKET_PORT);
		Integer socketPort=DEFAULT_SOCKET_PORT;
		if(portStr!=null&&portStr.matches("\\d+")){
			socketPort=Integer.parseInt(portStr);
		}
		Configuration config = new Configuration();
	    config.setPort(socketPort);
	    config.setUpgradeTimeout(100000);
        config.setPingTimeout(100000);
        config.setPingInterval(100000);
	    server=new SocketIOServer(config);
	    //连接成功事件
	    server.addConnectListener(new ConnectListener() {
	        @Override
	        public void onConnect(SocketIOClient socket) {
	        	socket.sendEvent
				("connect success", "{\"status\":\"ok\"}");
	        }
	    });
	   
	    //login浜嬩欢
	    server.addEventListener("login", UserInfo.class, new DataListener<UserInfo>() {
			@Override
			public void onData(SocketIOClient socket, UserInfo userInfo,
					AckRequest request) throws Exception {
				//进行权限验证
				String id=userInfo.getId();
				String code=userInfo.getCode();
				String sign=userInfo.getSign();
				String bak=id+"#"+code;
				if(!PasswordUtils.MD5(bak).equalsIgnoreCase(sign)){//拦截登录
					socket.disconnect();return;
				}else if(SfContextUtils.getComponent(CooperationService.class).getByCode(code)==null){
					socket.disconnect();return;
				}
				
				//进行登录操作
				SocketIOClient old=null;
				//挤出
				if((old=socketMap.put(userInfo.getId(), socket))!=null){
					old.sendEvent("pick up", " ");
					old.del("user");
					old.disconnect();
				}
				System.out.println(userInfo.getName()+"登录了");
				socket.set("user", userInfo);//绑定用户信息
				//初始化未接受消息条目
				List<HistoryCountDTO> historyCount= historyService.loadHistory(userInfo.getId());
				
				socket.sendEvent("unaccpet msg", JsonUtils.object2json(historyCount));
			}
	    });
	    //say to user
	    server.addEventListener("say to user", PersionTalkInfo.class, new DataListener<PersionTalkInfo>() {
	    	
			@Override
			public void onData(SocketIOClient socket, PersionTalkInfo talk,
					AckRequest request) throws Exception {
				
				talk.setFromId(((UserInfo)(socket.get("user"))).getId());//绑定该id
				TalkHistory history=createHistory(talk);
				//保存聊天信息
				historyService.saveOrUpdate(history);
				
				//给发送消息的人，回显信息
				String fromUser=talk.getFromId();
				history.setMessage(filterString(history.getMessage()));
				if(socketMap.get(fromUser)!=null){
					socket.sendEvent("per2perTalking",JsonUtils.object2json(history));
				}
				
				//传达消息
				String toUserId=talk.getToId();
				if(socketMap.get(toUserId)!=null){
					socketMap.get(toUserId).sendEvent
					("per2perTalking", JsonUtils.object2json(history));
					//信息更新为已接收
			//		history.setStatus(TalkHistory.STATUS_ACCEPT);
			//		historyService.saveOrUpdate(history);
					
					System.out.println("---"+ talk.getMessage()+"消息");
				}
				
			}
	    });
	    
	    //信息更新为已读
	    server.addEventListener("read talk", TalkHistory.class, new DataListener<TalkHistory>() {
	    	
			@Override
			public void onData(SocketIOClient socket, TalkHistory talk,
					AckRequest request) throws Exception {
				talk.setStatus(TalkHistory.STATUS_ACCEPT);
				//更新为已读聊天信息
				historyService.saveOrUpdate(talk);
				
			}
	    });
	    
	    //load history
	    server.addEventListener("load history", PersionTalkInfo.class, new DataListener<PersionTalkInfo>() {
	    	
	    	@Override
	    	public void onData(SocketIOClient socket, PersionTalkInfo talk,
	    			AckRequest request) throws Exception {
	    		UserInfo user=socket.get("user");
	    		TalkHistory history=new TalkHistory();
	    		System.out.println(socket.get("user"));
	    		history.setFromId(talk.getToId());
	    		history.setToId(user.getId());
	    		//保存聊天信息
	    		List<TalkHistoryDTO> talks=historyService.getListByParam(history);
	    		for(TalkHistoryDTO temp :talks){
	    			if(temp.getStatus()==0&&
	    					temp.getToId().equals(((UserInfo)(socket.get("user"))).getId())
	    					){//更新为已接受
	    				TalkHistory update=new TalkHistory();
	    				update.setStatus(TalkHistory.STATUS_ACCEPT);
	    				update.setHistoryId(temp.getHistoryId());
	    				historyService.saveOrUpdate(update);
	    			}
	    		}
	    		socket.sendEvent("load history",JsonUtils.object2json(talks));
	    		
	    		if(talks==null||talks.size()==0){//发送问候语
	    			String code=user.getCode();
	    			CooperationDTO coop=cooperationService.getByCode(code);
	    			
					TalkHistory welcome=new TalkHistory();
					welcome.setFromId(talk.getToId());
					welcome.setFromHeadImg(coop.getLogo());
					welcome.setFromName(coop.getName()+"［自动回复］");
					welcome.setToId(talk.getFromId());
					welcome.setToHeadImg(user.getHeadImg());
					welcome.setToName(user.getName());
					welcome.setMessage(WELCOME);
					welcome.setCreateDate(new Date().getTime());
					socket.sendEvent("per2perTalking",JsonUtils.object2json(welcome));//发送
				}
	    		
	    		
	    	}
	    });
	    //delete history
	    server.addEventListener("delete history", PersionTalkInfo.class, new DataListener<PersionTalkInfo>() {
	    	
	    	@SuppressWarnings("deprecation")
			@Override
	    	public void onData(SocketIOClient socket, PersionTalkInfo talk,
	    			AckRequest request) throws Exception {
	    		UserInfo user=socket.get("user");
	    		TalkHistory history=new TalkHistory();
	    		history.setFromId(talk.getToId());
	    		history.setToId(user.getId());
	    		if(talk.getToId()!=null&&user.getId()!=null)
	    		//删除聊天信息
	    		historyService.delete(history);
	    		
	    	}
	    });
	    //失去连接事件
	    server.addDisconnectListener(new DisconnectListener() {
	        @Override
	        public void onDisconnect(SocketIOClient socket) {
	        	UserInfo userInfo=socket.get("user");
	        	if(userInfo!=null){
	        		System.out.println(userInfo.getName()+"下线了");
	        		String userId=userInfo.getId();
	        		socketMap.remove(userId);//socket
	        	}
	        }
	    });
//	    	server.start();
	    	start=true;
	}
	public boolean isStart() {
		return start;
	}
	
	
	private TalkHistory createHistory(PersionTalkInfo info){
		UserInfo fromUser=socketMap.get(info.getFromId()).get("user");
		TalkHistory talk=new TalkHistory();
		talk.setFromId(info.getFromId());
		if(fromUser!=null){
			talk.setFromName(fromUser.getName());
			talk.setFromHeadImg(fromUser.getHeadImg());
		}else{
			talk.setFromName("");
			talk.setFromHeadImg("");
		}
		talk.setToName("");
		talk.setToHeadImg("");
		talk.setToId(info.getToId());
		/**
		UserInfo toUser=(UserInfo) (socketMap.get(info.getToId())!=null?socketMap.get(info.getToId()).get("user"):null);
		if(toUser!=null){
			talk.setToName(toUser.getName());
			talk.setToHeadImg(toUser.getHeadImg());
		}else{
			talk.setToName("");
			talk.setToHeadImg("");
		}**/
		talk.setCreateDate(new Date().getTime());
		talk.setStatus(TalkHistory.STATUS_UN_ACCEPT);
		talk.setMessage(info.getMessage());
		return talk;
	}
	
	//处理特殊字符
	private String filterString(String str){
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
