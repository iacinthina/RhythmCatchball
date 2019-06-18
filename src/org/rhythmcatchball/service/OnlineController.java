package org.rhythmcatchball.service;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.gameplay.Checkout;

public class OnlineController extends Controller implements KeyListener {
	private Connection connection;
	private String data;
	private int[] keyval = new int[4];
	private boolean isKeyboardPlayer;
	private String sendData;
	private boolean onlineCatchSync;
	
	public OnlineController(Connection connection){
		super();
		this.connection = connection;
		isKeyboardPlayer = false;
		sendData = "";
		onlineCatchSync = false;
	}
	
	public OnlineController(Connection connection, int[] keySet){
		super();
		this.connection = connection;
		isKeyboardPlayer = true;
		sendData = "";
		if (keySet != null)
			keyval = keySet;
	}

	@Override
	public void update(int beatcount) {
		catchCheck = false;
		updateDisplay();
		data = connection.recv();
		if (data != null) {
			//if (!isKeyboardPlayer)
			decrypt(data);
			System.out.println("connection.recv() = "+data);
		}
		
		/*Checkout result = player.onlineGetBallResult();
		
		if (result != null) {
			switch(result) {
			case EXACTLY:
				sendData = sendData + "E";
				break;
			case NEAT:
				sendData = sendData + "N";
				break;
			case COOL:
				sendData = sendData + "C";
				break;
			case LAME:
				sendData = sendData + "L";
				break;
			}
		}
		if (sendData.length() > 0) {
			System.out.println(this+".send : "+sendData);
		} else {
			System.out.println(this+".send : nothing to send");
		}
		char sendSign = Character.forDigit(player.onlineGetThrow(), 10 );*/
		encrypt();
		connection.send(sendData);
		//Character.forDigit(3, 10 );
		onlineCatchSync = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyboardPlayer) return;
		int keyCode = e.getKeyCode();
		System.out.println("keyCode = "+keyCode);
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
			//catchCheck = true;
			onlineCatchSync = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void decrypt(String data) {
		char input;
		while(true) {
			if (data.length() > 0) {
				input = data.charAt(0);
				data = data.substring(1);
			} else
				break;
			System.out.println("test- processed char "+input+" ... left"+data);
			if (input >= '0' && input <= 'f') {
				int value = input - '0';
				System.out.println("test ["+input+"] value = "+value);
				boolean row[] = new boolean[3];
				int bitmask = 1;
				for(int i = 0; i < row.length; i++) {
					//bitmask = (int) Math.pow(2, i);
					row[i] = ((value & bitmask) == 1);
					bitmask *= 2;
				}
				player.onlineSyncThrow(row);
			} else
			switch(input) {
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
	
	public void encrypt() {
		char throwQueue = '0';
		char checkout = '0';
		
		int sync = player.onlineGetThrow();
		throwQueue = Character.forDigit(sync, 16);
		
		Checkout result = player.onlineGetBallResult();
		if (result != null) {
			switch(result) {
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
