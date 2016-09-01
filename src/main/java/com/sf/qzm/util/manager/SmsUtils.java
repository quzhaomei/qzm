package com.sf.qzm.util.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sf.qzm.bean.biz.MsgTemplate;
import com.sf.qzm.util.other.JsonUtils;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 短信发送
 *
 */
public class SmsUtils {
	public static List<MsgTemplate> template=new ArrayList<MsgTemplate>();
	static{
		
		template.add(new MsgTemplate("SMS_13800456", 
				"${name}，感谢您参加本次家具展会活动，报名已成功，后续有专员联系，请保持手机畅通。"));
		
		template.add(new MsgTemplate("SMS_14195148", 
				"9月7-11日，到展会买家具，工厂直供价，多省不止50%！百万家电满额即送！http://t.cn/Rteltz8 回复T退订"));
	}
	private static String appkey="23445662";
	private static String secret="a260f3ec3ba598d9e9e926f25352b83a";
	private static String url="http://gw.api.taobao.com/router/rest";
	private static String signName="吉盛伟邦";
	/**
	 * 0表示成功
	 * @param name
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static SendMsgResult  sendMsg(String name,String phone,String template) {
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend( "jswb" );
		req.setSmsType( "normal" );
		req.setSmsFreeSignName( signName );
		req.setSmsParamString( "{name:'"+name+"'}" );
		req.setRecNum(phone);
		req.setSmsTemplateCode(template);
		AlibabaAliqinFcSmsNumSendResponse rsp=null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		SendMsgResult result=new SendMsgResult();
		System.out.println(rsp.getBody());
		Map<String,Object> map=JsonUtils.parseJSON2Map(rsp.getBody());
		Object response=null;
		response= map.get("alibaba_aliqin_fc_sms_num_send_response");
		if(response!=null){
			result.setSuccess(1);
		}else{
			response= map.get("error_response");
			if(response!=null){
				result.setSuccess(0);
				Map<String,Object> info=(Map<String, Object>) response;
				result.setMessage((String)info.get("sub_msg"));
			}else{
				result.setSuccess(0);
				result.setMessage("短信发送异常，请联系管理员！");
			}
		}
		return result;
	}
}


