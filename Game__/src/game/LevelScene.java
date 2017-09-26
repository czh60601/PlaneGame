package game;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import util.EffectMusic;
import util.Movie;
import util.Music;

// 游戏主场景
public class LevelScene  extends JPanel implements Runnable{

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;
	//声明一个背景
	private Background bg;
	private Player player;
	private Music bgMusic;
	private EffectMusic sMusic;
	private EffectMusic kMusic;
	//玩家子弹集合 线程安全集合
	private CopyOnWriteArrayList<PlayerBullet> pBulls = new CopyOnWriteArrayList<PlayerBullet>();
	//敌机集合
	private CopyOnWriteArrayList<EnemyPlane> ePlanes = new CopyOnWriteArrayList<EnemyPlane>();
	//敌机子弹集合
	private CopyOnWriteArrayList<EnemyBullet> eBulls = new CopyOnWriteArrayList<EnemyBullet>();
	//玩家技能集合
	private CopyOnWriteArrayList<PlayerSkill> pSkills = new CopyOnWriteArrayList<PlayerSkill>();
	//爆炸动画集合
	private CopyOnWriteArrayList<Movie> mvs = new CopyOnWriteArrayList<Movie>();

	public LevelScene(int lv) {
		//初始化背景音乐
		bgMusic = Music.getMusic();
		bgMusic.playMusic("asstes/meida/mbg1.mp3", true);

		//初始化射击音效
		sMusic = new EffectMusic();
		//初始化击杀音效
		kMusic = new EffectMusic();

		//初始化背景
		bg= new Background(lv);

		//新建飞机
		player = new Player(Stage.getInstence().planeCode);
		//获取焦点
		Stage.getInstence().setFocusable(true);

		//改变游戏状态
		Stage.GAMEPLAY = true;
		new Thread(this).start();
	}

	//重写绘制UI方法
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Dialog",Font.PLAIN,12));
		//绘制图片
		bg.drawMap(g);
		drawBullet(g);
		drawEnemyPlane(g);
		drawBooms(g);
		drawEnemyBullet(g);
		drawPlayerSikll(g);
		//绘制飞机
		player.darwPlane(g);

		if(!Stage.GAMEPLAY) {
			drawGameOver(g);
		}
	}

	/**
	 * 添加玩家子弹
	 */
	public void addPlayerBullet() {
		PlayerBullet bl = new PlayerBullet(player.planeCode,player.lv,player.x+10,player.y);
		PlayerBullet br = new PlayerBullet(player.planeCode,player.lv,player.x+player.width-15,player.y);
		//添加的集合
		pBulls.add(bl);
		pBulls.add(br);
		sMusic.playMusic("asstes/meida/blast0.mp3", false);
	}

	/**
	 * 绘制玩家子弹
	 * @param g
	 */
	public void drawBullet(Graphics g) {
		for(PlayerBullet b:pBulls) {
			b.darwBullet(g);
		}
	}

	/**
	 * 移动玩家子弹
	 */
	public void moveBullet() {
		for(PlayerBullet b:pBulls) {
			b.move();
		}
	}

	/**
	 * 移除舞台外的玩家子弹
	 */
	public void removePlayerBullet() {
		for(int i=0;i<pBulls.size();i++) {
			if(pBulls.get(i).y<=0) {
				pBulls.remove(i);
			}
		}
	}

	/**
	 * 添加敌机
	 */
	public void addEnemyPlane() {
		//添加的集合
		ePlanes.add(new EnemyPlane());
	}

	/**
	 * 绘制敌机
	 * @param g
	 */
	public void drawEnemyPlane(Graphics g) {
		for(EnemyPlane p:ePlanes) {
			p.darwPlane(g);
		}
	}

	/**
	 * 添加敌机子弹
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
	 * 绘制敌机子弹
	 * @param g
	 */
	public void drawEnemyBullet(Graphics g) {
		for(EnemyBullet b:eBulls) {
			b.darwBullet(g);
		}
	}

	/**
	 * 移动敌机子弹
	 */
	public void moveEnemyBullet() {
		for(EnemyBullet b:eBulls) {
			b.move();
		}
	}

	/**
	 * 移除舞台外的敌机子弹
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
	 * 移除舞台外的敌机
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
	 * 绘制爆照动画
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
	 * 添加放的玩家技能
	 */
	public void addPlayerSkill() {
		pSkills.add(new PlayerSkill(player.planeCode, player.lv, player.x+player.width/2, player.y));
	}

	/**
	 * 绘制玩家技能
	 * @param g
	 */
	public void drawPlayerSikll(Graphics g) {
		for(PlayerSkill s:pSkills) {
			s.darwBullet(g);
		}
	}

	/**
	 * 移动玩家技能
	 */
	public void movePlayerSikll() {
		for(PlayerSkill s:pSkills) {
			s.move();
		}
	}

	/**
	 * 移除玩家技能
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
	 * 绘制结束字幕
	 */
	public void drawGameOver(Graphics g){
		g.setFont(new Font("微软雅黑",Font.ITALIC,45));
		g.drawString("GAME OVER", gX, --gY);
	}

	/**
	 * 碰撞检测方法
	 */
	public void hitTest() {
		//玩家子弹造成伤害检测
		for (int i = 0; i < pBulls.size(); i++) {
			PlayerBullet b = pBulls.get(i);
			for (int j = 0; j < ePlanes.size(); j++) {
				EnemyPlane e = ePlanes.get(j);

				if(b.hitArea.intersects(e.hitArea)) {
					e.hp -= b.atk;

					if(e.hp <= 0) {
						//添加爆炸东哈
						mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "爆炸 "));
						//播放爆炸有效
						kMusic.playMusic("asstes/meida/showbomb.mp3", false);
						//增加得分
						Player.SCORE += e.maxHp;
						//移除敌机
						ePlanes.remove(j);
					}
					//移除玩家子弹
					pBulls.remove(i);
				}
			}
		}

		//玩家技能伤害
		for (int i = 0; i < pSkills.size(); i++) {
			PlayerSkill s = pSkills.get(i);
			for (int j = 0; j < ePlanes.size(); j++) {
				EnemyPlane e = ePlanes.get(j);

				if(s.hitArea.intersects(e.hitArea)) {
					kMusic.playMusic("asstes/meida/showbomb.mp3", false);
					mvs.add(new Movie(s.x-s.width/2, s.y-s.height/2, s.width*2, s.height*2, 10, "e"));
					e.hp -= s.atk;

					if(e.hp <= 0) {
						mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "爆炸 "));
						kMusic.playMusic("asstes/meida/showbomb.mp3", false);
						Player.SCORE += e.maxHp;
						ePlanes.remove(j);
					}
				}
			}
		}

		//玩家飞机与敌机撞击检测 相互扣除当前生命值，为0死亡
		for (int j = 0; j < ePlanes.size(); j++) {
			EnemyPlane e = ePlanes.get(j);

			if(player.hitArea.intersects(e.hitArea)) {
				int ehp = e.hp;
				e.hp -= player.hp;
				player.hp -= ehp;

				if(e.hp <= 0) {
					mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "爆炸 "));
					kMusic.playMusic("asstes/meida/showbomb.mp3", false);
					Player.SCORE += e.maxHp*2;
					ePlanes.remove(j);
				}
			}
		}

		//敌机造成伤害检测
		for (int j = 0; j < eBulls.size(); j++) {
			EnemyBullet e = eBulls.get(j);
			if(player.hitArea.intersects(e.hitArea)) {
				player.hp -= e.atk;
				eBulls.remove(j);
			}
		}

		//玩家阵亡
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
	
	//全屏大招
//	public void BigBoom() {
//		for(int i=0;i<ePlanes.size();i++) {
//			EnemyPlane e = ePlanes.get(i);
//			mvs.add(new Movie(e.x, e.y, e.width, e.height, 6, "爆炸 "));
//		}
//		
//		ePlanes.clear();
//	}

	@Override
	public void run() {
		//玩家攻击频率
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
			//刷新场景
			this.repaint();
		}

		bgMusic.playMusic("asstes/meida/gameover.mp3", false);

		//重新启动标志，绘制结束游戏字幕
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

		//情况集合
		ePlanes.clear();
		eBulls.clear();
		pSkills.clear();
		pBulls.clear();
	}
}
