package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

//欢迎界面
public class WellcomeScene extends JPanel{

	/**
	 * 序列化UID
	 */
	private static final long serialVersionUID = 1L;

	private Image img1;
	private Image img2;
	private int x=0;

	public WellcomeScene() {
		this.setLayout(null);
		
		BufferedImage readImage;
		try {
			readImage = ImageIO.read(new File("asstes/ui/tencentlogo0.png"));
			//缩放
			img1 = readImage.getScaledInstance(Stage.WIDTH/2, Stage.HEIGHT, BufferedImage.SCALE_DEFAULT);

			readImage = ImageIO.read(new File("asstes/ui/tencentlogo1.png"));
			img2 = readImage.getScaledInstance(Stage.WIDTH/2, Stage.HEIGHT, BufferedImage.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JButton btn = new JButton("开始游戏");
		btn.setBounds(200, 600, 100, 30);
		btn.setBorder(null);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Stage.getInstence().dir.showScene(new LevelScene(Stage.getInstence().lv));
			}
		});
		this.add(btn);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		drawBackground(g);
	}

	private void drawBackground(Graphics g) {
		g.drawImage(img1, x, 0, null);
		g.drawImage(img2, Stage.WIDTH/2-x, 0, null);
	}
}
