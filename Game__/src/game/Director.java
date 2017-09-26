package game;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Director extends JPanel {
	
	/**
	 * ���л�UID
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
		//���ñ߽粼�ֲ���
		this.setLayout(new BorderLayout());
		//�Ƴ�ԭ�г���
		this.removeAll();
		//����³���
		this.add(scene,BorderLayout.CENTER);
		//����UI
		this.updateUI();
	}
}
