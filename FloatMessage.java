
public class FloatMessage 
{
	public int framesLeft;
	public float vspeed;
	public float friction;
	
	public FloatMessage(float x, float y, boolean isMove)
	{
		if(isMove == true)
			this.vspeed = 5;
	}
	
	public void Update()
	{
		
	}
}
