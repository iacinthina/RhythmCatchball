import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

/**
 * GameManager.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임 총괄. 시작부터 끝까지 항상 존재해야 한다.
 */

public class GameManager extends JFrame {
	private static GameManager singleton;
	public static GameManager getref() {
		if (singleton == null)
			singleton = new GameManager();
		return singleton;
	} //싱글톤 디자인 패턴
	
	public Graphics buffg;
	public Image buffImage;
	private ArrayList<GameObj> gameInst; //게임 진행중에 활성화된 오브젝트는 전부 여기로 들어간다.
	//private Image[] sprite; //게임 내에서 오브젝트가 가질 수 있는 모든 이미지를 담고있음. 저장방식이 애매하다.
	private HashMap<String, sprite> sprites;
	class sprite 
	{
		int xoffset;
		int yoffset;
		Image img;
		
		public sprite(int xoffset, int yoffset, Image img) {
			this.xoffset = xoffset;
			this.yoffset = yoffset;
			this.img = img;
		}
		
		public int getxoff() {return xoffset;}
		public int getyoff() {return yoffset;}
		public Image getImage() {return img;}
	}
	/**
	 * 생성자
	 */
	
	private GameManager() {
		gameInst = new ArrayList<GameObj>();
		sprites = new HashMap<String, sprite>();
		buffg = null;
		buffImage = null;
		LoadImages();
	}
	
	/**
	 * purpose : gui에 이미지 그리기
	 * mechanism : gameInst를 돌아가면서 스프라이트 그림
	 * comment : Draw에서 그릴거 다그려둠.
	 */
	public void Draw() {
		//게임오브젝트의 int가
		sprite spr = null; 
		for(GameObj o : gameInst) {
			//if (o.visible) //보여야 그릴 수 있다.(?)
			spr = sprites.get(o.imageIndex);
			buffg.drawImage(spr.img, (int)o.xpos-spr.xoffset, (int)o.ypos-spr.yoffset, this);
		}
		
	}
	
	
	/**
	 * purpose : 1프레임 진행
	 * mechanism : gameInst 내의 모든 GameObj의 Update를 실행한다. isAlive()가 false면 리스트에서 제거한다
	 * comment : 
	 */
	public void Update() {
		
	}
	
	/**
	 * purpose : 파일로부터 이미지를 읽어온다
	 * mechanism : sprite[i] = new ImageIcon(fname).getImage;
	 * comment : 인덱스 번호를 인식하는 방법 필요. 예를들면 sprite_name이라던지
	 */
	public boolean LoadImages() {
		/*
		 * 파일을 돌아가며 설정. Image[] sprite에 넣는다.
		 */
		Image loadimg;
		sprite spr;
		String[] fnameList = {"sprites/spr_message_", ""};
		int[] subimg = {11, 0};
		int i, sprType;
		
		for(sprType = 0; sprType < 1; sprType++) {
			for(i=0; i<subimg[sprType]; i++)
			{
				loadimg = new ImageIcon(fnameList[sprType] +i+ ".png").getImage();
				spr = new sprite(loadimg.getWidth(this)/2, loadimg.getHeight(this)/2, loadimg);
				sprites.put("" + i, spr);
			}
		}
		return false;//성공시 true
	}
	
	public void paint(Graphics g){
		int f_width = 640;
		int f_height = 360;
		buffImage = createImage(f_width, f_height); 
		buffg = buffImage.getGraphics();
		
		sprite spr = null;
		int xpos = 0;
		int ypos = 0;
		for(int i=0; i<11; i++) {
			//if (o.visible) //보여야 그릴 수 있다.(?)
			spr = sprites.get(""+i);
			xpos = 40+i*40;//-spr.img.getWidth(this)/2;
			ypos = 30+i*30;//-spr.img.getHeight(this)/2;
			buffg.drawImage(spr.img, xpos-spr.xoffset, ypos-spr.yoffset, this);
			System.out.println("is same? "+spr.xoffset+" ?= "+spr.img.getWidth(this)/2);
			System.out.println("is same? "+spr.yoffset+" ?= "+spr.img.getHeight(this)/2);
			buffg.drawLine(xpos, ypos - 30, xpos, ypos + 30);
			buffg.drawLine(xpos - 30, ypos, xpos + 30, ypos);

		}

		g.drawImage(buffImage, 0, 0, this);
	}
	
	/**
	 * purpose : 오브젝트 생성하고 이미지 설정하기
	 * mechanism : 
	 * comment : 이게 여기있어도 정말 괜찮을까? 이미지 타입 전달은 고민을 좀 해봐야할듯
	 */
	public void SetImage(GameObj o, int index) {
		o.imageIndex = index;
	}
	

	public static void main(String[] args)
	{
		GameManager gm = GameManager.getref();
		sprite spr = gm.sprites.get("0");
		int f_width = 640;
		int f_height = 360;
		//gm.buffImage = gm.createImage(f_width, f_height); 
		//gm.buffg = gm.buffImage.getGraphics();
		//gm.buffg.drawImage(spr.img, spr.xoffset, spr.yoffset,gm );
		//JFrame frame = new JFrame();
		gm.setSize(f_width, f_height);
		gm.setLayout(null);
		gm.setVisible(true);

		try {
			while(true) {
				GameManager.getref().repaint();
				Thread.sleep(20);
			}
		} catch (Exception e) {}
	}

}

