
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
		
		ypos = ypos + vspeed;
		vspeed -= Integer.signum((int) vspeed) * Math.min(friction, Math.abs(vspeed)); //+ : 1, -: -1

		if(framesLeft <= 0) {
			Destroy();
		}
	}
}