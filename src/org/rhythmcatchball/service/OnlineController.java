package org.rhythmcatchball.service;

import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.gameplay.Player;

public class OnlineController implements Controller{
	private Player player;
	private Connection connection;
	private int keyCode;
	private String data;
	public int[] keyval = new int[4];
	
	public OnlineController(Connection connection){
		this.connection = connection;
	}

	@Override
	public void setPlayer(Player playertoset) {
		this.player = playertoset;
	}
	
	//본인이 1player, online상대의 key값은 
	public void datagot() {
		data = connection.receive();
		keyCode = Integer.parseInt(data);
		
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
		}
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean catchCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(int beatcount) {
		// TODO Auto-generated method stub
		
	}
}
