package util;

import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

/**���ֲ�����*/
public class Music  implements Runnable {
	//Ҫ���ŵ��ļ�
	public String musicPath;
	//�ظ�����
	public boolean isLoop;
	//�߼�������
	public AdvancedPlayer ap;
	//���߳�
	Thread son = new Thread(this);
	
	private Music() {
	}

	@Override
	public void run() {

		do {
			try{
				// ʵ����ap
				ap = new AdvancedPlayer(  new FileInputStream(this.musicPath)  );
				// ����
				ap.play();

			}catch(Exception e){}

		} while (isLoop);

	}

	//
	public void playMusic(String file ,boolean isloop){
         if(son.isAlive()){ // ����ǿ����ľ��ȹر�
        	 son.stop();
         }
		
		this.musicPath = file;
		this.isLoop = isloop;
		
		son = new Thread(this);
		son.start();
	}
	
	private static Music instance ;
	public static Music  getMusic(){
		
		if(instance== null){
			instance = new Music();
		}
		
		return instance;	
	}

}
