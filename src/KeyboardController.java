import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.gameplay.Player;

public class KeyboardController implements Controller, KeyListener {
	public Player player;
	public int[] keyval = new int[4];
	
	public KeyboardController() {
		
	}
	
	//player설정 
	public void setPlayer(Player playertoset) {
		this.player = playertoset;
	}

	//누른 키에 대해 작동 
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == keyval[0]) {
			player.readyToThrow(Key.LOW.getIndex());
		}
		else if (e.getKeyCode() == keyval[1]) {
			player.readyToThrow(Key.MIDDLE.getIndex());
		}
		else if(e.getKeyCode() == keyval[2]) {
			player.readyToThrow(Key.HIGH.getIndex());
		}
		else if(e.getKeyCode() == keyval[3]) {
			player.readyToThrow(Key.RECEIVE.getIndex());
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
				
	}
	
	
	
	
}
