package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PlayerSkill {
	//移动速度
	final private int speed = 20;

	//皮肤
	private Image skin;

	//子弹型号
	public int planeCode;
	//大小
	public int width;
	public int height;
	//坐标
	public int x;
	public int y;
	//等级
	public int lv;
	//碰撞检测区
	public Rectangle hitArea;
	//伤害
	public int atk;

	public PlayerSkill(int planeCode,int lv,int x,int y) {
		//获取飞机型号
		this.planeCode = planeCode;
		this.lv = lv;
		//获取皮肤
		skin = getSkin();
		//设置初始位置
		this.x = x-width/2;
		this.y = y;
		hitArea = new Rectangle(x,y,width,height);
		atk = lv*40;
	}

	public Image getSkin() {
		try {
			Image img = ImageIO.read(new File("asstes/bullent/img_bullet_sk"+planeCode+".png"));
			//获取原本大小的二分之一
			width = new ImageIcon(img).getIconWidth();
			height = new ImageIcon(img).getIconHeight();
			//缩放
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
