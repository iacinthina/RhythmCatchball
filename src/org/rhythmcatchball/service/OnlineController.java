package org.rhythmcatchball.service;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.gameplay.Player;

public class OnlineController extends Controller implements KeyListener {
	private Connection connection;
	private String data;
	private int[] keyval = new int[4];
	private boolean isKeyboardPlayer;
	private String sendData;
	
	public OnlineController(Connection connection){
		super();
		this.connection = connection;
		isKeyboardPlayer = false;
		sendData = "";
	}
	
	public OnlineController(Connection connection, int[] keySet){
		super();
		this.connection = connection;
		isKeyboardPlayer = true;
		sendData = "";
		if (keySet != null)
			keyval = keySet;
	}
	
	//본인이 1player, online상대의 key값은 
	public void datagot(String data) {
		char input;
		while(true) {
			if (data.length() > 0) {
				input = data.charAt(0);
				data = data.substring(1);
			} else
				break;
			System.out.println("test- processed char "+input+" ... left"+data);
			switch(input) {
			case 'a':
				player.readyToThrow(2);
				break;
			case 's':
				player.readyToThrow(1);
				break;
			case 'd':
				player.readyToThrow(0);
				break;
			default:
				player.catchOnce();
				catchCheck = true;
				break;
			}
		}
	}

	@Override
	public void update(int beatcount) {
		catchCheck = false;
		data = connection.recv();
		if (data != null) {
			if (!isKeyboardPlayer) datagot(data);
			System.out.println("connection.recv() = "+data);
		}
		if (sendData.length() > 0)
			connection.send(sendData);
		sendData = "";
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyboardPlayer) return;
		int keyCode = e.getKeyCode();
		System.out.println("keyCode = "+keyCode);
		if(keyCode == keyval[Key.LOW.getIndex()]) {
			player.readyToThrow(0);
			sendData = sendData + "d";
		}
		if (keyCode == keyval[Key.MIDDLE.getIndex()]) {
			player.readyToThrow(1);
			sendData = sendData + "s";
		}
		if(keyCode == keyval[Key.HIGH.getIndex()]) {
			player.readyToThrow(2);
			sendData = sendData + "a";
		}
		if(keyCode == keyval[Key.RECEIVE.getIndex()]) {
			player.catchOnce();
			catchCheck = true;
			sendData = sendData + "z";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
