import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
			player.ReadyThrow(Key.LOW.index_num);
		}
		else if (e.getKeyCode() == keyval[1]) {
			player.ReadyThrow(Key.MIDDLE.index_num);
		}
		else if(e.getKeyCode() == keyval[2]) {
			player.ReadyThrow(Key.HIGH.index_num);
		}
		else if(e.getKeyCode() == keyval[3]) {
			player.ReadyThrow(Key.RECEIVE.index_num);
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
				
	}

	@Override
	public void keyReleased(KeyEvent e) {
				
	}
	
	
	
	
}
