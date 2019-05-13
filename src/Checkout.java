
public enum Checkout {
	EXACTLY(50),
	NEAT(40),
	COOL(30),
	LAME(-30);
	
	private int score;
	
	Checkout(int Score) { this.score = Score; }
	public int getScore() { return score; }
}
