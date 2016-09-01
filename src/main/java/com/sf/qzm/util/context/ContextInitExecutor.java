package com.sf.qzm.util.context;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sf.qzm.annotation.MenuTag;
import com.sf.qzm.bean.menu.AutoMenu;
import com.sf.qzm.service.AutoMenuService;
import com.sf.qzm.socket.server.ServerSocket;

/**
 * 当spring容器初始化完毕之后，执行的初始化事件！
 * @author quzhaomei
 *
 */
@Component
public class ContextInitExecutor implements ApplicationListener<ContextRefreshedEvent>{
	private final String subfix=".htmls";
	/**
	 * 后台所有需要权限拦截的链接
	 */
	public  Map<String,AutoMenu> menuMaps=new HashMap<String, AutoMenu>();
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ServerSocket socket=SfContextUtils.getComponent(ServerSocket.class);
		if(socket!=null&&!socket.isStart()){
			socket.initSocket();
		}
		/**
		 * 初始化所有的菜单
		 */
		if(event.getApplicationContext().getParent()!=null){
			RequestMappingHandlerMapping maps=event.getApplicationContext().getBean(RequestMappingHandlerMapping.class);
			Map<RequestMappingInfo, HandlerMethod> methodMaps=maps.getHandlerMethods();
			Set<RequestMappingInfo> keys=methodMaps.keySet();
			for(RequestMappingInfo temp:keys){
				HandlerMethod method=methodMaps.get(temp);
				String servalPath=temp.getPatternsCondition().getPatterns().iterator().next()+subfix;
				parseMenu(method, servalPath);
			}
			AutoMenuService menuService=event.getApplicationContext().getBean(AutoMenuService.class);
			List<AutoMenu> autoMenus= menuService.getAllMenu();
			compare(autoMenus,menuService);
		}
	}
	
	private  void parseMenu(HandlerMethod method,String servalPath){
		MenuTag tag=method.getMethod().getAnnotation(MenuTag.class);
		if(tag!=null){//菜单注册
			//判断type
			Integer extend=0;
			if(method.getMethod().getDeclaringClass().toString().indexOf("back")==-1){
				extend=1;
			}
			AutoMenu methodMenu=new AutoMenu();
			methodMenu.setCode(tag.code());
			methodMenu.setName(tag.name());
			methodMenu.setType(tag.type());
			methodMenu.setParentCode(tag.parentCode());
			methodMenu.setSequence(tag.sequence());
			methodMenu.setServletUrl(servalPath);
			methodMenu.setIcon(tag.icon());
			methodMenu.setExtend(extend);
			
			MenuTag pkg=method.getBeanType().getAnnotation(MenuTag.class);
			
			menuMaps.put(tag.code(), methodMenu);
			
			if(pkg!=null){//注册父标签
				if(tag.parentCode()==null||"".equals(tag.parentCode())){//设置默认值
					methodMenu.setParentCode(pkg.code());
				}
				
				AutoMenu classMenu=new AutoMenu();
				classMenu.setCode(pkg.code());
				classMenu.setName(pkg.name());
				classMenu.setType(pkg.type());
				classMenu.setIcon(pkg.icon());
				classMenu.setParentCode(null);
				classMenu.setSequence(pkg.sequence());
				classMenu.setServletUrl(null);
				classMenu.setExtend(extend);
				menuMaps.put(pkg.code(), classMenu);
			}
		}
	}
	
	/**
	 * 处理数据库菜单，和文件中菜单配置的差异
	 * @param menus
	 * @param service
	 */
	@SuppressWarnings("deprecation")
	private void compare(List<AutoMenu> menus,AutoMenuService service){
		List<AutoMenu> deleteMenu=new ArrayList<AutoMenu>();//需要删除的
		List<AutoMenu> saveMenu=new ArrayList<AutoMenu>();//需要保存的
		List<AutoMenu> updateMenu=new ArrayList<AutoMenu>();//需要更新的
		
		if(menus!=null&&menus.size()>0){
			for(AutoMenu dbMenu:menus){
				String code=dbMenu.getCode();
				AutoMenu javaMenu=menuMaps.get(code);
				if(javaMenu==null){
					deleteMenu.add(dbMenu);//需要删除的
				}else{
					javaMenu.setMenuId(dbMenu.getMenuId());//赋值id
					menuMaps.remove(code);
					if(new Comparator<AutoMenu>(){
						@Override
						public int compare(AutoMenu menu1, AutoMenu menu2) {
							if(menu1.getParentCode()!=null&&!menu1.getParentCode().equals(menu2.getParentCode())){
								return 1;
							}else if(menu1.getName()!=null&&!menu1.getName().equals(menu2.getName())){
								return 1;
							}else if(menu1.getServletUrl()!=null&&!menu1.getServletUrl().equals(menu2.getServletUrl())){
								return 1;
							}else if(!menu1.getSequence().equals(menu2.getSequence())){
								return 1;
							}else if(!menu1.getType().equals(menu2.getType())){
								return 1;
							}else if(!menu1.getExtend().equals(menu2.getExtend())){
								return 1;
							}else if(!menu1.getIcon().equals(menu2.getIcon())){
								return 1;
							}
							return 0;
						}
						
					}.compare(dbMenu, javaMenu)>0){
						updateMenu.add(javaMenu);
					}
					
					
				}
			}
		}
		saveMenu.addAll(menuMaps.values());//新增的
		
		//同步数据
		if(saveMenu.size()!=0){
			for(AutoMenu save:saveMenu){
				try {
					save.setCreateDate(new Date());
					service.saveOrUpdate(save);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(updateMenu.size()!=0){
			for(AutoMenu update:updateMenu){
				try {
					service.saveOrUpdate(update);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(deleteMenu.size()!=0){
			for(AutoMenu delete:deleteMenu){
				try {
					service.delete(delete);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
