package util;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/*这是一个图片工具类
 */
public class ImageHelp {
 
	//通过图片名称 获取图片
	public  static  Image getImageByName( String picName){
		       Image pic = null;
		       File  f = new File( picName  ); 
		       try{
		    	   
		         pic = ImageIO.read(f);
		         
		       }catch( Exception e){
		    	   System.out.println("图片加载失败");
		       }
		       
	           return pic;
	}
	// 修改尺寸
	public static Image editImage(Image img,int W,int H){
		
		    img =  img.getScaledInstance(W, H, Image.SCALE_DEFAULT);
		    
		    return img;
		
	}
	
    /**
     * 
     * @param name:文件的路径
     * @param W:指定宽
     * @param H :指定高
     * @return
     */
	public static Image getTargetImage(String name, int W,int H){
		// 根据图片名称获取图片
	     Image pic=	getImageByName(name);
	    // 缩放图片
	     pic = editImage(pic, W, H);
	     // 把图片传递出去
	     return pic;	     
	}

}
