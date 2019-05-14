import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * GameSprite.java
 * @author pc1
 * @date   2019. 5. 14.
 * 게임 스프라이트 로더, 내부에 서브클래스가 필요함
 */
public class GameSprite {
	private static HashMap<String, GameSprite> sprites; //싱글톤 패턴, 모든 GameSprite마다 존재하면 안됨
	
	
	int xoffset;
	int yoffset;
	Image img;
	
	public GameSprite(int xoffset, int yoffset, Image img) {
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.img = img;
	}
	
	public int getxoff() {return xoffset;}
	public int getyoff() {return yoffset;}
	public Image getImage() {return img;}
	
	/**
	 * purpose : 
	 * mechanism : 
	 * comment : 
	 */
	public static boolean LoadImages() {
		/*
		 * 파일을 돌아가며 설정. Image[] sprite에 넣는다.
		 */
		GameManager gm = GameManager.getref();
		Image loadimg;
		GameSprite spr;
		String[] fnameList = {"sprites/spr_message_", ""};
		int[] subimg = {11, 0};
		int i, sprType;
		
		for(sprType = 0; sprType < 1; sprType++) {
			for(i=0; i<subimg[sprType]; i++)
			{
				loadimg = new ImageIcon(fnameList[sprType] +i+ ".png").getImage();
				spr = new GameSprite(loadimg.getWidth(gm)/2, loadimg.getHeight(gm)/2, loadimg);
				sprites.put("" + i, spr);
			}
		}
		return false;//성공시 true
	}
	
	/**
	 * purpose : 키에 따라 HashMap sprites에서 GameSprite를 불러오기
	 * mechanism : 
	 * comment : 
	 */
	public static GameSprite GetImage(String key) {
		GameSprite spr = null;
		return spr;
	}
}
