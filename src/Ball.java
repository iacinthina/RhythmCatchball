
public class Ball extends GameObj
{
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
		
		if(caughtHold > 0){
			if (caughtHold-- <= 0) {
				//lame
				return -30;
				Destroy();
			}
		}
		else {
			//move
		}
		
		
	}
	
	public void ResetValue(int x, int y, int total) {
		xstart = x;
		ystart = y;
		framesTotal = total;
		framesLeft = framesTotal;
		caughtHold = 0;
	}
	
	public int Judgement() {
		if(framesLeft == 0)
			return 50;
		else if(framesLeft == 1 || framesLeft == -1)
			return 40;
		else if(framesLeft == 2 || framesLeft == -2)
			return 30;
		else if(framesLeft >= 3)
			return -30;
		else //framesLeft >= -3
			
	}
}
