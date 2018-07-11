package com.jiajie.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ChartGraphics {

 BufferedImage image;

public void createImage(String fileLocation) {
  try {
   FileOutputStream fos = new FileOutputStream(fileLocation);
   BufferedOutputStream bos = new BufferedOutputStream(fos);
   JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
   encoder.encode(image);
   bos.close();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

 public void graphicsGeneration(String name, String id, String classname,String hg,
   String imgurl) {

  int imageWidth = 450;// 图片的宽度

  int imageHeight = 120;// 图片的高度

  image = new BufferedImage(imageWidth, imageHeight,
    BufferedImage.TYPE_INT_RGB);
  Graphics graphics = image.getGraphics();
  Color c = new Color(238,245,252);
  graphics.setColor(c);
  graphics.fillRect(0, 0, imageWidth, imageHeight);
  graphics.setColor(Color.BLACK);
  graphics.setFont(new Font("宋体",Font.BOLD,20));
  graphics.drawString("姓名:        " + name, 10, 20);
  graphics.drawString("身份证件号:  " + id, 10, 60);
  graphics.drawString("成绩:        " + classname, 10, 100);
 
  // ImageIcon imageIcon = new ImageIcon(imgurl);
  // graphics.drawImage(imageIcon.getImage(), 230, 0, null);

  // 改成这样:
  BufferedImage bimg = null;
  try {
	  File file = new java.io.File(imgurl);
	  if(file.exists()){
		  file.delete();
	  }
   bimg = javax.imageio.ImageIO.read(file);
  } catch (Exception e) {
  }

  if (bimg != null)
   graphics.drawImage(bimg, 230, 0, null);
  graphics.dispose();

  createImage(imgurl);

 } 
}