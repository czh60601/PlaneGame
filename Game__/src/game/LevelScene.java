package game;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import util.EffectMusic;
import util.Movie;
import util.Music;

// ��Ϸ������
public class LevelScene  extends JPanel implements Runnable{

	/**
	 * ���л�UID
	 */
	private static final long serialVersionUID = 1L;
	//����һ������
	private Background bg;
	private Player player;
	private Music bgMusic;
	private EffectMusic sMusic;
	private EffectMusic kMusic;
	//����ӵ����� �̰߳�ȫ����
	private CopyOnWriteArrayList<PlayerBullet> pBulls = new CopyOnWriteArrayList<PlayerBullet>();
	//�л�����
	private CopyOnWriteArrayList<EnemyPlane> ePlanes = new CopyOnWriteArrayList<EnemyPlane>();
	//�л��ӵ�����
	private CopyOnWriteArrayList<EnemyBullet> eBulls = new CopyOnWriteArrayList<EnemyBullet>();
	//��Ҽ��ܼ���
	private CopyOnWriteArrayList<PlayerSkill> pSkills = new CopyOnWriteArrayList<PlayerSkill>();
	//��ը��������
	private CopyOnWriteArrayList<Movie> mvs = new CopyOnWriteArrayList<Movie>();

	public LevelScene(int lv) {
		//��ʼ����������
		bgMusic = Music.getMusic();
		bgMusic.playMusic("asstes/meida/mbg1.mp3", true);

		//��ʼ�������Ч
		sMusic = new EffectMusic();
		//��ʼ����ɱ��Ч
		kMusic = new EffectMusic();

		//��ʼ������
		bg= new Background(lv);

		//�½��ɻ�
		player = new Player(Stage.getInstence().planeCode);
		//��ȡ����
		Stage.getInstence().setFocusable(true);

		//�ı���Ϸ״̬
		Stage.GAMEPLAY = true;
		new Thread(this).start();
	}

	//��д����UI����
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Dialog",Font.PLAIN,12));
		//����ͼƬ
		bg.drawMap(g);
		drawBullet(g);
		drawEnemyPlane(g);
		drawBooms(g);
		drawEnemyBullet(g);
		drawPlayerSikll(g);
		//���Ʒɻ�
		player.darwPlane(g);

		if(!Stage.GAMEPLAY) {
			drawGameOver(g);
		}
	}

	/**
	 * �������ӵ�
	 */
	public void addPlayerBullet() {
		PlayerBullet bl = new PlayerBullet(player.planeCode,player.lv,player.x+10,player.y);
		PlayerBullet br = new PlayerBullet(player.planeCode,player.lv,player.x+player.width-15,player.y);
		//��ӵļ���
		pBulls.add(bl);
		pBulls.add(br);
		sMusic.playMusic("asstes/meida/blast0.mp3", false);
	}

	/**
	 * ��������ӵ�
	 * @param g
	 */
	public void drawBullet(Graphics g) {
		for(PlayerBullet b:pBulls) {
			b.darwBullet(g);
		}
	}

	/**
	 * �ƶ�����ӵ�
	 */
	public void moveBullet() {
		for(PlayerBullet b:pBulls) {
			b.move();
		}
	}

	/**
	 * �Ƴ���̨�������ӵ�
	 */
	public void removePlayerBullet() {
		for(int i=0;i<pBulls.size();i++) {
			if(pBulls.get(i).y<=0) {
				pBulls.remove(i);
			}
		}
	}

	/**
	 * ��ӵл�
	 */
	public void addEnemyPlane() {
		//��ӵļ���
		ePlanes.add(new EnemyPlane());
	}

	/**
	 * ���Ƶл�
	 * @param g
	 */
	public void drawEnemyPlane(Graphics g) {
		for(EnemyPlane p:ePlanes) {
			p.darwPlane(g);
		}
	}

	/**
	 * ��ӵл��ӵ�
	 */
	public void addEnemyBullet() {
		for (int i = 0; i < ePlanes.size(); i++) {
			EnemyPlane p = ePlanes.get(i);
			if(p.isShoot) {
				eBulls.add(new EnemyBullet(p.lv, p.x+p.width/2, p.y));
				p.isShoot = false;
			}
		}
	}

	/**
	 * ���Ƶл��ӵ�
	 * @param g
	 */
	public void drawEnemyBullet(Graphics g) {
		for(EnemyBullet b:eBulls) {
			b.darwBullet(g);
		}
	}

	/**
	 * �ƶ��л��ӵ�
	 */
	public void moveEnemyBullet() {
		for(EnemyBullet b:eBulls) {
			b.move();
		}
	}

	/**
	 * �Ƴ���̨��ĵл��ӵ�
	 */
	public void removeEnemyBullet() {
		for(int i=0;i<eBulls.size();i++) {
			EnemyBullet e = eBulls.get(i);
			if(e.y >= Stage.HEIGHT && e.x <=0 && e.x >= Stage.WIDTH) {
				ePlanes.remove(i);
			}
		}
	}

	/**
	 * �Ƴ���̨��ĵл�
	 */
	public void removeEnemyPlane() {
		for(int i=0;i<ePlanes.size();i++) {
			EnemyPlane e = ePlanes.get(i);
			if(e.y >= Stage.HEIGHT) {

				ePlanes.remove(i);
			}
		}
	}

	/**
	 * ���Ʊ��ն���
	 * @param g
	 */
	public void drawBooms(Graphics g) {
		for (int i = 0; i < mvs.size(); i++) {
			Movie m = mvs.get(i);
			m.draw(g);
			if(m.isOver()) {
				mvs.remove(i);
			}
		}
	}

	/**
	 * ��ӷŵ���Ҽ���
	 */
	public void addPlayerSkill() {
		pSkills.add(new PlayerSkill(player.planeCode, player.lv, player.x+player.width/2, player.y));
	}

	/**
	 * ������Ҽ���
	 * @param g
	 */
	public void drawPlayerSikll(Graphics g) {
		for(PlayerSkill s:pSkills) {
			s.darwBullet(g);
		}
	}

	/**
	 * �ƶ���Ҽ���
	 */
	public void movePlayerSikll() {
		for(PlayerSkill s:pSkills) {
			s.move();
		}
	}

	/**
	 * �Ƴ���Ҽ���
	 */
	public void removePlayerSikll() {
		for(int i=0;i<pSkills.size();i++) {
			PlayerSkill s = pSkills.get(i);
			if(s.y <= 0) {
				pSkills.remove(i);
			}
		}
	}
	
	private int gX = Stage.WIDTH/2-150;
	private int gY = Stage.HEIGHT;
	/**
	 * ���ƽ�����Ļ
	 */
	public void drawGameOver(Graphics g){
		g.setFont(new Font("΢���ź�",Font.ITALIC,45));
		g.drawString("GAME OVER", gX, --gY);
	}

	/**
	 * ��ײ��ⷽ��
	 */
	public void hitTest() {
		//����ӵ�����˺����
		for (int i = 0; i < pBulls.size(); i++) {
			PlayerBullet b = pBulls.get(i);
			for (int j = 0; j < ePlanes.size(); j++) {
				EnemyPlane e = ePlanes.get(j);

				if(b.hitArea.intersects(e.hitArea)) {
					e.hp -= b.atk;

					if(e.hp <= 0) {
						//��ӱ�ը����
						mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "��ը "));
						//���ű�ը��Ч
						kMusic.playMusic("asstes/meida/showbomb.mp3", false);
						//���ӵ÷�
						Player.SCORE += e.maxHp;
						//�Ƴ��л�
						ePlanes.remove(j);
					}
					//�Ƴ�����ӵ�
					pBulls.remove(i);
				}
			}
		}

		//��Ҽ����˺�
		for (int i = 0; i < pSkills.size(); i++) {
			PlayerSkill s = pSkills.get(i);
			for (int j = 0; j < ePlanes.size(); j++) {
				EnemyPlane e = ePlanes.get(j);

				if(s.hitArea.intersects(e.hitArea)) {
					kMusic.playMusic("asstes/meida/showbomb.mp3", false);
					mvs.add(new Movie(s.x-s.width/2, s.y-s.height/2, s.width*2, s.height*2, 10, "e"));
					e.hp -= s.atk;

					if(e.hp <= 0) {
						mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "��ը "));
						kMusic.playMusic("asstes/meida/showbomb.mp3", false);
						Player.SCORE += e.maxHp;
						ePlanes.remove(j);
					}
				}
			}
		}

		//��ҷɻ���л�ײ����� �໥�۳���ǰ����ֵ��Ϊ0����
		for (int j = 0; j < ePlanes.size(); j++) {
			EnemyPlane e = ePlanes.get(j);

			if(player.hitArea.intersects(e.hitArea)) {
				int ehp = e.hp;
				e.hp -= player.hp;
				player.hp -= ehp;

				if(e.hp <= 0) {
					mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "��ը "));
					kMusic.playMusic("asstes/meida/showbomb.mp3", false);
					Player.SCORE += e.maxHp*2;
					ePlanes.remove(j);
				}
			}
		}

		//�л�����˺����
		for (int j = 0; j < eBulls.size(); j++) {
			EnemyBullet e = eBulls.get(j);
			if(player.hitArea.intersects(e.hitArea)) {
				player.hp -= e.atk;
				eBulls.remove(j);
			}
		}

		//�������
		if(player.hp <= 0) {
			mvs.add(new Movie(player.x, player.y, player.width, player.height, 20, ""));
			kMusic.playMusic("asstes/meida/showbomb.mp3", false);

			if(--Player.LIFE <= 0) {
				player.x = -200;
				Stage.GAMEPLAY = false;
			}
			else {
				player.reBirth();
			}
		}

	}
	
	//ȫ������
//	public void BigBoom() {
//		for(int i=0;i<ePlanes.size();i++) {
//			EnemyPlane e = ePlanes.get(i);
//			mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "��ը "));
//		}
//		
//		ePlanes.clear();
//	}

	@Override
	public void run() {
		//��ҹ���Ƶ��
		int shootTime = 9;

		while(Stage.GAMEPLAY){
			try {
				Thread.sleep(Stage.SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(player.isShoot) {
				if(++shootTime%10 == 0) {
					addPlayerBullet();
				}
			}else {
				shootTime = 9;
			}

			if(player.isSkill && player.isSkillReady()) {
				addPlayerSkill();
				player.useSkill();
//				BigBoom();
			}

			if(EnemyPlane.timeGo()%120 == 0) {
				addEnemyPlane();
			}

			addEnemyBullet();

			movePlayerSikll();
			moveBullet();
			moveEnemyBullet();

			hitTest();

			removeEnemyPlane();
			removePlayerBullet();
			removeEnemyBullet();
			//ˢ�³���
			this.repaint();
		}

		bgMusic.playMusic("asstes/meida/gameover.mp3", false);

		//����������־�����ƽ�����Ϸ��Ļ
		boolean b = true;
		while(b) {
			try {
				Thread.sleep(Stage.SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(gY <= Stage.HEIGHT/2) {
				b = false;
			}

			this.repaint();
		}

		//�������
		ePlanes.clear();
		eBulls.clear();
		pSkills.clear();
		pBulls.clear();
	}
}
