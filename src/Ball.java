
public class Ball {
	public int framesLeft;
	public int framesTotal;
	public int caughtHold;
	public float xstart;
	public float ystart;
	public Player toward;
	
	public Ball() {
		//framesTotal :총 몇프레임 가야하는지
		//
	}
	
	public void Update() {
		framesLeft--;
		
		
	}
	
	public void ResetValue(int x, int y, int total) {
		xstart = x;
		ystart = y;
		framesTotal = total;
		framesLeft = framesTotal;
	}
	
	public int Judgement() {
		
	}
}
