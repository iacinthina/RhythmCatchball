
public class FloatMessage extends GameObj
{
	public int framesLeft;
	public float vspeed;
	public float friction;
	
	public FloatMessage(float x, float y)
	{
		this.framesLeft = 30;
		this.vspeed = 10;
		this.friction = 2;
		xpos = x;
		ypos = y;
	}
	
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