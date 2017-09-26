package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PlayerBullet {
	//�ƶ��ٶ�
	final private int speed = 10;

	//Ƥ��
	private Image skin;

	//�ӵ��ͺ�
	public int planeCode;
	//��С
	public int width;
	public int height;
	//����
	public int x;
	public int y;
	//�ȼ�
	public int lv;
	//��ײ�����
	public Rectangle hitArea;
	//�˺�
	public int atk;

	public PlayerBullet(int planeCode,int lv,int x,int y) {
		//��ȡ�ɻ��ͺ�
		this.planeCode = planeCode;
		this.lv = lv;
		//��ȡƤ��
		skin = getSkin();
		//���ó�ʼλ��
		this.x = x;
		this.y = y;
		hitArea = new Rectangle(x,y,width,height);
		atk = lv*13;
	}

	public Image getSkin() {
		try {
			Image img = ImageIO.read(new File("asstes/bullent/img_bullet_lv"+lv+".png"));
			//��ȡԭ����С�Ķ���֮һ
			width = new ImageIcon(img).getIconWidth()/2;
			height = new ImageIcon(img).getIconHeight()/2;
			//����
			img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			return img;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void darwBullet(Graphics g) {
		g.drawImage(skin, x, y, null);
	}

	public void move() {
		y -= speed;

		hitArea.x = x;
		hitArea.y = y;
	}
}
