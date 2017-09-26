package game;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

//��̨
public class Stage extends JFrame{
	/**
	 * ���л�UID
	 */
	private static final long serialVersionUID = 1L;


	final public static int WIDTH = 500;
	final public static int HEIGHT = 800;
	
	//֡Ƶ
	public static int SPEED = 30;
	//��Ϸ����
	public static boolean GAMEPLAY = false;
	//����
	public Director dir;
	
	//�ɻ��ͺ�
	public int planeCode = 2;
	//��ǰ�ؿ���
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
	
	//��ʼ��
	public void initStage() throws IOException {
		//ʵ��������
		dir = Director.getInstence();
		
		//�ߴ�
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.add(dir,BorderLayout.CENTER);
		
		//��ֹ�޸Ĵ��ڴ�С
		this.setResizable(false);
		this.setVisible(true);
	}
}