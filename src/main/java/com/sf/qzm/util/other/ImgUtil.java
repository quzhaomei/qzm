package com.sf.qzm.util.other;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sf.qzm.dto.ImgUploadResultDTO;
import com.sf.qzm.service.SystemSourceService;
import com.sf.qzm.util.context.SfContextUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*******************************************************************************
 * 缩略图类（�?用） 本java类能将jpg、bmp、png、gif图片文件，进行等比或非等比的大小转换�?具体使用方法
 * compressPic(大图片路�?生成小图片路�?大图片文件名,生成小图片文�?生成小图片宽�?生成小图片高�?是否等比缩放(默认为true))
 */
public class ImgUtil {
	// 图片处理
	public static void compressPic(Image img,String path) {
		try {
			// 获得源文�?
			if(img==null){
				return;
			}
			// 判断图片格式是否正确
				int newWidth=img.getWidth(null);
				int newHeight=img.getHeight(null);
				// 判断是否是等比缩�?
					// 为等比缩放计算输出的图片宽度及高�?
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算�?生成缩略图片的平滑度�?优先级比速度�?生成的图片质量比较好 但�?度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(path);
				// JPEGImageEncoder可�?用于其他图片类型的转�?
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * @param in 文件流
	 * @param isImg 是否是图片
	 * @param basePath 基础路径
	 * @param childPath 子目录
	 * @return
	 */
	public static ImgUploadResultDTO saveFile(MultipartFile file,boolean isImg,String basePath) {
		if(isImg){
			return saveImg(file, basePath);
		}else{
			
		}
		return null;
	}
	/**
	 * 
	 * @param in 文件流
	 * @param basePath 基础路径
	 * @param childPath 子目录
	 * @return
	 */
	private static ImgUploadResultDTO saveImg(MultipartFile file,String basePath) {
		ImgUploadResultDTO ImgUploadResultDTO = new ImgUploadResultDTO();
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(bi==null){
			ImgUploadResultDTO.setError(ImgUploadResultDTO.FAIL);
			ImgUploadResultDTO.setMessage("请输入正确格式的图片");
		}else{
			//默认放在tomcat 根目录
			String imgBasePath=System.getProperty("catalina.base");//绝对路径
			String imgUrl="";//图片url
			String imgRoot=	SfContextUtils.getComponent(SystemSourceService.class).getByKey(Constant.IMG_ROOT_KEY);
			if(imgRoot!=null){
				imgBasePath+="/"+imgRoot+"/";
				imgUrl+="/"+imgRoot+"/";
			}
			//图片目录按日期分类,一个月一个文件夹
			imgBasePath+=new SimpleDateFormat("yyyy-MM").format(new Date())+"/";
			imgUrl+=new SimpleDateFormat("yyyy-MM").format(new Date())+"/";
			if(basePath!=null){
				imgBasePath+=basePath+"/";
				imgUrl+=basePath+"/";
			}
			createDirs(imgBasePath);//创建目标文件夹
			
			String uuid=createFileName(imgBasePath,file.getOriginalFilename());//重新生成文件名字
			
			File aimFile = new File(imgBasePath,uuid);
			
			imgUrl+=uuid;
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), aimFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImgUploadResultDTO.setError(ImgUploadResultDTO.SUCCESS);
			ImgUploadResultDTO.setHeight(bi.getHeight()+"");
			ImgUploadResultDTO.setWidth(bi.getWidth()+"");
			ImgUploadResultDTO.setAlign("");
			ImgUploadResultDTO.setTitle("");
			ImgUploadResultDTO.setUrl(imgUrl);
			
		}
		
		return ImgUploadResultDTO;
	}
	
	private  static void createDirs(String path){
		File file=new File(path);
		if(!file.exists()){
			if(!file.getParentFile().exists()){
				createDirs(file.getParentFile().getPath());
			}
			file.mkdirs();
		}
	}
	
	private static String createFileName(String basePath,String fileName){
		fileName=getRandomName(fileName);//重新生成文件名字
		File aimFile = new File(basePath,fileName);
		String fileTemp=fileName;
		int count=0;
		
		while(aimFile.exists()){
			count++;
			if(fileName.indexOf(".")!=-1){
				fileTemp=fileName.substring(0, fileName.indexOf("."))+"("+count+")"
						+fileName.substring(fileName.indexOf("."), fileName.length());
			}else{
				fileTemp=fileName+"("+count+")";
			}
			aimFile=new File(basePath,fileTemp);
		}
		return fileTemp;
	}
	private static String getRandomName(String name){
		if(name.indexOf(".")!=-1){
			return UUID.randomUUID().toString()+name.substring(name.lastIndexOf("."));
		}else{
			return UUID.randomUUID().toString();
		}
	}

}
