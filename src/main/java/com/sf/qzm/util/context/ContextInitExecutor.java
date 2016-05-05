package com.sf.qzm.util.context;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.sf.qzm.socket.server.ServerSocket;

/**
 * 当spring容器初始化完毕之后，执行的初始化事件！
 * @author quzhaomei
 *
 */
@Component
public class ContextInitExecutor implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		ServerSocket socket=SfContextUtils.getComponent(ServerSocket.class);
		if(socket!=null&&!socket.isStart()){
			socket.initSocket();
		}
	}

}
