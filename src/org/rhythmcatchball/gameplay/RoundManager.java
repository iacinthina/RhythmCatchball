package org.rhythmcatchball.gameplay;

import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.service.Controller;

public class RoundManager extends GameObj {
	private ArrayList<Player> entry;
	private int playtime;
	private int timelimit;
	private int beatcount;
	
	private ArrayList<Ball> tossable;
	private Ball tossed;
	private ArrayList<Ball> taken;
	
	private ArrayList<Controller> ctrls;
	
	private int signal_skill;
	private int signal_toss;
	private int signal_grab;
	private int signal_great;
	private int signal_reversal;
	
	public RoundManager() {
		entry = new ArrayList<Player>();
		playtime = -3;
		timelimit = 60;
		beatcount = 0;
		
		tossable = new ArrayList<Ball>();
		tossed = null;
		taken = new ArrayList<Ball>();
		
		ctrls = new ArrayList<Controller>();
		
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
			for(Player p : entry)
				p.onBeat();
			for(Ball b : taken)
				b.onBeat();
		}
		
		//짝수일때는 0부터, 홀수일때는 1부터
		Controller c;
		int i, order, cnt;
		i = (beatcount % 2 == 0)? 0 : ctrls.size()-1;
		order = (i == 0)? 1 : -1;
		cnt = 0;
		//iteration
		for(;cnt < ctrls.size(); cnt++, i += order) {
			c = ctrls.get(i);
			if (c == null) {
				System.out.println("c = ctrls.get("+i+")");
				continue;
			}
			c.update(beatcount);
			if (/*c.catchCheck() && */!tossable.isEmpty()) {
				Ball toss = tossable.get(0);
				if (toss != null) {
					tossable.remove(0);
					Player target = c.getPlayer().opponent;
					target.takeBall(toss);
					toss.reset(toss.xpos, toss.ypos, 1, target);
					taken.add(toss);
				}
				System.out.println("toss = "+toss);
			}
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

		entry.add(P1);
		entry.add(P2);
		//P1.setActive(false);
		//P2.setActive(false);
	}
	
	public Player getEntry(int index) {
		if (entry.isEmpty()) {
			launchLocal();
			return entry.get(index);
		}
		return entry.get(index);
	}
	
	public void addController (Controller ctrl) {
		if (ctrl != null) ctrls.add(ctrl);
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
