
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
				Destroy();
			}
		}
		else {
			//move
		}
		
		
	}
	
	public void ResetValue(float x, float y, int total, Player target) {
		xstart = x;
		ystart = y;
		framesTotal = total;
		framesLeft = framesTotal;
		toward = target;
		caughtHold = 0;
	}
	
	public int Judgement() {
		int returnScore = 0;
		switch(Math.abs(framesLeft)) {
		case 0: //framesLeft가 0일때, 1/60초만큼 지속된다. 
			//exactly
			returnScore = 50;
			break;
		case 1: //if(framesLeft == 1 || framesLeft == -1)
			//neat
			returnScore = 40;
			break;
		case 2: //if(framesLeft == 2 || framesLeft == -2)
			//cool
			returnScore = 30;
			break;
		case 3: //if(framesLeft == 3 || framesLeft == -3)
			//lame
			returnScore = -30;
			break;
		}
		return returnScore;
		/*if(framesLeft == 0)
			return 50;
		else if(framesLeft == 1 || framesLeft == -1)
			return 40;
		else if(framesLeft == 2 || framesLeft == -2)
			return 30;
		else if(framesLeft >= 3)
			return -30;
		else //framesLeft >= -3
			*/
	}
}
