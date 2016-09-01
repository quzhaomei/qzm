 	var remoteHost="http://127.0.0.1:9009";
// 	var remoteHost="http://192.168.17.207:9009";
	// ��ȡ�¼���HttpServer��ַ
	var httpServerAdress = remoteHost+"/State/query?callback=?";
	//var httpServerAdress = "http://www.huayunw.com/test.xml";
	//var httpServerAdress = "http://127.0.0.1/cca/TestDummy/UCServer.jsp?callback=?&getEvent=bbb";
	// �����ַ
	var httpServerAdressCallOut = remoteHost+"/CallControl/MakeCall?callback=?";
	//var httpServerAdressCallOut = "http://www.huayunw.com/test.xml";
	//var httpServerAdressCallOut = "http://127.0.0.1/cca/TestDummy/test.xml";
	//var httpServerAdressCallOut = "http://127.0.0.1/cca/TestDummy/UCServer.jsp?callback=?";
	// ���е�ַ
	var httpServerAdressReady = remoteHost+"/State/Ready?callback=?";
	//var httpServerAdressReady = "http://127.0.0.1/cca/TestDummy/test.xml";
	//var httpServerAdressReady = "http://127.0.0.1/cca/TestDummy/UCServer.jsp?callback=?&Ready=bbb";
	// ��æ��ַ
	var httpServerAdressNotReady = remoteHost+"/State/NotReady?callback=?&Reason=";
	//var httpServerAdressNotReady = "http://127.0.0.1/cca/TestDummy/jsonp.txt";
	//var httpServerAdressNotReady = "http://127.0.0.1/cca/TestDummy/UCServer.jsp?callback=?&Reason=bbb";
	// �Ҷϵ�ַ
	var httpServerAdressDisconnect = remoteHost+"/CallControl/Hangup?callback=?";
	//var httpServerAdressDisconnect = "http://127.0.0.1/cca/TestDummy/UCServer.jsp?callback=?";
	// ������ַ
	var openUrl = "http://www.huayunw.com/";
	// ������ʽ
	// UC_OPEN: UCֱ�ӵ���
	// CALL_BACK: UC�ص�����
	var openWindowMode = "CALL_BACK";
	// ˢ���¼�
	var refreshTime = 1000;

	// ��������
	var operation_type = new Array();
	// ���
	operation_type[0] = "CallOut";
	// ����
	operation_type[1] = "Ready";
	// ��æ
	operation_type[2] = "NotReady";
	// �Ҷ�
	operation_type[3] = "Disconnect";

	// �����б�
	var call_in_params = new Array();
	// ԭʼ����
	call_in_params[0] = "OrgANI";
	// ԭʼ����
	call_in_params[1] = "OrgDNIS";
	// ��ǰ����
	call_in_params[2] = "ANI";
	// ��ǰ����
	call_in_params[3] = "DNIS";
	// ��ѯ����
	call_in_params[4] = "CslANI";
	// ��ѯ����
	call_in_params[5] = "CslDNIS";
	// ��������
	call_in_params[6] = "ServiceUnitType";
	// ��������
	call_in_params[7] = "ReqSkill";
	// ��Ӧ������
	call_in_params[8] = "ResSkill";
	// ��������
	call_in_params[9] = "CallType";
	// ���κ��б�ʶ
	call_in_params[10] = "SessionID";
	// IVR���
	call_in_params[11] = "IVRData";
	
	// http����ʽ
	var requestType = "get";
	/**********************���ؽ����************************/
	// �¼�����
	var EventType = "EventType";
	// ԭʼ����
	var OrgANI = "OrgANI";
	// ԭʼ����
	var OrgDNIS = "OrgDNIS";
	// ��ǰ����
	var ANI= "ANI";
	// ��ǰ����
	var DNIS= "DNIS";
	// ��ѯ����
	var CslANI= "CslANI";
	// ��ѯ����
	var CslDNIS= "CslDNIS";
	// ��������
	var ServiceUnitType= "ServiceUnitType";
	// ��������
	var ReqSkill= "ReqSkill";
	// ��Ӧ������
	var ResSkill= "ResSkill";
	// ��������
	var CallType= "CallType";
	// IVR���
	var IVRData= "IVRData";
	// ���κ��б�ʾ
	var SessionID = "SessionID";
	/**********************���ؽ����************************/
	
	/**********************�¼�����EventType************************/

	// Init״̬
	var AgentStatus_Init = "AgentStatus_Init";
	// Login״̬
	var AgentStatus_Login = "AgentStatus_Login";
	// Logout״̬
	var AgentStatus_Logout = "AgentStatus_Logout";
	// Ready״̬
	var AgentStatus_Ready = "AgentStatus_Ready";
	// NotReady״̬
	var AgentStatus_NotReady = "AgentStatus_NotReady";
	// Otherwork״̬
	var AgentStatus_Otherwork = "AgentStatus_Otherwork";
	// ACW״̬
	var AgentStatus_ACW = "AgentStatus_ACW";
	// Locked״̬
	var AgentStatus_Locked = "AgentStatus_Locked";
	// Alerting״̬
	var AgentStatus_Alerting = "AgentStatus_Alerting";
	// Connecting״̬
	var AgentStatus_Connecting = "AgentStatus_Connecting";
	// Connected_Normal״̬
	var AgentStatus_Connected_Normal = "AgentStatus_Connected_Normal";
	// Connected_Internal״̬
	var AgentStatus_Connected_Internal = "AgentStatus_Connected_Internal";
	// Disconnected״̬
	var AgentStatus_Disconnected = "AgentStatus_Disconnected";
	// Suspended״̬
	var AgentStatus_Suspended = "AgentStatus_Suspended";
	// Held״̬(����IVR����ʱתΪ��״̬)
	var AgentStatus_Held = "AgentStatus_Held";
	// Consulting״̬
	var AgentStatus_Consulting = "AgentStatus_Consulting";
	// Consulted״̬
	var AgentStatus_Consulted = "AgentStatus_Consulted";
	// Conferenced״̬
	var AgentStatus_Conferenced = "AgentStatus_Conferenced";
	// Monitored״̬
	var AgentStatus_Monitored = "AgentStatus_Monitored";
	// Inserted״̬
	var AgentStatus_Inserted = "AgentStatus_Inserted";
	// Unkown״̬
	var AgentStatus_Unkown = "AgentStatus_Unkown";
	/**********************�¼�����EventType************************/

	/**********************��������************************/
	// �������ͣ�
	// ��ʶ ��Ϣ ��Ϣ����
	// 0 INTERNAL ����
	// 1 OUTBOUND ���
	// 2 INBOUND �ں�
	var INTERNAL = "INTERNAL";
	var OUTBOUND = "OUTBOUND";
	var INBOUND = "INBOUND";
	/**********************��������************************/

	
	var event_params = new Array();
	// ԭʼ����
	event_params[0] = OrgANI;
	// ԭʼ����
	event_params[1] = OrgDNIS;
	// ��ǰ����
	event_params[2] = ANI;
	// ��ǰ����
	event_params[3] = DNIS;
	// ��ѯ����
	event_params[4] = CslANI;
	// ��ѯ����
	event_params[5] = CslDNIS;
	// ��������
	event_params[6] = ServiceUnitType;
	// ��������
	event_params[7] = ReqSkill;
	// ��Ӧ������
	event_params[8] = ResSkill;
	// ��������
	event_params[9] = CallType;
	// ���κ��б�ʶ
	event_params[10] = SessionID;
	// IVR���
	event_params[11] = IVRData;
	var ucwindows;
	var ucdocument;

	/* UCObj version 2.0 pre
	 * creator: tang-xue (tangxue@huayunw.com)
	 * ���¸��£�2012-2-1
	 */
	function UCObj(win, doc) {
	
	 	ucwindows = win;
	 	ucdocument = doc;
		/*
		 * ��ʼ��UCȡ�����¼�.ÿ��1000msȡһ��
		 */
		this.initUCObj = function(openURL, params, requestTime) { 
			openUrl = openURL;
			call_in_params = params;
			refreshTime = requestTime;
		}
		
		/*
		 * ��ʼ��UCȡ�����¼�.ÿ��1000msȡһ��
		 */
		 this.start = function() { 
			showDebugMessage("Send Start..................");
			win.setInterval("getEvent()",refreshTime); 
			showDebugMessage("Send End..................");
		}
		
		/*
		 * �������
		 */
		this.doCallOut = function(callNum, showNum) {

			showDebugMessage("doCallOut Start..................");
			// �Ⲧ����ǰ���Tel:��ǰ׺
			if (callNum.indexOf("Tel:") == -1) {
				callNum = "Tel:" + callNum;
			}

			var callOutUrl = httpServerAdressCallOut + "&TargetDN=" + callNum + "&ShowANI=" + showNum + "&MakeCallType=2";
			var msg = "";

			jQuery.ajax({
	  		type: requestType,
			  url: callOutUrl,
			  dataType: "jsonp",
			  jsonp: "callback",
			  beforeSend: function(XMLHttpRequest){
			   showDebugMessage("beforeSend Start..................");
			  },
			  success: function(data){
			  	showDebugMessage("success Start..................");
					// ������
					ucwindows.showOpeResultMsg(operation_type[0],data.Code, data.Msg);
			  },
			  complete: function(XMLHttpRequest, textStatus){
			   	showDebugMessage("complete Start..................");
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown){
			   //������?��
			   showDebugMessage("���ʧ�� " + XMLHttpRequest.statusText);
				 ucwindows.showOpeResultMsg(operation_type[0],"-2", "����ʧ�ܡ�");
			  }
			})

			showDebugMessage("doCallOut End..................");
			return msg;
		}

		/*
		 * ���д���
		 */
		this.doReady = function() {

			showDebugMessage("doReady Start..................");

			var msg = "";
		
			jQuery.ajax({
	  		type: requestType,
			  url: httpServerAdressReady,
			  dataType: "jsonp",
			  jsonp: "callback",
			  beforeSend: function(XMLHttpRequest){
			   showDebugMessage("beforeSend Start..................");
			  },
			  success: function(data){
			  	showDebugMessage("success Start..................");
			  	// ������
					ucwindows.showOpeResultMsg(operation_type[1],data.Code, data.Msg);
				
			  },
			  complete: function(XMLHttpRequest, textStatus){
			   	showDebugMessage("complete Start..................");
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown){
			   //������?��
			   showDebugMessage("����ʧ��: " + XMLHttpRequest.statusText);
				 ucwindows.showOpeResultMsg(operation_type[1],"-2", "����ʧ�ܡ�");
			  }
			})
			
			showDebugMessage("doReady End..................");
			return msg;
		}

		/*
		 * ��æ����
		 */
		this.doNotReady = function() {

			showDebugMessage("doNotReady Start..................");
			var msg = "";
      
			jQuery.ajax({
	  		type: requestType,
			  url: httpServerAdressNotReady,
			  dataType: "jsonp",
			  jsonp: "callback",
			  beforeSend: function(XMLHttpRequest){
			   showDebugMessage("beforeSend Start..................");
			  },
			  success: function(data){
			  	showDebugMessage("success Start..................");
			  	// ������
					ucwindows.showOpeResultMsg(operation_type[2],data.Code, data.Msg);
					
			  },
			  complete: function(XMLHttpRequest, textStatus){
			   	showDebugMessage("complete Start..................");

			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown){
			   //������?��
			   showDebugMessage("��æʧ��: " + XMLHttpRequest.statusText);
				 ucwindows.showOpeResultMsg(operation_type[2],"-2", "����ʧ�ܡ�");
			  }
			})

			showDebugMessage("doNotReady End..................");
			return msg;
		}
		
		/*
		 * �Ҷϴ���
		 */
		this.doDisconnect = function() {

			showDebugMessage("doDisconnect Start..................");
			var msg = "";
      
			jQuery.ajax({
	  		type: requestType,
			  url: httpServerAdressDisconnect,
			  dataType: "jsonp",
			  jsonp: "callback",
			  beforeSend: function(XMLHttpRequest){
			   showDebugMessage("beforeSend Start..................");
			  },
			  success: function(data){
			  	showDebugMessage("success Start..................");
			  	// ������
					//ucwindows.showOpeResultMsg(operation_type[3],data.Code, data.Msg);
					
			  },
			  complete: function(XMLHttpRequest, textStatus){
			   	showDebugMessage("complete Start..................");

			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown){
			   //������?��
			   showDebugMessage("�Ҷ�ʧ��: " + XMLHttpRequest.statusText);
				 //ucwindows.showOpeResultMsg(operation_type[3],"-2", "����ʧ�ܡ�");
			  }
			})

			showDebugMessage("doDisconnect End..................");
			return msg;
		}
	}

	/*
	 * �����¼������Ϊ�����¼����е�������
	 */
	function getEvent() {

			jQuery.ajax({
	  		type: requestType,
			  url: httpServerAdress,
			  dataType: "jsonp",
			  jsonp: "callback",
			  beforeSend: function(XMLHttpRequest){
			   showDebugMessage("beforeSend Start..................");
			  },
			  success: function(data){
			  	showDebugMessage("success Start..................");

					// �¼�����
					var resultCode = data.Result;
					
					if (resultCode != "0") {
						return;
					};
					// �¼�����
					var EventType = data.EventType;
					//if (EventType == AgentStatus_Alerting) {
					if (openWindowMode == "UC_OPEN") {
								doOpenUrl(data);
							} else {
								doCallBackOpen(data);
							}
					//	} 
			  },
			  complete: function(XMLHttpRequest, textStatus){
			   	showDebugMessage("complete Start..................");
			  },
			  error: function(XMLHttpRequest, textStatus, errorThrown){

			   //������?��
			   showErrorMessage("ȡ���¼�ʧ��: " + XMLHttpRequest.statusText);
			  }
			})

	}

	// UCֱ�ӵ���
	function doOpenUrl(events) {
		// ƴ�ӵ�����ַ
		var j = 0;
		for (j = 0; j < call_in_params.length; j++) {
			var paramName = call_in_params[j];
			if (paramName != "") {
				// ��һ������
				if (openUrl.indexOf("?") < 0) {
					var dataValue = "";
					if (event_params[j] == "OrgANI") {
						dataValue = events.OrgANI;
					} else if (event_params[j] == "OrgDNIS") {
						dataValue = events.OrgDNIS;
					} else if (event_params[j] == "ANI") {
						dataValue = events.ANI;
					} else if (event_params[j] == "DNIS") {
						dataValue = events.DNIS;
					} else if (event_params[j] == "CslANI") {
						dataValue = events.CslANI;
					} else if (event_params[j] == "CslDNIS") {
						dataValue = events.CslDNIS;
					} else if (event_params[j] == "ServiceUnitType") {
						dataValue = events.ServiceUnitType;
					} else if (event_params[j] == "ReqSkill") {
						dataValue = events.ReqSkill;
					} else if (event_params[j] == "ResSkill") {
						dataValue = events.ResSkill;
					} else if (event_params[j] == "CallType") {
						dataValue = events.CallType;
					} else if (event_params[j] == "IVRData") {
						dataValue = events.IVRData;
					}
					openUrl = openUrl + "?" + paramName + "=" + dataValue;
					// �������
				} else {
					var dataValue = "";
					if (event_params[j] == "OrgANI") {
						dataValue = events.OrgANI;
					} else if (event_params[j] == "OrgDNIS") {
						dataValue = events.OrgDNIS;
					} else if (event_params[j] == "ANI") {
						dataValue = events.ANI;
					} else if (event_params[j] == "DNIS") {
						dataValue = events.DNIS;
					} else if (event_params[j] == "CslANI") {
						dataValue = events.CslANI;
					} else if (event_params[j] == "CslDNIS") {
						dataValue = events.CslDNIS;
					} else if (event_params[j] == "ServiceUnitType") {
						dataValue = events.ServiceUnitType;
					} else if (event_params[j] == "ReqSkill") {
						dataValue = events.ReqSkill;
					} else if (event_params[j] == "ResSkill") {
						dataValue = events.ResSkill;
					} else if (event_params[j] == "CallType") {
						dataValue = events.CallType;
					} else if (event_params[j] == "IVRData") {
						dataValue = events.IVRData;
					}
					openUrl = openUrl + "&" + paramName + "=" + dataValue;
				}

			}
		}
		showDebugMessage("openUrl: " + openUrl);
		window.open(openUrl);
	}
	
	// �ص�ҵ��ϵͳ����
	function doCallBackOpen(events) {
		/*
		var i = 0;
		var result = "{";
		for (i = 0; i < event_params.length; i++) {
			
			// JSON�������
			result = result + '"' + event_params[i] + '":';
			result = result + '"' + events[event_params[i]] + '",';
		}
		
		result = result.substring(0, result.length - 1);
		result = result + "}"*/
		showDebugMessage("doCallBackOpen��ʼִ�С� ");
		ucwindows.callIn(events);
	}

	var debug_mode = false;
	function showDebugMessage(showMessage) {
		if (debug_mode) {
			alert(showMessage);
		}
	}
	
	var errorlog_mode = false;
	function showErrorMessage(errorMessage) {
		if (errorlog_mode) {
			alert(errorMessage);
		}
	}

	// ��ִͣ�к���
	function Pause(obj,iMinSecond) {     
    if (window.eventList==null)   
        window.eventList=new Array();     
    var ind=-1;     
    for (var i=0;i<window.eventList.length;i++)  
    {      
        if (window.eventList[i]==null)   
        {       
            window.eventList[i]=obj;   
            ind=i;      
            break;     
        }   
    }       
    if (ind==-1)  
    {      
        ind=window.eventList.length;      
        window.eventList[ind]=obj;     
    }     
    setTimeout("GoOn(" + ind + ")",iMinSecond);    
}   
  
  // ������
 function GoOn(ind) {     
    var obj=window.eventList[ind];     
    window.eventList[ind]=null;     
    if (obj.NextStep)   
        obj.NextStep();     
    else obj();    
 }
    
  // ������
  function sleep(sleeptime) { 
    var start=new   Date().getTime(); 
    while(true)   if(new Date().getTime()-start> sleeptime)
    break; 
  } 
  
	function killErrors(){
		return true;
	}
	window.onerror = killErrors;