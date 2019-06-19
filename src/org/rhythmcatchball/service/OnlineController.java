package org.rhythmcatchball.service;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.gameplay.Checkout;

public class OnlineController extends Controller implements KeyListener {
	private Connection connection;
	private int[] keyval = new int[4];
	private boolean isKeyboardPlayer;
	private String sendData;
	private boolean onlineCatchSync;

	public OnlineController(Connection connection) {
		super();
		this.connection = connection;
		isKeyboardPlayer = false;
		sendData = "";
		onlineCatchSync = false;
	}

	public OnlineController(Connection connection, int[] keySet) {
		super();
		this.connection = connection;
		isKeyboardPlayer = true;
		sendData = "";
		if (keySet != null)
			keyval = keySet;
	}

	@Override
	public void update(int beatcount) {
		String data;
		catchCheck = false;
		updateDisplay();
		data = connection.recv();
		if (data != null) {
			decrypt(data);
		}

		encrypt();
		connection.send(sendData);
		onlineCatchSync = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyboardPlayer)
			return;
		int keyCode = e.getKeyCode();
		if (keyCode == keyval[Key.LOW.getIndex()]) {
			player.readyToThrow(0);
		}
		if (keyCode == keyval[Key.MIDDLE.getIndex()]) {
			player.readyToThrow(1);
		}
		if (keyCode == keyval[Key.HIGH.getIndex()]) {
			player.readyToThrow(2);
		}
		if (keyCode == keyval[Key.RECEIVE.getIndex()]) {
			player.catchOnce();
			onlineCatchSync = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing because of X and Y.
	}

	public void decrypt(String data) {
		char input;
		while (true) {
			if (data.length() > 0) {
				input = data.charAt(0);
				data = data.substring(1);
			} else {
				break;
			}

			if (input >= '0' && input <= 'f') {
				int value = input - '0';
				boolean[] row = new boolean[3];
				int bitmask = 1;
				for (int i = 0; i < row.length; i++) {
					row[i] = ((value & bitmask) == 1);
					bitmask *= 2;
				}
				player.onlineSyncThrow(row);
			} 
			else
			{
				switch (input) {
				case 'E':
					player.onlineProcessWaitingBall(Checkout.EXACTLY);
					break;
				case 'N':
					player.onlineProcessWaitingBall(Checkout.NEAT);
					break;
				case 'C':
					player.onlineProcessWaitingBall(Checkout.COOL);
					break;
				case 'L':
					player.onlineProcessWaitingBall(Checkout.LAME);
					break;
				default:
					catchCheck = true;
					break;
				}
			}
		}
	}

	public void encrypt() {
		char throwQueue;
		char checkout = '0';

		int sync = player.onlineGetThrow();
		throwQueue = Character.forDigit(sync, 16);

		Checkout result = player.onlineGetBallResult();
		if (result != null) {
			switch (result) {
			case EXACTLY:
				checkout = 'E';
				break;
			case NEAT:
				checkout = 'N';
				break;
			case COOL:
				checkout = 'C';
				break;
			case LAME:
				checkout = 'L';
				break;
			default:
				checkout = 'z';
				break;
			}
		} else {
			if (onlineCatchSync)
				checkout = 'z';
		}

		sendData = "" + throwQueue + checkout;
	}
}
