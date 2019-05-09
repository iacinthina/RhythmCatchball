
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
	private ArrayList<Gameobj> gameInst;
	private Image[] sprite;
	private GameManager() {
		gameInst = new ArrayList<Gameobj>();
	}
	
	public void Draw() {
		for(Gameobj o : gameInst) {
			buffg.drawImage(sprite[o.image], (int)o.xpos-o.xoffset, (int)o.ypos-o.yoffset, this);
		}
	}
	
	public void Desrtoy() {	
	}	
}

