package game;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Director extends JPanel {
	
	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;
	private Director() {
		showScene(new WellcomeScene());
	}
	
	private static Director instence;
	public static Director getInstence() {
		if(instence == null)
			instence = new Director();
		
		return instence;
	}
	
	public void showScene(JPanel scene) {
		//设置边界布局布局
		this.setLayout(new BorderLayout());
		//移除原有场景
		this.removeAll();
		//添加新场景
		this.add(scene,BorderLayout.CENTER);
		//更新UI
		this.updateUI();
	}
}
