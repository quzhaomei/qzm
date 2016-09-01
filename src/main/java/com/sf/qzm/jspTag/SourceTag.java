package com.sf.qzm.jspTag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.sf.qzm.service.SystemSourceService;
import com.sf.qzm.util.context.SfContextUtils;

public class SourceTag extends SimpleTagSupport {
	private String key;
	private String def;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getDef() {
		return def;
	}
	public void setDef(String def) {
		this.def = def;
	}
	@Override
	public void doTag() throws JspException, IOException {
		String value=SfContextUtils.getComponent(SystemSourceService.class).getByKey(key);
		value=value==null?def:value;
		getJspContext().getOut().print(value);
		super.doTag();
	}
	
}
