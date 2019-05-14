package org.rhythmcatchball.gameplay;
/**
 * FloatMessage.java
 * @author pc1
 * @date   2019. 5. 10.
 * 여러가지 메세지를 띄울 애. 메세지는 이미지를 사용.
 */
public class FloatMessage extends GameObj
{
	public int framesLeft;
	public float vspeed;
	public float friction;
	
	
	/**
	 * purpose : 메세지의 움직임
	 * mechanism : 매 프레임 vspeed만큼 이동하고, vspeed는 점점 0으로, 시간이 다되면 제거.
	 * comment : 
	 */
	public void update()
	{
		ypos += vspeed;
		reduceSpd();
		if(framesLeft-- <= 0) {
			destroy();
		}
	}
	
	public static GameObj create(float xpos, float ypos, String sprKey, int framesLeft, float vspeed, float friction) {
		// TODO Auto-generated method stub
		/**
		 * (대충 인스턴스 생성한다는 내용. 다만 더 구체적으로)
		 * 이미지 설정도 같이 해주세요
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		FloatMessage floatMessage = new FloatMessage();
		floatMessage.framesLeft = framesLeft;
		floatMessage.vspeed = vspeed;
		floatMessage.friction = friction;
		floatMessage.xpos = xpos;
		floatMessage.ypos = ypos;
		
		return floatMessage;
	}
	
	public static GameObj create(float xpos, float ypos, String sprKey, boolean isMove) {
		// TODO Auto-generated method stub
		/**
		 * (대충 인스턴스 생성한다는 내용)
		 * isMove가 true면 preset 값 호출하는거로
		 * 이미지 설정도 같이 해주세요
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		return create(xpos, ypos, sprKey, 30, 10, 2);
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