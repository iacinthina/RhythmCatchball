package org.rhythmcatchball.service;

import java.util.Random;
import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.Player;
import org.rhythmcatchball.gameplay.RoundManager;

public class AIController implements Controller {
	private Player player;
	private RoundManager gameInfo;
	private int framesLeft;
	private int nextCatch;
	private int nextBeat;
	private boolean catchCheck;
	private Random rand = new Random();

	public AIController(RoundManager gameInfo) {
		framesLeft = getBeatrate();
		nextBeat = framesLeft;
		nextCatch = 0;
		this.gameInfo = gameInfo;
		catchCheck = false;
	}
	
	@Override
	public void setPlayer(Player playertoset) {
		this.player = playertoset;
	}
	
	public void update(int beatcount) {
		if (player == null) {
			//System.out.println("player : " + player);
			return;
		}
		catchCheck = false;
		nextBeat--;
		framesLeft--;
		
		if (nextBeat == nextCatch+1 && nextCatch != 4){
			player.catchOnce();
			FloatMessage.create(player.xpos, player.ypos, "spr_message_"+Math.abs(nextCatch), false);
			catchCheck = true;
		}
		if (framesLeft <= 0) {
			framesLeft = getBeatrate()/2;
			nextBeat = framesLeft;
			resetCatchTiming();
			resetThrowTiming();
			//System.out.println("AI Catch() nextBeat("+nextBeat+") == nextCatch("+nextCatch+")");
		}
	}


	private void resetCatchTiming() {
		int[] aicomp = {0,0,1,1,1,1,2,2,2,3,4};
		int choose = random(aicomp.length);
		nextCatch = aicomp[choose];
		if (random(3) == 0 && nextCatch != 4) {
			nextCatch *= -1;
		}
		//System.out.println("resetCatchTiming() "+nextCatch);
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

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean catchCheck() {
		return catchCheck;
	}
	
	private int getBeatrate() {
		return GameManager.getref().modeBeatrate;
	}
}
