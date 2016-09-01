package com.sf.qzm.controller.back;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sf.qzm.annotation.LoginTag;
import com.sf.qzm.bean.constant.Zone;
import com.sf.qzm.bean.login.LoginOutMessage;
import com.sf.qzm.dto.ImgUploadResultDTO;
import com.sf.qzm.dto.JsonDTO;
import com.sf.qzm.dto.admin.AdminUserDTO;
import com.sf.qzm.dto.menu.AutoMenuDTO;
import com.sf.qzm.service.AdminUserService;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.service.SystemSourceService;
import com.sf.qzm.service.ZoneService;
import com.sf.qzm.util.context.SfContextUtils;
import com.sf.qzm.util.other.Constant;
import com.sf.qzm.util.other.JsonUtils;

@Controller
@RequestMapping("welcome")
@LoginTag
public class WelcomeController {
	
	@Resource
	private AdminUserService adminUserService;
	@Resource
	private AutoMenuService menuService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		return "admin/welcome";
	}
	/**
	 * 查询是否有此权限
	 * @param powerCode
	 * @return
	 */
	@RequestMapping(value = "/checkPower")
	@ResponseBody
	public JsonDTO checkPower(HttpServletRequest request, String powerCode,HttpSession session) {
		String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
		JsonDTO json=new JsonDTO();
		AdminUserDTO user= (AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		if(user.getPhone().equals(godPhone)|| user.getMenuCodeMap().get(powerCode)!=null){
			json.setStatus(1);//1为有权限，0表示没有权限
		}else{
			json.setStatus(0);//1为有权限，0表示没有权限
		}
		
		
		return json;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return json字符串
	 * logo:{image:"images/logo.svg",title:"我的后台管理",url:"url"},
		user:{name:"游客",avatar:"images/avatar.jpg"},
		menu:[{
		    	  icon:"icon-cog",
		    	  title:"系统设置",
		    	  url:"",
		    	  sequence:1,
		    	  childMenu:[
		    	        {title:"通用设定",url:"",sequence:1},
		    	        {title:"内容模块管理",url:"",sequence:2,},
		    	        {title:"角色及权限分配",url:"",sequence:3,},
		    	        {title:"后台菜单",url:"",sequence:4}
		    	   ]
		      },
		      {icon:"icon-users",title:"用户管理",url:"",sequence:2,childMenu:[]}
		      ],
		 menu_:[
		        {icon:"icon-earth",title:"站点信息设置",url:"",sequence:1,childMenu:[]},
		        ]
		       }
	 */
	@ResponseBody
	@RequestMapping(value="/menu-info")
	public Map<String,Object> menuInfo(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, Model model) {
		AdminUserDTO loginUser=(AdminUserDTO) session.getAttribute(Constant.ADMIN_USER_SESSION);
		Map<String,Object> result=new HashMap<String, Object>();
		//模拟数据
		Map<String,String> user=new HashMap<String, String>();
		user.put("name", "管理员");
		user.put("avatar", "images/avatar.jpg");
		result.put("user", user);
		
		Map<String,String> logo=new HashMap<String, String>();
		logo.put("image", "images/logo-admin.png");
		logo.put("title", SfContextUtils.getSystemSourceByKey("title"));
		logo.put("url", "#");
		result.put("logo", logo);
		//根据用户角色查找菜单
		List<AutoMenuDTO> menuList=menuService.getAdminNavMenu(loginUser.getAdminUserId());
		String godPhone = SfContextUtils.getWebXmlParam(request, "godPhone");
		
		if(godPhone.equals(loginUser.getPhone())){//超级账号
			menuList=menuService.getAdminNavMenu();
		}
		
		//拆分
		List<AutoMenuDTO> menu=new ArrayList<AutoMenuDTO>();
		List<AutoMenuDTO> menu_=new ArrayList<AutoMenuDTO>();
		for(AutoMenuDTO temp:menuList){
			if(temp.getExtend()!=null&&temp.getExtend()==1){
				menu_.add(temp);
			}else{
				menu.add(temp);
			}
		}
		
		result.put("menu", menu);
		result.put("menu_", menu_);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/sss_check")
	public LoginOutMessage checkLoginStatus(HttpSession session){
		LoginOutMessage	loginOut=new LoginOutMessage();
			loginOut.setType(1);//正常状态
			return loginOut;
	}
	
	@Resource
	private ZoneService zoneService;
	
	@ResponseBody
	@RequestMapping(value="/getZone")
	public List<Zone> getChildZone(Integer id){
		List<Zone> zones=zoneService.list(id);
		return zones;
	}
	
	@RequestMapping(value="/allZone")
	public String allZone(String varible,Map<String,Object> model){
		List<Map<String,Object>> zones=new ArrayList<Map<String, Object>>();
		List<Zone> roots=zoneService.root();
		for(Zone temp:roots){
			Map<String,Object> tempRoot=new HashMap<String, Object>();
			tempRoot.put("zoneId", temp.getZoneId());
			tempRoot.put("name", temp.getName());
			tempRoot.put("children", initChildren(temp.getZoneId()));
			zones.add(tempRoot);
		}
		model.put("varible", varible);
		model.put("value", JsonUtils.object2json(zones));
		return "script";
	}
	
	private List<Map<String,Object>> initChildren(Integer parentId){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Zone> children=zoneService.list(parentId);
		if(children!=null&&children.size()>0){
			for(Zone temp:children){
				Map<String,Object> tempRoot=new HashMap<String, Object>();
				tempRoot.put("zoneId", temp.getZoneId());
				tempRoot.put("name", temp.getName());
				tempRoot.put("children", initChildren(temp.getZoneId()));
				result.add(tempRoot);
			}
		}
		return result;
	}
	
	@Resource
	private SystemSourceService sourceService;
	private String tomcat_house=System.getProperty("catalina.base");//tomcat根目录
	//上传图片
	@RequestMapping(value = "/uploadSource/{mkdirs}")
	@ResponseBody
	public ImgUploadResultDTO uploadSource(@PathVariable(value="mkdirs") String mkdirs,
				@RequestParam(value = "file") MultipartFile file, Model model) {
		String rootPath=sourceService.getByKey("img_path");//获取全局配置，图片根目录
			String orName=file.getOriginalFilename();
			String houzui="";
			if(orName.lastIndexOf(".")!=-1){
				houzui=orName.substring(orName.lastIndexOf("."));
			}
			File parent = new File("/"+tomcat_house+"/"+rootPath+"/"+mkdirs );
			if (!parent.exists()) {
				parent.mkdirs();
			}
			
			String filename = UUID.randomUUID()+houzui;
			
			File target = new File(parent, filename);
			// 如果大于200K，则进行压缩
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), target);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImgUploadResultDTO result=new ImgUploadResultDTO();
			result.setError(0);
			result.setUrl("/"+rootPath+"/"+mkdirs+"/"+filename);
			// 回传结果
			return result;
		}
}
