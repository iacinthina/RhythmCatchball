import java.util.ArrayList;
import java.util.List;

public class Player extends GameObj {
	public Player opponent;
	private Ball[] throwQueue;
	private List<Ball> catchQueue;
	private List<Ball> ballsGot;
	public int score;
	private int combo;
	private int comboLevel;
	
	public Player() {
		opponent = null;
		throwQueue = new Ball[3];
		List<Ball> catchQueue = new ArrayList<Ball>();
		List<Ball> ballsGot = new ArrayList<Ball>();
		score = 0;
		combo = 0;
		comboLevel = 0;
	}
	
	public void Catch() {
		int precision = 0;
		boolean canGrab = true;
		
		for(Ball grab: catchQueue) {
			precision = grab.Judgement();
			if (canGrab) {
				ballsGot.add(grab);
				catchQueue.remove(grab);
				canGrab = false;
				getScore(precision);
			} else {
				grab.caughtHold = 1;
			}
		}
	}
	
	public void getScore(int precision) {
		switch(precision) {
		default:
			score += 3450;
			combo++;
			break;
		case -1:
			combo = 0;
			comboLevel = 0;
			break;
		}
		switch(comboLevel) {
		case 0:
			if (combo >= 5) {
				score += 25;
				comboLevel++;
			}
			break;
		case 1:
			if (combo >= 10) {
				score += 50;
				comboLevel++;
			}
			break;
		case 2:
			if (combo >= 20) {
				score += 100;
				comboLevel++;
			}
			break;
		case 3:
			if (combo >= 40) {
				score += 200;
				comboLevel++;
			}
			break;
		case 4:
			if (combo >= 70) {
				score += 300;
				comboLevel++;
			}
			break;
		}
	}
	
	public void ReadyThrow(int type) {
		if (throwQueue[type] == null) {
			Ball pickup = null;
			if(!ballsGot.isEmpty()) {
				pickup = ballsGot.get(0);
				ballsGot.remove(0);
			}
			if (pickup != null) {
				throwQueue[type] = pickup;
			}
		}
	}
	
	public void Throw(int type) {
		boolean check;
		Ball shoot;
		for(int i = 0; i < 3; i++) {
			shoot = throwQueue[i];
			if(shoot != null) {
				
				//초기화 
				throwQueue[i] = null;//throwQueue 비우기
				opponent.catchQueue.add(shoot);
			}
		}
	}
}
