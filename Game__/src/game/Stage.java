package game;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

//舞台
public class Stage extends JFrame{
	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;


	final public static int WIDTH = 500;
	final public static int HEIGHT = 800;
	
	//帧频
	public static int SPEED = 30;
	//游戏进程
	public static boolean GAMEPLAY = false;
	//导演
	public Director dir;
	
	//飞机型号
	public int planeCode = 2;
	//当前关卡数
	public int lv = 1;
	
	private Stage() {
		
		try {
			initStage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Stage instencec;
	public static Stage getInstence() {
		if(instencec == null) {
			instencec = new Stage();
		}
		
		return instencec;
	}
	
	//初始化
	public void initStage() throws IOException {
		//实例化导演
		dir = Director.getInstence();
		
		//尺寸
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.add(dir,BorderLayout.CENTER);
		
		//禁止修改窗口大小
		this.setResizable(false);
		this.setVisible(true);
	}
}