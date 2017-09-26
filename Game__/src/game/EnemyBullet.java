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
	//y移动速度
	final private int ySpeed = 10;

	//x移动速度
	private int xSpeed;
	//皮肤
	private ArrayList<Image> skin = new ArrayList<Image>();
	//当前皮肤图片编号
	private int iSkin = 0;
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

	public EnemyBullet(int lv,int x,int y) {
		this.lv = lv;
		//获取皮肤
		iniSkin();
		//设置初始位置
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
			//获取原本大小的二分之一
			width = new ImageIcon(img).getIconWidth()/2;
			height = new ImageIcon(img).getIconHeight()/2;
			//缩放
			img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

			skin.add(img);

			for(int i=2;i<7;i++) {
				img = ImageIO.read(new File("asstes/bullent02/bu02_0"+i+".png"));
				//缩放
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

