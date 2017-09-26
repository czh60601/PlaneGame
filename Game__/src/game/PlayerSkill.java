package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PlayerSkill {
	//�ƶ��ٶ�
	final private int speed = 20;

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

	public PlayerSkill(int planeCode,int lv,int x,int y) {
		//��ȡ�ɻ��ͺ�
		this.planeCode = planeCode;
		this.lv = lv;
		//��ȡƤ��
		skin = getSkin();
		//���ó�ʼλ��
		this.x = x-width/2;
		this.y = y;
		hitArea = new Rectangle(x,y,width,height);
		atk = lv*40;
	}

	public Image getSkin() {
		try {
			Image img = ImageIO.read(new File("asstes/bullent/img_bullet_sk"+planeCode+".png"));
			//��ȡԭ����С�Ķ���֮һ
			width = new ImageIcon(img).getIconWidth();
			height = new ImageIcon(img).getIconHeight();
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
