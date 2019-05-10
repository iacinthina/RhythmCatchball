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
	 * @param x
	 * @param y
	 * 이미지 설정 필수
	 */
	public FloatMessage(float x, float y)
	{
		this.framesLeft = 30;
		this.vspeed = 10;
		this.friction = 2;
		xpos = x;
		ypos = y;
	}

	/**
	 * @param x
	 * @param y
	 * @param vspeed
	 * 또다른 생성자
	 */
	public FloatMessage(float x, float y, float vspeed)
	{
		
	}
	
	/**
	 * purpose : 메세지의 움직임
	 * mechanism : 매 프레임 vspeed만큼 이동하고, vspeed는 점점 0으로, 시간이 다되면 제거.
	 * comment : 
	 */
	public void Update()
	{
		framesLeft--;
		
		ypos = ypos + vspeed; //ShiftPos() 사용 안해도 괜찮을까?
		vspeed -= Integer.signum((int) vspeed) * Math.min(friction, Math.abs(vspeed)); //vspeed는 Update()가 호출될 때마다 0에 가까워진다.

		if(framesLeft <= 0) {
			Destroy();
		}
	}
}