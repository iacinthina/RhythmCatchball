/**
 * Ball.java
 * @author pc1
 * @date   2019. 5. 10.
 * 주고받는 공. 게임 시작에 생성되고 계속 유지됨
 */
public class Ball extends GameObj
{
	public int framesLeft;
	public int framesTotal;
	public int caughtHold;
	public float xstart;
	public float ystart;
	public Player toward;
	
	
	/**
	 * 생성자는 게임 시작시 호출될 것이기 때문에 특별히 뭐 할 필요는 없음. 에러에 주의
	 */
	public Ball() {
		
	}
	
	/**
	 * purpose : 공 움직이게 하기
	 * mechanism : 매 프레임 진행시간에 따라서 현재위치 계산함. SetPos로 이동
	 * comment : 
	 */
	public void Update() {
		
		if(caughtHold > 0){
			if (caughtHold-- <= 0) {
				//lame
				Destroy();
			}
		}
		else {
			float lerpPos = framesLeft/ framesTotal;
			
			//move
			//포물선 운동 표현			
			xpos = lerp(toward.xpos, xstart, lerpPos);
			ypos = (float) (lerp(toward.xpos, xstart, lerpPos) - Math.sin(Math.PI * lerpPos));
			
			framesLeft--;//framesLeft는 판정할때 쓰이기 때문에 고정된 상태에서 움직이면 안된다.
			//framesLeft가 -4 이하가 되면, 더이상 판정을 받을 수 없는 상태이다. 따라서 공을 되돌려주거나 그냥 추가하거나 처리를 해줘야 한다. > Update함수에서 공 놓친거 체크
			if(framesLeft <= -4) {
				toward.GiveBall(this);
			}
		}
	}
	
	/**
	 * purpose : 공을 던지는 순간에 공의 파라미터를 변경함
	 * mechanism : 
	 * comment : 
	 */
	public void ResetValue(float x, float y, int total, Player target) {
		xstart = x;
		ystart = y;
		framesTotal = total;
		framesLeft = framesTotal;
		toward = target;
		caughtHold = 0;
	}
	float lerp(float a, float b, float f) //f : 0 - 1 사이 값
	{
	  return (float) ((a * (1.0 - f)) + (b * f));
	}
	/**
	 * purpose : 타이밍의 정확도 판정, 날아온 시간에 따라 오기로 한 박자가 다 됐는가 검사 
	 * mechanism : framesLeft가 0일때 제일 정확함
	 * comment : 좀더 보기 좋게 코드 수정했습니다.
	 */
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
		return returnScore;	//0이라면 무시
	}
}
