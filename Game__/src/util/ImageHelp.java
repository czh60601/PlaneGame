package util;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/*����һ��ͼƬ������
 */
public class ImageHelp {
 
	//ͨ��ͼƬ���� ��ȡͼƬ
	public  static  Image getImageByName( String picName){
		       Image pic = null;
		       File  f = new File( picName  ); 
		       try{
		    	   
		         pic = ImageIO.read(f);
		         
		       }catch( Exception e){
		    	   System.out.println("ͼƬ����ʧ��");
		       }
		       
	           return pic;
	}
	// �޸ĳߴ�
	public static Image editImage(Image img,int W,int H){
		
		    img =  img.getScaledInstance(W, H, Image.SCALE_DEFAULT);
		    
		    return img;
		
	}
	
    /**
     * 
     * @param name:�ļ���·��
     * @param W:ָ����
     * @param H :ָ����
     * @return
     */
	public static Image getTargetImage(String name, int W,int H){
		// ����ͼƬ���ƻ�ȡͼƬ
	     Image pic=	getImageByName(name);
	    // ����ͼƬ
	     pic = editImage(pic, W, H);
	     // ��ͼƬ���ݳ�ȥ
	     return pic;	     
	}

}
