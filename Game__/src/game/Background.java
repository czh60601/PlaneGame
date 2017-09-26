package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background implements Runnable{
	private int y = 0;
	private Image img;

	public Background(int lv) {
		try {
			//获取背景图
			img = ImageIO.read(new File("asstes/plane/img_bg_level_"+lv+".jpg"));
			img = img.getScaledInstance(Stage.WIDTH, Stage.HEIGHT, BufferedImage.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//启动背景移动线程
		new Thread(this).start();
	}

	//游戏的渲染线程
	@Override
	public void run() {

		while(true) {
			try {
				Thread.sleep(Stage.SPEED);

				//循环背景
				if(++y >= Stage.HEIGHT) {
					y = 0;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//绘制背景的方法
	public void drawMap(Graphics g){
		//画出图片
		g.drawImage(img, 0, y,null);
		g.drawImage(img, 0, y-Stage.HEIGHT,null);
	}

}

