package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

//KeyAdapter ����������,��KeyListener��ʵ�ֳ�����
public class Player extends KeyAdapter implements Runnable {
	//�ƶ��ٶ�
	private int moveSpeed = 5;
	//Ƥ��
	private Image skin;
	//��С������ʾʣ����Ϸ����
	private Image lifeSkin;
	//����ͼ��
	private Image skillSkin;
	//������ȴʱ��
	private int maxSkillTime;
	//����״̬
	private boolean isUp = false;
	private boolean isDown = false;
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isReBirth = true;
	//�ɻ��ͺ�
	public int planeCode;
	//��С
	public int width;
	public int height;
	//����
	public int x;
	public int y;
	//��ײ���
	public Rectangle hitArea;
	//�ȼ�
	public int lv;
	public boolean isShoot = false;
	public boolean isSkill = false;
	public int hp;
	public int maxHp;
	//������ȴʱ��
	public int skillTime = 0;

	//�÷�
	public static int SCORE = 0;
	//ʣ����Ϸ����
	public static int LIFE = 3;
	//ʣ�༼���ͷŴ���
	public static int SKILL = 3;

	public Player(int planeCode) {
		//��ȡ�ɻ��ͺ�
		this.planeCode = planeCode;
		lv = 1;
		//��ȡƤ��
		skillSkin = getSkin(true);
		skin = getSkin(false);
		lifeSkin = skin.getScaledInstance(width/3, height/3, Image.SCALE_DEFAULT);

		maxHp = planeCode*3+110+50*lv;
		maxSkillTime = 100;

		reBirth();
		hitArea = new Rectangle(x, y, width, height);

		//��Ӱ�������
		Stage.getInstence().addKeyListener(this);
		new Thread(this).start();
	}

	/**
	 * ��ȡƤ��Ч��
	 * @param isSkill �Ƿ��ǻ�ȡ����ͼ��
	 * @return Image
	 */
	public Image getSkin(boolean isSkill) {
		try {
			Image img;

			if(!isSkill) {
				img = ImageIO.read(new File("asstes/myplane/img_plane_"+planeCode+lv+".png"));
			}else {
				img = ImageIO.read(new File("asstes/bullent/img_bullet_sk"+planeCode+".png"));
			}

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

		//���ұ߻��� ���Ѫ��

		//���ƻ�ɫʣ������ֵ
		g.setColor(Color.yellow);
		g.fillRect(5, 45, maxHp, 10);

		//���ƺ�ɫ�۳�����ֵ
		g.setColor(Color.red);
		g.fillRect(5+hp, 45, maxHp-hp,10);

		//����ʣ���������
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(getPlaneName(), 15, 19);

		//���Ƶ÷�
		g.setColor(Color.green);
		g.drawString("�÷֣�"+SCORE, maxHp-10, 35);

		//���Ƽ���
		for(int i=0;i<SKILL;i++) {
			g.drawImage(skillSkin, 5+i*15, 60, null);
		}

		//��������
		for(int i=0;i<LIFE;i++) {
			g.drawImage(lifeSkin, 5+i*width/2, 40-height/3, null);
		}
	}

	public String getPlaneName() {
		switch(planeCode) {
		default:
			return planeCode+"�Ż���";
		}
	}

	public void move() {
		if(isReBirth) {
			if((y -= moveSpeed) <= 600) {
				isReBirth = false;
			}
		}

		if(isUp && y > 0) {
			y -= moveSpeed;
		}

		if(isDown && y < (Stage.HEIGHT - height*2)) {
			y += moveSpeed;
		}

		if(isRight && x < (Stage.WIDTH - width-10)) {
			x += moveSpeed;
		}

		if(isLeft && x > 0) {
			x -= moveSpeed;
		}

		hitArea.x = x;
		hitArea.y = y;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W: {
			isUp = true;
		}break;

		case KeyEvent.VK_S: {
			isDown = true;
		}break;

		case KeyEvent.VK_A: {
			isLeft = true;
		}break;

		case KeyEvent.VK_D:{
			isRight = true;
		}break;

		case KeyEvent.VK_J:{
			isShoot = true;
		}break;

		case KeyEvent.VK_K:{
			isSkill = true;
		}break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W: {
			isUp = false;
		}break;

		case KeyEvent.VK_S: {
			isDown = false;
		}break;

		case KeyEvent.VK_A: {
			isLeft = false;
		}break;

		case KeyEvent.VK_D: {
			isRight = false;
		}break;

		case KeyEvent.VK_J:{
			isShoot = false;
		}break;

		case KeyEvent.VK_K:{
			isSkill = false;
		}break;
		}
	}

	public void reBirth() {
		hp = maxHp;
		x =  Stage.WIDTH/2 - width/2;
		y = Stage.HEIGHT + 100;
		SKILL = 3;
		skillTime = 0;

		isReBirth = true;
	}

	public boolean isSkillReady() {
		return skillTime == 0 && SKILL != 0?true:false;
	}

	public void useSkill() {
		skillTime = maxSkillTime;
		SKILL--;
	}

	@Override
	public void run() {
		while(Stage.GAMEPLAY) {
			try {
				Thread.sleep(Stage.SPEED);
				move();

				if(!isSkillReady()) {
					skillTime--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
