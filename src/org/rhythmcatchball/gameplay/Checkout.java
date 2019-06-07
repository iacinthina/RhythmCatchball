package org.rhythmcatchball.gameplay;

public enum Checkout {
	EXACTLY(50),
	NEAT(40),
	COOL(30),
	LAME(-30);
	
	private int score;
	
	Checkout(int score) { this.score = score; }
	public int getScore() { return score; }
}
