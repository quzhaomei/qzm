package com.sf.qzm.controller.bis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sf.qzm.bean.ac.ActiveCol;
import com.sf.qzm.bean.ac.ActiveVal;
import com.sf.qzm.dao.SystemSourceDao;
import com.sf.qzm.service.ActiveService;
import com.sf.qzm.util.context.SfContextUtils;

@Controller
@RequestMapping("active")
public class testActiveController {
	@Resource
	private ActiveService activeService;
	
	
	@RequestMapping(value = "index/{activeId}")
	public String activeIndex(HttpSession session, @PathVariable("activeId") Integer activeId, Map<String, Object> map) {
		List<ActiveCol> cols = activeService.getColById(activeId);
		map.put("cols", cols);
		map.put("activeId", activeId);
		return "active-index";
	}

	@RequestMapping(value = "getDatas")
	@ResponseBody
	public List<Map<String, String>> activeIndex(Integer activeId) {
		return activeService.getDataById(activeId);
	}

	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, Integer activeId) {
		Enumeration<String> params = request.getParameterNames();
		List<ActiveVal> values = new ArrayList<ActiveVal>();
		List<ActiveCol> cols = activeService.getColById(activeId);
		while (params.hasMoreElements()) {
			String temp = params.nextElement();
			String value = request.getParameter(temp);
			Integer colId = getColId(temp, cols);
			if (colId != null) {
				ActiveVal per = new ActiveVal();
				per.setColId(colId);
				per.setValue(value);
				values.add(per);
			}
		}
		activeService.save(values);
	}

	private Integer getColId(String property, List<ActiveCol> cols) {
		for (ActiveCol temp : cols) {
			if (temp.getProperty().equals(property)) {
				return temp.getId();
			}
		}
		return null;
	}

	@RequestMapping(value = "getWork")
	@ResponseBody
	public List<Map<String, String>> getWork() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key", "互联网");
		map1.put("value", "互联网");
		result.add(map1);
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key", "制造业");
		map2.put("value", "制造业");
		result.add(map2);
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("key", "金融业");
		map3.put("value", "金融业");
		result.add(map3);
		return result;
	}

	@RequestMapping(value = "getPro")
	@ResponseBody
	public List<Map<String, String>> getPro() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key", "1");
		map1.put("value", "湖北");
		result.add(map1);
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("key", "2");
		map2.put("value", "湖南");
		result.add(map2);
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("key", "3");
		map3.put("value", "上海");
		result.add(map3);
		return result;
	}

	@RequestMapping(value = "getCity")
	@ResponseBody
	public List<Map<String, String>> getCity(String key) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("key", "城市" + key);
		map1.put("value", "城市" + key);
		result.add(map1);
		return result;
	}

	@Value("#{propertyConfigurer['jdbc.username']}")
	private String username;
	@Value("#{propertyConfigurer['jdbc.password']}")
	private String password;

	@RequestMapping(value = "download")
	@ResponseBody
	public void download(HttpServletResponse response) {
		String sqldir = System.getProperty("catalina.home") + "/sqlTemp";
		File dir = new File(sqldir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String filename = "tempsql.sql";
		File file = new File(dir, filename);
		String database = "talk";
		String sqlBase = (String) SfContextUtils.getComponent(SystemSourceDao.class)
				.executeSql("select @@basedir as basePath from dual");
		StringBuffer command = new StringBuffer(sqlBase);
		command.append("/bin/mysqldump").append(" -u").append(username).append(" -p").append(password).append(" ")
				.append(database).append(" -r ").append(file.getAbsolutePath());
		
		try {
			Process p=Runtime.getRuntime().exec(new String[]{"csh","-c",command.toString()});
			p.waitFor();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String contentType = "application/x-download";
		response.setContentType(contentType);
		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1"));
			ServletOutputStream out = response.getOutputStream();

			byte[] bytes = new byte[0xffff];
			InputStream is = new FileInputStream(file);
			int b = 0;
			while ((b = is.read(bytes, 0, 0xffff)) > 0) {
				out.write(bytes, 0, b);
			}
			is.close();
			out.flush();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
