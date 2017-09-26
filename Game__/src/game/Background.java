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
			//��ȡ����ͼ
			img = ImageIO.read(new File("asstes/plane/img_bg_level_"+lv+".jpg"));
			img = img.getScaledInstance(Stage.WIDTH, Stage.HEIGHT, BufferedImage.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//���������ƶ��߳�
		new Thread(this).start();
	}

	//��Ϸ����Ⱦ�߳�
	@Override
	public void run() {

		while(true) {
			try {
				Thread.sleep(Stage.SPEED);

				//ѭ������
				if(++y >= Stage.HEIGHT) {
					y = 0;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//���Ʊ����ķ���
	public void drawMap(Graphics g){
		//����ͼƬ
		g.drawImage(img, 0, y,null);
		g.drawImage(img, 0, y-Stage.HEIGHT,null);
	}

}

