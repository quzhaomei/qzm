package com.sf.qzm.dto;

/**
 * 图片上传结果
 * @author Administrator
 *
 */
public class ImgUploadResultDTO implements SfDto {
	/**
	 * 图片上传是否失败。0-成功，1-失败
	 */
	private Integer error;
	/**
	 * 信息
	 */
	private String message;//
	/**
	 * 上传后的图片url
	 */
	private String url;
	/**
	 * 图片宽度
	 */
	private String width;
	
	/**
	 * 图片高度
	 */
	private String height;
	
	/**
	 * 对齐方式
	 */
	private String align;
	
	/**
	 * 图片标题
	 */
	private String title;
	
	public  String getWidth() {
		return width;
	}
	public  void setWidth(String width) {
		this.width = width;
	}
	public  String getHeight() {
		return height;
	}
	public  void setHeight(String height) {
		this.height = height;
	}
	public  String getAlign() {
		return align;
	}
	public  void setAlign(String align) {
		this.align = align;
	}
	public  String getTitle() {
		return title;
	}
	public  void setTitle(String title) {
		this.title = title;
	}
	public  String getMessage() {
		return message;
	}
	public  void setMessage(String message) {
		this.message = message;
	}
	public  Integer getError() {
		return error;
	}
	public  void setError(Integer error) {
		this.error = error;
	}
	public  String getUrl() {
		return url;
	}
	public  void setUrl(String url) {
		this.url = url;
	}
	
}
