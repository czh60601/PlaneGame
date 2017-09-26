package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//KeyAdapter 按键适配器,即KeyListener的实现抽象类
public class EnemyPlane implements Runnable {
	//移动速度
	final private int speed = 1;

	//飞机型号
	private int planeCode;
	//皮肤
	private Image skin;
	//方向状态
	private boolean isLeft = false;
	private boolean isRight = false;

	//大小
	public int width;
	public int height;
	//坐标
	public int x;
	public int y;
	//碰撞检测
	public Rectangle hitArea;
	//生命值
	public int hp;
	public int maxHp;
	//等级
	public int lv;
	//是否射击
	public boolean isShoot = true;

	public EnemyPlane() {
		//随机飞机型号
		planeCode = (int)(Math.random()*11)+1;
		//获取皮肤
		skin = getSkin();
		//设置初始位置
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
			//获取原本大小的二分之一
			width = new ImageIcon(img).getIconWidth()/2;
			height = new ImageIcon(img).getIconHeight()/2;
			//缩放
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

				//发射子弹平率
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
