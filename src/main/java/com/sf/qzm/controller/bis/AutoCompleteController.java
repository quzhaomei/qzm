package com.sf.qzm.controller.bis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sf.qzm.dto.JsonDTO;


@Controller
@RequestMapping("auto")
public class AutoCompleteController {
	@Resource
    private RequestMappingHandlerMapping handlerMapping;
	
	private List<Book> list=new ArrayList<Book>();
	@RequestMapping("getQuestion")
	@ResponseBody
	public List<Book> getQuestion(){
		return list;
	}
	
	
	@RequestMapping("saveBook")
	@ResponseBody
	public JsonDTO test(Book book) {
		JsonDTO jsonDto=new JsonDTO();
		book.setCreateDate(new Date());
		list.add(book);
		jsonDto.setStatus(1).setMessage("保存成功");
		System.out.println(book);
		return jsonDto;
	}
}
class Book{
	private String name;
	private Integer price;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", price=" + price + "]";
	}
	
}
