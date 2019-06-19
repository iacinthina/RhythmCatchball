package org.rhythmcatchball.service;

import java.util.Random;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.RoundManager;

public class AIController extends Controller {
	private int framesLeft;
	private int nextCatch;
	private int nextBeat;
	private Random rand;

	public AIController() {
		super();
		framesLeft = getBeatrate();
		nextBeat = framesLeft;
		nextCatch = 0;
		rand = new Random();
	}
	
	@Override
	public void update() {
		if (player == null) {
			return;
		}
		catchCheck = false;
		updateDisplay();
		nextBeat--;
		framesLeft--;
		
		if (nextBeat == nextCatch+1 && nextCatch != 4){
			player.catchOnce();
			catchCheck = true;
		}
		if (framesLeft <= 0) {
			framesLeft = getBeatrate()/2;
			nextBeat = framesLeft;
			resetCatchTiming();
			resetThrowTiming();
		}
	}


	private void resetCatchTiming() {
		int[] aicomp = {0,0,1,1,1,1,2,2,2,3,4};
		int choose = random(aicomp.length);
		nextCatch = aicomp[choose];
		if (random(3) == 0 && nextCatch != 4) {
			nextCatch *= -1;
		}
	}
	
	private void resetThrowTiming() {
		int ballcount = random(player.countBall()+1);
		while (ballcount > 0) {
			player.readyToThrow(random(3));
			ballcount--;
		}
	}
	
	public void sync(int framesLeft) {
		this.framesLeft = framesLeft;
		nextBeat = framesLeft;
	}
	
	private int random(int max) {
		if (max <= 0) return 0;
		return this.rand.nextInt(max);
	}
}
