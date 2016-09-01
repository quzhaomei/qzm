package com.sf.qzm.util.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
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
	public static void compressPic(Image img, String path) {
		try {
			// 获得源文�?
			if (img == null) {
				return;
			}
			// 判断图片格式是否正确
			int newWidth = img.getWidth(null);
			int newHeight = img.getHeight(null);
			// 判断是否是等比缩�?
			// 为等比缩放计算输出的图片宽度及高�?
			BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

			/*
			 * Image.SCALE_SMOOTH 的缩略算�?生成缩略图片的平滑度�?优先级比速度�?生成的图片质量比较好 但�?度慢
			 */
			tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
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
	 * @param in
	 *            文件流
	 * @param isImg
	 *            是否是图片
	 * @param basePath
	 *            基础路径
	 * @param childPath
	 *            子目录
	 * @return
	 */
	public static ImgUploadResultDTO saveFile(MultipartFile file, boolean isImg, String basePath) {
		if (isImg) {
			return saveImg(file, basePath);
		} else {

		}
		return null;
	}

	/**
	 * 
	 * @param in
	 *            文件流
	 * @param basePath
	 *            基础路径
	 * @param childPath
	 *            子目录
	 * @return
	 */
	private static ImgUploadResultDTO saveImg(MultipartFile file, String basePath) {
		ImgUploadResultDTO ImgUploadResultDTO = new ImgUploadResultDTO();
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (bi == null) {
			ImgUploadResultDTO.setError(ImgUploadResultDTO.FAIL);
			ImgUploadResultDTO.setMessage("请输入正确格式的图片");
		} else {
			// 默认放在tomcat 根目录
			String imgBasePath = System.getProperty("catalina.base");// 绝对路径
			String imgUrl = "";// 图片url
			String imgRoot = SfContextUtils.getComponent(SystemSourceService.class).getByKey(Constant.IMG_ROOT_KEY);
			if (imgRoot != null) {
				imgBasePath += "/" + imgRoot + "/";
				imgUrl += "/" + imgRoot + "/";
			}
			// 图片目录按日期分类,一个月一个文件夹
			imgBasePath += new SimpleDateFormat("yyyy-MM").format(new Date()) + "/";
			imgUrl += new SimpleDateFormat("yyyy-MM").format(new Date()) + "/";
			if (basePath != null) {
				imgBasePath += basePath + "/";
				imgUrl += basePath + "/";
			}
			createDirs(imgBasePath);// 创建目标文件夹

			String uuid = createFileName(imgBasePath, file.getOriginalFilename());// 重新生成文件名字

			File aimFile = new File(imgBasePath, uuid);

			imgUrl += uuid;
			try {
				FileUtils.copyInputStreamToFile(file.getInputStream(), aimFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImgUploadResultDTO.setError(ImgUploadResultDTO.SUCCESS);
			ImgUploadResultDTO.setHeight(bi.getHeight() + "");
			ImgUploadResultDTO.setWidth(bi.getWidth() + "");
			ImgUploadResultDTO.setAlign("");
			ImgUploadResultDTO.setTitle("");
			ImgUploadResultDTO.setUrl(imgUrl);

		}

		return ImgUploadResultDTO;
	}

	private static void createDirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createDirs(file.getParentFile().getPath());
			}
			file.mkdirs();
		}
	}

	private static String createFileName(String basePath, String fileName) {
		fileName = getRandomName(fileName);// 重新生成文件名字
		File aimFile = new File(basePath, fileName);
		String fileTemp = fileName;
		int count = 0;

		while (aimFile.exists()) {
			count++;
			if (fileName.indexOf(".") != -1) {
				fileTemp = fileName.substring(0, fileName.indexOf(".")) + "(" + count + ")"
						+ fileName.substring(fileName.indexOf("."), fileName.length());
			} else {
				fileTemp = fileName + "(" + count + ")";
			}
			aimFile = new File(basePath, fileTemp);
		}
		return fileTemp;
	}

	private static String getRandomName(String name) {
		if (name.indexOf(".") != -1) {
			return UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
		} else {
			return UUID.randomUUID().toString();
		}
	}

	static Random random = new Random();



	/**
	 * 随机产生定义的颜色
	 * 
	 * @return
	 */
	private static Color getRandColor() {
		Random random = new Random();
		Color color[] = new Color[10];
		color[0] = new Color(32, 158, 25);
		color[1] = new Color(18, 42, 119);
		color[2] = new Color(131, 75, 8);
		color[3] = new Color(180, 02, 182);
		color[4] = new Color(171, 100, 85);
		return color[random.nextInt(5)];
	}

	/**
	 * 产生随机字体
	 * 
	 * @return
	 */
	private static Font getFont() {
		Random random = new Random();
		Font font[] = new Font[5];
		font[0] = new Font("Ravie", Font.BOLD, 17);
		font[1] = new Font("Antique Olive Compact", Font.BOLD, 15);
		font[2] = new Font("Forte", Font.BOLD, 16);
		font[3] = new Font("Wide Latin", Font.BOLD, 18);
		font[4] = new Font("Gill Sans Ultra Bold", Font.BOLD, 16);
		return font[random.nextInt(5)];
	}

	/**
	 * 生成图片
	 * 
	 * @throws IOException
	 */
	public static BufferedImage generImg(String randomStr,int width, int height) throws IOException {
		// 在内存中创建图像
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
		// 获取图形上下文
		Graphics2D g = (Graphics2D) bi.getGraphics();
		// 话边框
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		Font font=getFont();
		g.setFont(font);
		g.setColor(Color.BLACK);
		// 画认证码，每个认证码在不同的水平位置
		String str1[] = new String[randomStr.length()];
		for (int i = 0; i < str1.length; i++) {
			str1[i] = randomStr.substring(i, i + 1);
			int w = 0;
			int x = (i + 1) % 7;
			// 随机生成验证码字符水平偏移量
			if (x == random.nextInt(7)) {
				w = height/2+font.getSize()/2 - random.nextInt(5)-5;
			} else {
				w = height/2 +font.getSize()/2+ random.nextInt(5)-5;
			}
			// 随机生成颜色
			g.setColor(getRandColor());
			g.drawString(str1[i], font.getSize() * i + 15, w);
		}
		// 随机产生干扰点，并用不同的颜色表示，事图像的认证码不易被其他程序探测到
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			Color color = new Color(random.nextInt(155)+100, random.nextInt(155)+100, random.nextInt(155)+100);
			// 随机画各种颜色的线
			g.setColor(color);
			g.setFont(new Font("Forte", Font.ITALIC, 10));
			g.drawOval(x, y, 0, 0);
		}
		// 画干扰线
		for (int i = 0; i < 5; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			// 随机画各种颜色线
			g.setColor(color);
			g.drawLine(x, y, x1, y1);
		}
		// 图像生效
		g.dispose();
		// 输出页面
		return bi;
	}
}
