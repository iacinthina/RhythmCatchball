package org.rhythmcatchball.gameplay;

/**
 * Ball.java
 * @author pc1
 * @date   2019. 5. 10.
 * 주고받는 공. 게임 시작에 생성되고 계속 유지됨
 */
public class Ball extends GameObj
{
	private int framesLeft;
	private int framesTotal;
	private boolean caughtHold;
	private float xstart;
	private float ystart;
	private Player toward;
	
	
	/**
	 * purpose : 공 움직이게 하기
	 * mechanism : 매 프레임 진행시간에 따라서 현재위치 계산함. SetPos로 이동
	 * comment : 
	 */
	public void update() {
		if(!caughtHold){
			float lerpPos = framesLeft/ framesTotal;
			
			//move
			//포물선 운동 표현			
			xpos = lerp(toward.xpos, xstart, lerpPos);
			ypos = (float) (lerp(toward.xpos, ystart, lerpPos) - Math.sin(Math.PI * lerpPos));
			
			framesLeft--;//framesLeft는 판정할때 쓰이기 때문에 고정된 상태에서 움직이면 안된다.
			//framesLeft가 -4 이하가 되면, 더이상 판정을 받을 수 없는 상태이다. 따라서 공을 되돌려주거나 그냥 추가하거나 처리를 해줘야 한다. > Update함수에서 공 놓친거 체크
			/*if(framesLeft <= -4) {
				toward.takeBall(this);
			}*/
			//리스트로 되돌려줘야 하기때문에 제거
		}
	}
	
	public void onBeat() {
		if (caughtHold) {
			//lame판정 띄워줘야 함... Player 인스턴스가, update때 이 공을 보고 얘는 글러먹었구나 라고 판단할 수 있어야 함.
		}
	}

	public static GameObj create(float xpos, float ypos) {
		// TODO Auto-generated method stub
		/**
		 * (반드시 register() 사용하고, Ball형 인스턴스 생성해서 리턴해야 한다.)
		 * 이미지 설정도 같이 해주세요
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		return create(xpos, ypos);
	}
	

	/**
	 * purpose : 타이밍의 정확도 판정, 날아온 시간에 따라 오기로 한 박자가 다 됐는가 검사 
	 * mechanism : framesLeft가 0일때 제일 정확함
	 * comment : 좀더 보기 좋게 코드 수정했습니다.
	 */
	public Checkout judgement() {
		Checkout checkout = null;

		switch(Math.abs(framesLeft)) {
		case 0: //framesLeft가 0일때, 1/60초만큼 지속된다. 
			checkout = Checkout.EXACTLY;
			break;
		case 1: //if(framesLeft == 1 || framesLeft == -1)
			checkout = Checkout.NEAT;
			break;
		case 2: //if(framesLeft == 2 || framesLeft == -2)
			checkout = Checkout.COOL;
			break;
		case 3: //if(framesLeft == 3 || framesLeft == -3)
			checkout = Checkout.LAME;
			break;
		}
		return checkout;	//0이라면 무시
	}
	
	/**
	 * purpose : 공을 던지는 순간에 공의 파라미터를 변경함
	 * mechanism : 
	 * comment : 
	 */
	public void reset(float x, float y, int airTime, Player target) {
		xstart = x;
		ystart = y;
		framesTotal = airTime * getBeatrate();
		framesLeft = framesTotal;
		toward = target;
		caughtHold = false;
		setActive(true); //중요
		setVisible(true);
	}
	
	/**
	 * purpose : 잡혔을때 호출하면 가시성이 좋다.
	 * mechanism : 
	 * comment : 
	 */
	public void caught(boolean isInList) {
		caughtHold = !isInList;
		if (isInList) {
			setActive(false);
			setVisible(false);
		}
	}

	/**
	 * purpose : 잡혔을때 호출하면 가시성이 좋다.
	 * mechanism : 
	 * comment : 
	 */
	public boolean isOver() {
		// TODO
		/**
		 * 시간이 다돼서 못잡게 됐을경우를 판별
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		return false;
	}
	
	/**
	 * purpose : 
	 * mechanism : a와 b사이에서 f(0-1)위치를 구한다. 0에 가까울수록 a, 1에 가까울수록 b
	 * comment : 
	 */
	private float lerp(float a, float b, float f) //f : 0 - 1 사이 값
	{
	  return (float) ((a * (1.0 - f)) + (b * f));
	}
}
