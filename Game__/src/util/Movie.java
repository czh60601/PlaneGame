package util;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

//动画
public class Movie implements Runnable  {

	//播放的图片
	public  List<Image> res = null;
	// 播放索引
	public int index = 0;
	// 定义总的帧数
	public int count = 22;
	// 定于尺寸
	public int W=30,H=30;
	// 定义位置
	public int x ,y ;
	// 定义前缀
	public String qz = "";
	

	//构造器
	public Movie(int x,int y,int W,int H,int frame,String qz){
		this.x = x;
		this.y = y;
		this.W = W;
		this.H = H;
		this.count = frame;
		this.qz = qz;
		//调用
		getData();
		
		Thread son = new Thread( this );
		son.start();
	}
	/**获取图片数组*/
	public void getData(){
		res = new ArrayList();

		for(int i = 0; i< count; i++){
			Image pic = ImageHelp.getTargetImage("asstes/boom/"+qz+""+(i+1)+".png", W, H);
			// 添加到res
			res.add(pic);
		}
	}
	/**绘制暴炸*/
	public void draw(Graphics g){
		g.drawImage(  res.get(index)  ,x , y, null);
	}
    /** 是否播放完成*/
	public boolean isOver(){
		return index == (count-1);
	}

	public void run() {

		do{
			try{

				Thread.sleep(60);
				index++;

			}catch(Exception e){}
			
		}while(index<this.count-1);

	}
}
