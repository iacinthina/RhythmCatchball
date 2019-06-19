package org.rhythmcatchball.service;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.rhythmcatchball.core.Key;

public class KeyboardController extends Controller implements KeyListener {
	private int[] keyval;
	
	public KeyboardController(int[] keySet) {
		super();
		if (keySet != null)
			keyval = keySet;
		catchCheck = false;
	}

	//누른 키에 대해 작동 
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == keyval[Key.LOW.getIndex()]) {
			player.readyToThrow(0);
		}
		if (keyCode == keyval[Key.MIDDLE.getIndex()]) {
			player.readyToThrow(1);
		}
		if(keyCode == keyval[Key.HIGH.getIndex()]) {
			player.readyToThrow(2);
		}
		if(keyCode == keyval[Key.RECEIVE.getIndex()]) {
			player.catchOnce();
			catchCheck = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Override
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//Override
	}
}
