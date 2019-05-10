import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
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
	
	Graphics buffg;
	private ArrayList<GameObj> gameInst; //게임 진행중에 활성화된 오브젝트는 전부 여기로 들어간다.
	private Image[] sprite; //게임 내에서 오브젝트가 가질 수 있는 모든 이미지를 담고있음. 저장방식이 애매하다.
	private GameManager() {
		gameInst = new ArrayList<GameObj>();
	}
	
	/**
	 * purpose : gui에 이미지 그리기
	 * mechanism : gameInst를 돌아가면서 스프라이트 그림
	 * comment : 
	 */
	public void Draw() {
		//게임오브젝트의 int가
		for(GameObj o : gameInst) {
			//if (o.visible) //보여야 그릴 수 있다.(?)
			buffg.drawImage(sprite[o.imageIndex], (int)o.xpos-o.xoffset, (int)o.ypos-o.yoffset, this);
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
		return false;//성공시 true
	}
	
	/**
	 * purpose : 오브젝트 생성하고 이미지 설정하기
	 * mechanism : 
	 * comment : 이게 여기있어도 정말 괜찮을까? 이미지 타입 전달은 고민을 좀 해봐야할듯
	 */
	public void SetImage(GameObj o, int index) {
		o.imageIndex = index;
		o.xoffset = sprite[index].getWidth(singleton)/2;
		o.yoffset = sprite[index].getHeight(singleton)/2;
	}
}

