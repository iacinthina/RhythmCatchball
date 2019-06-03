package org.rhythmcatchball.service;

import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.GameObj;
import org.rhythmcatchball.gameplay.Player;
import org.rhythmcatchball.gameplay.RoundManager;

public class AIController extends GameObj implements Controller {
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
		
		register(this);
	}
	
	public void update() {
		if (nextBeat-1 == nextCatch+1 && nextCatch != 4){
			player.catchOnce();
			FloatMessage.create(player.xpos, player.ypos, "spr_message_"+Math.abs(nextCatch), true);
			System.out.println("AI Catch() nextBeat("+nextBeat+") == nextCatch("+nextCatch+")");
		}
		if (nextCatch == 4)
			FloatMessage.create(player.xpos, player.ypos, "spr_message_"+Math.abs(nextCatch), true);
		
		nextBeat--;
		framesLeft--;
		if (framesLeft <= 0) {
			framesLeft = getBeatrate();
			nextBeat = framesLeft;
			resetCatchTiming();
			resetThrowTiming();
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
		int ballcount = player.countBall();
		while (ballcount > 0) {
			player.readyToThrow(random(2));
			ballcount--;
		}
	}
	
	public void sync(int framesLeft) {
		this.framesLeft = getBeatrate();
		nextBeat = framesLeft/2;
	}
	
	private int random(int max) {
		return (int) (Math.random() * (max-1));
	}
}
