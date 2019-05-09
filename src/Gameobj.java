public class GameObj {
	public float xpos;
	public float ypos;
	public int imageIndex;
	public int xoffset;
	public int yoffset;
	public Boolean alive;
	
	public void Draw()
	{
		
	}
	public void Update()
	{
		
	}
	public void Destroy()
	{
		alive = false;
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
		//삭제 예정 
	}
}
