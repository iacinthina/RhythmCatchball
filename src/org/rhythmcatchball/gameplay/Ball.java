package org.rhythmcatchball.gameplay;

/**
 * Ball.java
 * @author pc1
 * @date   2019. 5. 10.
 * 주고받는 공. 게임 시작에 생성되고 계속 유지됨
 */
public class Ball extends GameObj
{
	private static final int TIMEOVER = -6;
	private int framesLeft = 1;
	private int framesTotal = 1;
	private int caughtHold = 0;
	private float xstart;
	private float ystart;
	private float flyHeight;
	private GameObj toward;
	
	Checkout checkout;
	/**
	 * purpose : 공 움직이게 하기
	 * mechanism : 매 프레임 진행시간에 따라서 현재위치 계산함. SetPos로 이동
	 * comment : 
	 */
	@Override
	public void update() {
		if(caughtHold == 0){
			framesLeft -= 1;
			
			float lerpPos = (float)framesLeft / (float)framesTotal;
			
			//포물선 운동 표현
			if (toward != null) {
				xpos = lerp(toward.xpos, xstart, lerpPos);
				ypos = lerp(toward.ypos, ystart, lerpPos) - (float)(Math.sin(Math.PI * lerpPos) * flyHeight);
			}
		}
	}
	@Override
	public void onBeat() {
		if (caughtHold > 0) {
			//lame판정 띄워줘야 함... Player 인스턴스가, update때 이 공을 보고 얘는 글러먹었구나 라고 판단할 수 있어야 함.
			if (--caughtHold == 0) {
				checkout = Checkout.LAME;
				framesLeft = TIMEOVER;
			}
		}
	}

	public static GameObj create(float xpos, float ypos) {
		/**
		 * (반드시 register() 사용하고, Ball형 인스턴스 생성해서 리턴해야 한다.)
		 * 이미지 설정도 같이 해주세요
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		Ball ball = new Ball();
		ball.framesLeft = 60;
		ball.framesTotal = 60;
		ball.caughtHold = 0;
		ball.xstart = xpos;
		ball.ystart = ypos;
		ball.toward = null;
		ball.setSpriteKey("spr_ball");
		ball.checkout = null;
		ball.flyHeight = 60;
		
		register(ball);
		return ball;
	}
	

	/**
	 * purpose : 타이밍의 정확도 판정, 날아온 시간에 따라 오기로 한 박자가 다 됐는가 검사 
	 * mechanism : framesLeft가 0일때 제일 정확함
	 * comment : 좀더 보기 좋게 코드 수정했습니다.
	 */
	public Checkout judgement() {
		checkout = null;
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
		default:
			break;
		}
		return checkout;	//0이라면 무시
	}
	
	/**
	 * purpose : 공을 던지는 순간에 공의 파라미터를 변경함
	 * mechanism : 
	 * comment : 
	 */
	public void reset(float x, float y, int airTime, GameObj target) {
		xstart = x;
		ystart = y;
		framesTotal = airTime * getBeatrate();
		framesLeft = framesTotal;
		toward = target;
		caughtHold = 0;
		checkout = null;
		flyHeight = framesTotal;
		setActive(true); //중요
		setVisible(true);
	}
	
	public void reset(float x, float y, int airTime, GameObj target, float height) {
		xstart = x;
		ystart = y;
		framesTotal = airTime * getBeatrate();
		framesLeft = framesTotal;
		toward = target;
		caughtHold = 0;
		checkout = null;
		flyHeight = height;
		setActive(true); //중요
		setVisible(true);
	}
	
	/**
	 * purpose : 잡혔을때 호출하면 가시성이 좋다.
	 * mechanism : 
	 * comment : 
	 */
	public void caught(boolean isInList) {
		if (isInList) {
			if (checkout != null) {
				switch(checkout) {
				case EXACTLY:
					FloatMessage.create(xpos, ypos, "spr_message_0", true);
					break;
				case NEAT:
					FloatMessage.create(xpos, ypos, "spr_message_1", true);
					break;
				case COOL:
					FloatMessage.create(xpos, ypos, "spr_message_2", true);
					break;
				case LAME:
					FloatMessage.create(xpos, ypos, "spr_message_3", true);
					break;
				default:
					break;
				}
			}
			setActive(false);
			setVisible(false);
		} else {
			caughtHold = (framesLeft >= 0)? 2 : 1;
		}
	}

	/**
	 * purpose : lame 판정을 띄울 기준
	 * mechanism : 
	 * comment : 
	 */
	public boolean isOver() {
		return (framesLeft <= TIMEOVER);
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
