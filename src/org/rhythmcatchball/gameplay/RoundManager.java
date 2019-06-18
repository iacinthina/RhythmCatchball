package org.rhythmcatchball.gameplay;

import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.service.Controller;

public class RoundManager extends GameObj {
	private static final int RETURN_DELAY = 300;
	
	private ArrayList<Player> entry;
	private int playtime;
	private int timelimit;
	private int beatcount;
	private int gameEnd;

	private ArrayList<Ball> tossable;
	private Ball tossed;
	private ArrayList<Ball> taken;

	private ArrayList<Controller> ctrls;

	private ArrayList<GameObj> displayTime;
	private int signalSkill;
	private int signalToss;
	private int signalGrab;
	private int signalGreat;
	private int signalReversal;

	private RoundManager() {
		entry = new ArrayList<>();
		playtime = -3;
		timelimit = 60;
		beatcount = 0;
		gameEnd = 0;

		tossable = new ArrayList<>();
		tossed = null;
		taken = new ArrayList<>();

		ctrls = new ArrayList<>();

		displayTime = GameObj.createMass(3, 0, 0);
		signalSkill = 0;
		signalToss = 0;
		signalGrab = 0;
		signalGreat = 0;
		signalReversal = 0;
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
		if (gameEnd > 0) {
			if (++gameEnd > RETURN_DELAY)
				GameManager.getref().restoreMainScreen();
			return;
		}
		int beatrate = getBeatrate();

		if (++beatcount >= beatrate) {
			beatcount = 0;
			onBeat();
			System.out.println("playtime = " + playtime);
			if (tossable.isEmpty()) {
				for (Player p : entry)
					p.onBeat();
				for (Ball b : taken)
					b.onBeat();
			}
		}
		signalToss();

		updateControllers();
	}

	@Override
	public void onBeat() {
		if (tossed != null) {
			tossable.add(tossed);
			tossed = null;
		}
		if (playtime < 0) {
			GameManager.playSound("snd_prebeat");
			Ball b = (Ball) Ball.create(xpos, ypos);
			b.reset(xpos, ypos, 2, this, GameManager.getref().getRoomHeight() * 0.3f);
			tossed = b;
		}
		if (playtime == 0) {
			GameManager.playSound("snd_start");
			for (Player player : entry)
				player.setActive(true);
		}
		if (playtime >= timelimit) {
			endGame();
		}
		GameManager.playSound("snd_beat");
		playtime++;
		updateDisplay();
	}

	public void initPlayers() {
		int xdiff = (int) (GameManager.getref().getRoomWidth() * 0.4);
		int ydiff = (int) (GameManager.getref().getRoomHeight() * 0.3);
		Player p1 = (Player) Player.create(xpos - xdiff, ypos + ydiff);
		Player p2 = (Player) Player.create(xpos + xdiff, ypos + ydiff);
		p1.opponent = p2;
		p2.opponent = p1;

		entry.add(p1);
		entry.add(p2);
		p1.setActive(false);
		p2.setActive(false);
	}

	private void endGame() {
		int score;
		int highest = -1;
		boolean draw = false;
		for (Player player : entry) {
			score = player.getScore();
			if (score == highest)
				draw = true;
			else if (score > highest) {
				highest = score;
			}
		}

		int msgtime = 1000;
		float vspd = -7f;
		float fric = 0.3f;
		for (Player player : entry) {
			if (player.getScore() == highest) {
				if (draw)
					FloatMessage.create(player.xpos, player.ypos, "endgame_draw", msgtime, vspd, fric);
				else
					FloatMessage.create(player.xpos, player.ypos, "endgame_win", msgtime, vspd, fric);
			} else {
				FloatMessage.create(player.xpos, player.ypos, "endgame_lose", msgtime, vspd, fric);
			}
			player.setActive(false);
		}
		gameEnd = 1;
		//setActive(false);
	}
	
	private void updateControllers() {
		// 짝수일때는 0부터, 홀수일때는 1부터
		Player target;
		Ball toss;
		Controller c;
		int i = (beatcount % 2 == 0) ? 0 : ctrls.size() - 1;
		int order = (i == 0) ? 1 : -1;
		int cnt = 0;
		// iteration
		for (; cnt < ctrls.size(); cnt++, i += order) {
			c = ctrls.get(i);
			if (c == null) continue;
			//Toss starting ball to opponent
			if (c.catchCheck() && !tossable.isEmpty()) {
				toss = tossable.get(0);
				if (toss != null) {
					tossable.remove(0);
					target = c.getPlayer().opponent;
					target.takeBall(toss);
					toss.reset(toss.xpos, toss.ypos, 0.5f, target, 0f);
					taken.add(toss);
				}
			}
			c.update(beatcount);
		}
	}

	public Player getEntry(int index) {
		if (entry.isEmpty()) {
			initPlayers();
			return entry.get(index);
		}
		return entry.get(index);
	}

	public void addController(Controller ctrl) {
		if (ctrl != null)
			ctrls.add(ctrl);
	}

	private void signalToss() {
		for (Player p : entry) {
			if (p.countBall() == 3)
				signalToss++;
		}
		if (signalToss > 80 && signalToss < 100 && playtime < 300) {
			// lettersign=3 sign_toss=100 alarm[2]=beat*3
		}
	}
	
	private void updateDisplay() {
		int realPlayTime = (playtime < 0) ? timelimit:Math.max(0, timelimit - playtime);
		float numberGap = 14;
		float xpos = this.xpos - numberGap*(displayTime.size()-1)/2;
		float ypos = this.ypos - 48;
		String numberFont = "number_russo_";
		GameObj.displayNumber(displayTime, realPlayTime, xpos, ypos, numberGap, numberFont);
	}
}
