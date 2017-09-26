package util;

import java.io.FileInputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;

/**音乐播放器*/
public class Music  implements Runnable {
	//要播放的文件
	public String musicPath;
	//重复播放
	public boolean isLoop;
	//高级播放器
	public AdvancedPlayer ap;
	//子线程
	Thread son = new Thread(this);
	
	private Music() {
	}

	@Override
	public void run() {

		do {
			try{
				// 实例化ap
				ap = new AdvancedPlayer(  new FileInputStream(this.musicPath)  );
				// 播放
				ap.play();

			}catch(Exception e){}

		} while (isLoop);

	}

	//
	public void playMusic(String file ,boolean isloop){
         if(son.isAlive()){ // 如果是开启的就先关闭
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
