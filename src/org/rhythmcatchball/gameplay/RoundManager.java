package org.rhythmcatchball.gameplay;

import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;

public class RoundManager extends GameObj {
	private ArrayList<Player> entry;
	private int playtime;
	private int timelimit;
	private int beatcount;
	
	private ArrayList<Ball> tossable;
	private Ball tossed;
	
	private int signal_skill;
	private int signal_toss;
	private int signal_grab;
	private int signal_great;
	private int signal_reversal;
	
	private RoundManager() {
		entry = new ArrayList<Player>();
		playtime = -3;
		timelimit = 60;
		beatcount = 0;
		
		tossable = new ArrayList<Ball>();
		tossed = null;
		
		signal_skill = 0;
		signal_toss = 0;
		signal_grab = 0;
		signal_great = 0;
		signal_reversal = 0;
	}
	
	public static GameObj create(float xpos, float ypos) {
		RoundManager instance = null;
		
		instance = new RoundManager();
		instance.xpos = xpos;
		instance.ypos = ypos;
		
		register(instance);
		
		return instance;
	}
	
	@Override
	public void update() {
		int beatrate = getBeatrate();
		
		if (++beatcount >= beatrate) {
			beatcount = 0;
			onBeat();
		}
	}
	
	@Override
	public void onBeat() {
		if (tossed != null) {
			tossable.add(tossed);
			tossed = null;
		}
		if (playtime++ < 0) {
			Ball b = (Ball) Ball.create(xpos, ypos);
			//b.setActive(false);
			b.reset(xpos, ypos, 2, this, GameManager.getref().getResHeight() * 0.3f);
			tossed = b;
		}
		if (playtime >= timelimit)
			;//gameend
		
	}
	
	public void launchLocal() {
		int xdiff = (int) (GameManager.getref().getResWidth() * 0.4);
		int ydiff = (int) (GameManager.getref().getResHeight() * 0.3);
		Player P1 = (Player) Player.create(xpos - xdiff, ypos + ydiff);
		Player P2 = (Player) Player.create(xpos + xdiff, ypos + ydiff);
		P1.opponent = P2;
		P2.opponent = P1;
		//P1.setActive(false);
		//P2.setActive(false);
	}
	
	private void signal_toss() {
		for(Player p : entry) {
			if (p.countBall() == 3)
				signal_toss++;
		}
		if (signal_toss > 80 && signal_toss < 100 && playtime < 300) {
			//lettersign=3 sign_toss=100 alarm[2]=beat*3
		}
	}
}
