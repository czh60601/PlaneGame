package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//KeyAdapter ����������,��KeyListener��ʵ�ֳ�����
public class EnemyPlane implements Runnable {
	//�ƶ��ٶ�
	final private int speed = 1;

	//�ɻ��ͺ�
	private int planeCode;
	//Ƥ��
	private Image skin;
	//����״̬
	private boolean isLeft = false;
	private boolean isRight = false;

	//��С
	public int width;
	public int height;
	//����
	public int x;
	public int y;
	//��ײ���
	public Rectangle hitArea;
	//����ֵ
	public int hp;
	public int maxHp;
	//�ȼ�
	public int lv;
	//�Ƿ����
	public boolean isShoot = true;

	public EnemyPlane() {
		//����ɻ��ͺ�
		planeCode = (int)(Math.random()*11)+1;
		//��ȡƤ��
		skin = getSkin();
		//���ó�ʼλ��
		x = (int)(Math.random()*(Stage.WIDTH-20));
		y = -height;
		hitArea = new Rectangle(x, y, width, height);

		isLeft = Math.random()>0.85?true:false;

		if(!isLeft) {
			isRight = Math.random()>0.85?true:false;
		}

		lv = (int) (Math.random()*5)+1;

		maxHp = hp = lv*25;
		new Thread(this).start();
	}

	public Image getSkin() {
		try {
			Image img = ImageIO.read(new File("asstes/plane/d"+planeCode+".png"));
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

	public void darwPlane(Graphics g) {
		g.drawImage(skin, x, y, null);
	}

	public void move() {
		y += speed;

		if(isRight) {
			if(++x >= (Stage.WIDTH - width-20)){
				isRight = false;
				isLeft = true;
			}
		}

		if(isLeft) {
			if(--x <= 0){
				isRight = true;
				isLeft = false;
			}
		}

		hitArea.x = x;
		hitArea.y = y;
	}

	@Override
	public void run() {
		int sTime = 1;
		while(Stage.GAMEPLAY) {
			try {
				Thread.sleep(Stage.SPEED-5+lv/2);
				move();

				//�����ӵ�ƽ��
				if((++sTime%100/lv)==0 && !isShoot) {
					isShoot = true;
				}else {
					sTime = 8;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static int time = 0;
	public static int timeGo() {
		if(++time > Integer.MAX_VALUE-20) {
			time = 0;
		}

		return time;
	}
}
