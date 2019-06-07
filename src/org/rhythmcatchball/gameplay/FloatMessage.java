package org.rhythmcatchball.gameplay;
/**
 * FloatMessage.java
 * @author pc1
 * @date   2019. 5. 10.
 * 여러가지 메세지를 띄울 애. 메세지는 이미지를 사용.
 */
public class FloatMessage extends GameObj
{
	private int framesLeft;
	private float vspeed;
	private float friction;
	
	/**
	 * purpose : 메세지의 움직임
	 * mechanism : 매 프레임 vspeed만큼 이동하고, vspeed는 점점 0으로, 시간이 다되면 제거.
	 * comment : 
	 */
	@Override
	public void update()
	{
		ypos += vspeed;
		reduceSpd();
		if(framesLeft-- <= 0) {
			destroy();
		}
	}
	
	/**
	 * purpose : (대충 인스턴스 생성한다는 내용. 다만 더 구체적으로)
	 * mechanism : 
	 * comment : 
	 */
	public static GameObj create(float xpos, float ypos, String sprKey, int framesLeft, float vspeed, float friction) {
		FloatMessage floatMessage = new FloatMessage();
		floatMessage.framesLeft = framesLeft;
		floatMessage.vspeed = vspeed;
		floatMessage.friction = friction;
		floatMessage.xpos = xpos;
		floatMessage.ypos = ypos;
		floatMessage.setSpriteKey(sprKey);
		
		register(floatMessage);
		return floatMessage;
	}
	
	/**
	 * purpose : (대충 인스턴스 생성한다는 내용)
	 * mechanism : 
	 * comment : isMove가 true면 preset 값 호출하는거로
	 */
	public static GameObj create(float xpos, float ypos, String sprKey, boolean isMove) {
		GameObj floatMessage = null;
		if (isMove) floatMessage = create(xpos, ypos, sprKey, 30, -5, 0.3f);
		else floatMessage = create(xpos, ypos, sprKey, 30, 0, 0);
		return floatMessage;
	}
	
	/**
	 * purpose : 가시성 좋게하기. update에서 불러주세요
	 * mechanism : 
	 * comment : 
	 */
	private void reduceSpd() {
		vspeed -= Integer.signum((int) vspeed) * Math.min(friction, Math.abs(vspeed)); //vspeed는 Update()가 호출될 때마다 0에 가까워진다.
	}
}