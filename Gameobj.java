public class GameObj {
	public float xpos;
	public float ypos;
	public int imageIndex;
	public int xoffset;
	public int yoffset;
	
	public void Draw(void)
	{
		
	}
	public void Update(void)
	{
		
	}
	public void Destroy(void)
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
