package com.sf.qzm.controller.bis;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sf.qzm.bean.bis.TalkHistory;
import com.sf.qzm.controller.BaseController;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.bis.CooperationDTO;
import com.sf.qzm.service.CooperationService;
import com.sf.qzm.socket.entity.UserInfo;
import com.sf.qzm.socket.server.ServerSocket;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;
import com.sf.qzm.util.other.PasswordUtils;

@Controller
@RequestMapping("talking")
public class TalkIndexController extends BaseController {
	@RequestMapping(value="/socketServer" )
	public String socketServer(HttpServletRequest request, String jsoncallback,Model model){
		JsonDTO json=new JsonDTO();
		String socketServer=getSocketServer(request);
		json.setData(socketServer);
		if (jsoncallback != null) {
			model.addAttribute("json", jsoncallback + "(" + JsonUtils.object2json(json) + ")");
		} else {
			model.addAttribute("json", JsonUtils.object2json(json));
		}
		return "json";
	}
	/**
	 * 跳转聊天首页
	 * 参数附带 fromUser，toUser，code，sign。
	 * @param request
	 * @param model
	 * @param history
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(Model model){

		String id="itooth2016customer";
		String name="cesixx";
		String code="itooth2016";
		String sign=PasswordUtils.MD5(id+"#"+code);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("code", code);
		model.addAttribute("sign", sign);
		return "admin/talking";
	}
	//检测数据是否正常
	private boolean checkUser(UserInfo user){
		return user.getHeadImg()!=null&&user.getId()!=null&&user.getName()!=null;
	}
	
	//检测签名是否正确
	private boolean checkSign(TalkHistory history,String code,String sign){
		String signCheck=code+"#"+history.getFromId()+"#"+history.getToId();
		signCheck=PasswordUtils.MD5(signCheck);
		return signCheck.equals(signCheck);
	}
	
	//检测企业code是否正常
	private boolean checkCode(String code){
		CooperationDTO cooperation=
				SfContextUtils.getComponent(CooperationService.class).getByCode(code);
		if(cooperation==null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 获取聊天的socketurl
	 * @param request
	 * @return
	 */
	private String getSocketServer(HttpServletRequest request){
		String root=SfContextUtils.getServerRootUrl(request);
		//去掉root中的端口号
		while(root.lastIndexOf(":")>4){
			root=root.substring(0, root.lastIndexOf(":"));
		}
		String port=SfContextUtils.getSystemSourceByKey(Constant.SOCKET_PORT);
		String socketServer=null;
		if(port==null){
			socketServer=root+":"+ServerSocket.DEFAULT_SOCKET_PORT;
		}else{
			socketServer=root+":"+port;
		}
		return socketServer;	
	}
}
