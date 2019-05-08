public class Gameobj {
	public float xpos;
	public float ypos;
	public int imageIndex;
	public int xoffset;
	public int yoffset;
	
	public void Draw()
	{
		
	}
	public void Update()
	{
		
	}
	public void Destroy()
	{
		
	}
	public void ShiftPos(float x, float y)
	{
		xpos += x;
		ypos += y;
		
	}
	public void SetPos(float x, float y)
	{
		xpos = x;
		ypos = y;
	}
	public void SetOffset(int x, int y)
	{
		
	}
}
