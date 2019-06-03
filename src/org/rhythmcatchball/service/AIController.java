package org.rhythmcatchball.service;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.Player;
import org.rhythmcatchball.gameplay.RoundManager;

public class AIController implements Controller {
	public Player player;
	public RoundManager gameInfo;
	private int framesLeft;
	private int nextCatch;
	private int nextBeat;

	@Override
	public void setPlayer(Player playertoset) {
		this.player = playertoset;
	}
	
	public AIController(RoundManager gameInfo) {
		framesLeft = getBeatrate();
		nextBeat = framesLeft;
		nextCatch = 0;
		this.gameInfo = gameInfo;
	}
	
	public void update(int beatcount) {
		if (player == null) {
			System.out.println("player : " + player);
			return;
		}
		if (nextBeat-1 == nextCatch+1 && nextCatch != 4){
			player.catchOnce();
			FloatMessage.create(player.xpos, player.ypos, "spr_message_"+Math.abs(nextCatch), false);
		}
		
		nextBeat--;
		framesLeft--;
		if (framesLeft <= 0) {
			framesLeft = getBeatrate()/2;
			nextBeat = framesLeft;
			resetCatchTiming();
			resetThrowTiming();
			System.out.println("AI Catch() nextBeat("+nextBeat+") == nextCatch("+nextCatch+")");
		}
	}


	private void resetCatchTiming() {
		int aicomp[] = {0,0,1,1,1,1,2,2,2,3,4};
		int choose = random(aicomp.length);
		nextCatch = aicomp[choose];
		if (random(3) == 0 && nextCatch != 4) {
			nextCatch *= -1;
		}
		System.out.println("resetCatchTiming() "+nextCatch);
	}
	
	private void resetThrowTiming() {
		int ballcount = random(player.countBall()+1);
		if (ballcount == 0) System.out.println("player.countBall() = " + player.countBall());
		while (ballcount > 0) {
			player.readyToThrow(random(2));
			ballcount--;
		}
	}
	
	public void sync(int framesLeft) {
		this.framesLeft = framesLeft;
		nextBeat = framesLeft;
	}
	
	private int random(int max) {
		return (int) (Math.random() * (max));
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
	
	private int getBeatrate() {
		return GameManager.getref().modeBeatrate;
	}
}
