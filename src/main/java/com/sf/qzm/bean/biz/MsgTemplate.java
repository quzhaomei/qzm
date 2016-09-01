package com.sf.qzm.bean.biz;

public class MsgTemplate {
	/**
	 * 短信模版
	 * @author quzhaomei
	 *
	 */
		private String template;
		private String value;
		public String getTemplate() {
			return template;
		}
		public void setTemplate(String template) {
			this.template = template;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public MsgTemplate(String template, String value) {
			super();
			this.template = template;
			this.value = value;
		}
		
}
