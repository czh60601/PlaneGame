package util;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

//����
public class Movie implements Runnable  {

	//���ŵ�ͼƬ
	public  List<Image> res = null;
	// ��������
	public int index = 0;
	// �����ܵ�֡��
	public int count = 22;
	// ���ڳߴ�
	public int W=30,H=30;
	// ����λ��
	public int x ,y ;
	// ����ǰ׺
	public String qz = "";
	

	//������
	public Movie(int x,int y,int W,int H,int frame,String qz){
		this.x = x;
		this.y = y;
		this.W = W;
		this.H = H;
		this.count = frame;
		this.qz = qz;
		//����
		getData();
		
		Thread son = new Thread( this );
		son.start();
	}
	/**��ȡͼƬ����*/
	public void getData(){
		res = new ArrayList();

		for(int i = 0; i< count; i++){
			Image pic = ImageHelp.getTargetImage("asstes/boom/"+qz+""+(i+1)+".png", W, H);
			// ��ӵ�res
			res.add(pic);
		}
	}
	/**���Ʊ�ը*/
	public void draw(Graphics g){
		g.drawImage(  res.get(index)  ,x , y, null);
	}
    /** �Ƿ񲥷����*/
	public boolean isOver(){
		return index == (count-1);
	}

	public void run() {

		do{
			try{

				Thread.sleep(60);
				index++;

			}catch(Exception e){}
			
		}while(index<this.count-1);

	}
}
