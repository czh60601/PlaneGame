package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EnemyBullet {
	//y�ƶ��ٶ�
	final private int ySpeed = 10;

	//x�ƶ��ٶ�
	private int xSpeed;
	//Ƥ��
	private ArrayList<Image> skin = new ArrayList<Image>();
	//��ǰƤ��ͼƬ���
	private int iSkin = 0;
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

	public EnemyBullet(int lv,int x,int y) {
		this.lv = lv;
		//��ȡƤ��
		iniSkin();
		//���ó�ʼλ��
		this.x = x;
		this.y = y;
		hitArea = new Rectangle(x,y,width,height);
		atk = lv*3;
		
		if(Math.random()>0.5) {
			xSpeed = (int) (Math.random()*5);
		}else {
			xSpeed = (int) (-Math.random()*5);
		}
	}

	public void iniSkin() {
		try {
			Image img = ImageIO.read(new File("asstes/bullent02/bu02_01.png"));
			//��ȡԭ����С�Ķ���֮һ
			width = new ImageIcon(img).getIconWidth()/2;
			height = new ImageIcon(img).getIconHeight()/2;
			//����
			img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			skin.add(img);

			for(int i=2;i<7;i++) {
				img = ImageIO.read(new File("asstes/bullent02/bu02_0"+i+".png"));
				//����
				img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

				skin.add(img);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Image getSkin() {
		if(++iSkin == skin.size()) {
			iSkin = 0;
		}

		return skin.get(iSkin);
	}

	public void darwBullet(Graphics g) {
		g.drawImage(getSkin(), x, y, null);
	}

	public void move() {
		y += ySpeed*lv/10;
		x += xSpeed;

		hitArea.x = x;
		hitArea.y = y;
	}
}

