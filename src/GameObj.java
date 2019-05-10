/**
 * GameObj.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임 진행의 컴포넌트를 맡음.
 */
public class GameObj {
	//그려지기 위한 위치. 충돌같은 판정도 없으니 접근성에 좀 여유를 둬도 될까?
	public float xpos;
	public float ypos;
	public int imageIndex;
	//스프라이트는 GameManager에서 관리하는데, xyoffset또한 거기서 관리할까?
	public int xoffset;
	public int yoffset;
	
	//직접 접근하지 못하도록 private로
	public Boolean alive;
	
	//이미지 그릴까 말까
	public Boolean visible;

	/**
	 * purpose : 오브젝트의 그래픽 표현
	 * mechanism : 
	 * comment : 사용하지 않을 듯
	 */
	public void Draw()
	{
		
	}
	
	/**
	 * purpose : GameManager에서 매 프레임마다 호출됨
	 * mechanism : 
	 * comment : 
	 */
	public void Update()
	{
		
	}
	
	/**
	 * purpose : 인스턴스가 더이상 필요없어져서 제거할때
	 * mechanism : GameManager에서 isAlive를 호출, false면 인스턴스 리스트에서 제외 
	 * comment : 
	 */
	public void Destroy() { alive = false; }
	public boolean isAlive() { return alive; }
	
	/**
	 * purpose : 현재 위치에서 x, y만큼 이동
	 * mechanism : 
	 * comment : FloatMessage가 사용할듯. 하지만 현재 FloatMessage는 값을 직접 변경하고 있음.
	 */
	public void ShiftPos(float x, float y)
	{
		xpos += x;
		ypos += y;
	}
	
	/**
	 * purpose : 현재 위치를 x, y로 이동
	 * mechanism : 
	 * comment : 
	 */
	public void SetPos(float x, float y)
	{
		xpos = x;
		ypos = y;
	}
	
	/**
	 * purpose : xpos, ypos를 중심점으로 만들기 위해 이미지 그려지는 위치를 옮겨주는 용도
	 * mechanism : 
	 * comment : 이미지와 함께 따라가기 때문에 언제 쓰일지 모르겠다.
	 */
	public void SetOffset(int x, int y)
	{
		//삭제 예정 
	}
	
	/**
	 * purpose : 박자 순간 경신되는 내용
	 * mechanism : 
	 * comment : 쓰일것임. 고민중
	 */
	public void Beat() {
		
	}
}
