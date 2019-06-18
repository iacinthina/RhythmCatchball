package org.rhythmcatchball.gameplay;
import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;

/**
 * GameObj.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임 진행의 컴포넌트를 맡음.
 */
public class GameObj {
	//게임 오브젝트의 중심 위치, 직접 접근 가능
	public float xpos = 0;
	public float ypos = 0;
	//스프라이트는 GameManager에서 관리함. 파일이름을 해시맵의 Key값으로 사용
	private String sprKey = "";
	//직접 접근하지 못하도록 private로
	private Boolean alive = true;
	//이미지 그릴까 말까
	private Boolean visible = true;
	//update 굴릴까 말까
	private Boolean active = true;

	/**
	 * purpose : GameManager에서 매 프레임마다 호출됨
	 * mechanism : 
	 * comment : 
	 */
	public void update() {
		//추후 구현 
	}
	
	/**
	 * purpose : 박자 순간 경신되는 내용
	 * mechanism : 
	 * comment : 쓰일것임. 고민중
	 */
	public void onBeat() {
		//추후 구현 
	}
	
	/**
	 * purpose : 생성자로는 좀 그래
	 * mechanism : 
	 * comment : 현재 구현된 method body는 안쓰일것.
	 */
	public static GameObj create(float xpos, float ypos) {
		GameObj instance = null;
		
		instance = new GameObj();
		instance.xpos = xpos;
		instance.ypos = ypos;
		instance.sprKey = "";
		instance.alive = true;
		instance.visible = true;
		instance.active = true;
		
		register(instance);
		
		return instance;
	}
	
	/**
	 * purpose : GameManger의 gameInstances 리스트에 인스턴스 추가
	 * mechanism : 
	 * comment : GameObj의 하위 오브젝트에서 GameManager에 접근시키고 싶지 않음.
	 */
	protected static void register(GameObj instance) {
		GameManager.getref().addInstance(instance);
	}
	
	/**
	 * purpose : 인스턴스가 더이상 필요없어져서 제거할때
	 * mechanism : GameManager에서 isAlive를 호출, false면 인스턴스 리스트에서 제외 
	 * comment : 
	 */
	public void destroy() { alive = false; }
	public boolean isAlive() { return alive; }

	/**
	 * purpose : 내가 보인다고? 지금은 어때
	 * mechanism : GameManager에서 draw를 호출할 때 getVisible이 true면 그려 
	 * comment : 
	 */
	public void setVisible(boolean visible) { this.visible = visible; }
	public boolean getVisible() { return visible; }

	/**
	 * purpose : 스프라이트 키
	 * mechanism : GameManager에서 draw를 호출할 때 getVisible이 true면 그려 
	 * comment : 
	 */
	public void setSpriteKey(String sprKey) { this.sprKey = sprKey; }
	public String getSpriteKey() { return sprKey; }
	
	/**
	 * purpose : 활동중이냐?
	 * mechanism : GameManager에서 update를 호출할 때 isActive가 true면 update와 onBeat 정상적으로 호출
	 * comment : 
	 */
	public void setActive(boolean active) { this.active = active; }
	public boolean isActive() { return active; }
	
	/**
	 * purpose : 하위 오브젝트에서 GameManager에 접근시키고 싶지 않아...
	 * mechanism : 
	 * comment : 
	 */
	public static int getBeatrate() {
		return GameManager.getref().modeBeatrate;
	}
	
	public static void displayNumber(ArrayList<GameObj> display, int number, float xpos, float ypos, float gap, String fontkey) {
		int i = 0;
		String score = ""+Math.abs(number);
		boolean negative = (number < 0);

		if (negative) i = 1;
		
		int decimal = Math.max(0, display.size() - score.length());
		for(; i < decimal; i++) {
			score = '0' + score;
		}
		
		if (negative) score = '-' + score;
		
		int index = 0;
		for(GameObj numb : display) {
			numb.xpos = xpos;
			numb.ypos = ypos;
			numb.setSpriteKey(fontkey+score.charAt(index));
			
			index++;
			xpos += gap;
		}
	}
	
	public static ArrayList<GameObj> createMass(int size, float xpos, float ypos) {
		if (size <= 0) return null;
		ArrayList<GameObj> mass = new ArrayList<>();
		for(int i = 0; i < size; i++)
			mass.add(GameObj.create(xpos, ypos));
		return mass;
	}
}
