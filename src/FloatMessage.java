
public class FloatMessage extends GameObj
{
	public int framesLeft;
	public float vspeed;
	public float friction;
	
	public FloatMessage(float x, float y)
	{
		this.framesLeft = 30;
		this.vspeed = 10;
		this.friction 2;
		xpos = x;
		ypos = y;
	}
	
	public void Update()
	{
		framesLeft--;
		
		if(vspeed > 0) {
			vspeed -= Integer.signum(vspeed) * Math.min(friction, Math.abs(vspeed)); //양수 1 음수 -1
			ypos = ypos + vspeed;
		}
		
		if(framesLeft <= 0) {
			Destroy();
		}
			
		Draw();
	}
}