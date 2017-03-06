package com.sf.qzm.jspTag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TimeStampTag extends SimpleTagSupport {
	private String pattern;
	private Long data;
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

	@Override
	public void doTag() throws JspException, IOException {
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		getJspContext().getOut().print(format.format(new Date(data)));
		super.doTag();
	}
	
}
