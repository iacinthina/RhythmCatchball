
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class GameManager extends JFrame {
	private static GameManager singleton;
	public static GameManager getref() {
		if (singleton == null)
			singleton = new GameManager();
		return singleton;
	}
	
	Graphics buffg;
	private ArrayList<GameObj> gameInst;
	private Image[] sprite;
	private GameManager() {
		gameInst = new ArrayList<GameObj>();
	}
	
	//gameInst를 돌아가면서 스프라이트 그림
	public void Draw() {
		//게임오브젝트의 int가
		for(GameObj o : gameInst) {
			buffg.drawImage(sprite[o.imageIndex], (int)o.xpos-o.xoffset, (int)o.ypos-o.yoffset, this);
		}
	}
	
	
	public void Update() {	
	}
	
	//파일로부터 이미지를 읽어온다
	public boolean LoadImages() {
		/*
		 * 파일을 돌아가며 설정. Image[] sprite에 넣는다.
		 */
		return true;
	}
	
	//이미지 타입 전달은 고민을 좀 해봐야할듯
	public void SetImage(GameObj o, int index) {
		o.imageIndex = index;
		o.xoffset = sprite[index].getWidth(singleton)/2;
		o.yoffset = sprite[index].getHeight(singleton)/2;
	}
}

