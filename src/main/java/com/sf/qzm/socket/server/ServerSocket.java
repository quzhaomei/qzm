package com.sf.qzm.socket.server;

import java.util.Hashtable;
import java.util.Map;

import javax.annotation.PreDestroy;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.sf.qzm.socket.entity.PersionTalkInfo;
import com.sf.qzm.socket.entity.UserInfo;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;

//@Repository
public class ServerSocket {
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
				
			}
	    });
	    //say to user
	    server.addEventListener("say to user", PersionTalkInfo.class, new DataListener<PersionTalkInfo>() {
	    	
			@Override
			public void onData(SocketIOClient socket, PersionTalkInfo talk,
					AckRequest request) throws Exception {
				
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
	
	
	
	
	//处理特殊字符
	private String filterString(String str){
		return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
