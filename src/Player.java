import java.util.ArrayList;
import java.util.List;

/**
 * Player.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임이 시작되면 생성하는 플레이어 오브젝트. 게임 진행중에 항상 2개 이상이 존재해야 한다.
 */
public class Player extends GameObj {
	public Player opponent; //상대방 플레이어. 내가 공을 던질 타겟(나한테 공을 던지는 대상은 알 수 없음)
	private Ball[] throwQueue; //박자가 경신되는 순간에, 이 배열에 있는 공을 위치에 따라 다른 속도로 던진다.
	private ArrayList<Ball> catchQueue; //내가 받을 공들. 상태를 체크할 수 있다.
	private ArrayList<Ball> ballsGot; //내가 가진 공들. throwQueue로만 이동할 수 있다.
	public int score; //점수. 출력할 수 있어야 한다
	private int combo; //놓치지만 않으면 하나씩 쌓인다.
	private int comboLevel; //콤보에 따른 보너스 점수를 얻기 위한 마커
	
	/**
	 * 생성자. 리스트 초기화
	 */
	public Player() {
		opponent = null;
		throwQueue = new Ball[3];
		catchQueue = new ArrayList<Ball>();
		ballsGot = new ArrayList<Ball>();
		score = 0;
		combo = 0;
		comboLevel = 0;
	}
	
	/**
	 * purpose : 잡기키를 누르면 공을 잡으려고
	 * mechanism : 나한테 오고있는 공을 모두 검사해서 받을지 안받을지 검사
	 * comment : 
	 */
	public void Catch() {
		int precision = 0; //잡은 타이밍에 따른 판정. 자료형 고민
		boolean canGrab = true; //한번에 하나의 공만 잡을 수 있다.
		
		//나한테 오고있는 모든 공을 검사
		for(Ball grab: catchQueue) {
			//공의 위치를 검사한다. 받지 못할경우엔 무시해야 함.
			precision = grab.Judgement();
			//if (precision != 0) 받지 못할경우에 무시하는 내용이 빠짐
			if (canGrab) {
				ballsGot.add(grab);
				catchQueue.remove(grab);
				canGrab = false;
				getScore(precision); //정확도에 따라 점수를 얻는다. 안좋은 판정을 받더라도
			} else {
				/*
				 * 두개 이상의 공이 한꺼번에 올때 멈추게 하기 위해서 caughtHold를 바꿔준다.
				 * caughtHold의 자료형과, 받기까지 유예시간을 설정해야 함.
				 * caughtHold를 boolean형으로 바꾸고, 박자가 경신되는 메서드 ex) Beat()를 만들어서
				 * caughtHold가 true면 Beat()메서드가 실행됐을 때 제거하는 방향도 괜찮을 것 같음.
				 * 그렇게하면 다음 박자 전까지 중복되는 공을 받아야 함
				 */
				grab.caughtHold = 1;
			}
		}
	}
	
	/**
	 * purpose : 판정에 따른 점수를 얻기 위함
	 * mechanism : 콤보에 따른 보너스 점수도 부여.
	 * comment : 현재 판정의 자료형을 고민중
	 */
	public void getScore(int precision) {
		int[] comboReq = {5, 10, 20, 40, 70, 99999};//각 index는 요구 콤보수를 뜻함
		int[] bonus = {25, 50, 100, 200, 300, 0}; //요구 콤보수에 도달했을 때 얻는 점수
		
		//판정에 따라 다른 점수. switch를 써야 할까?
		switch(precision) {
		default:
			/*
			 * 공을 받았을 경우, 판정에 따라 점수를 얻는다
			 * exact 50
			 * neat 40
			 * cool 30
			 */
			score += 3450;
			//콤보 하나 추가해주고 보너스 얻을 수 있는지 검사
			if (++combo >= comboReq[comboLevel]) {
				score += bonus[comboLevel];
				comboLevel = Math.max(comboLevel+1, 5);
			}
			break;
		case -1:
			//실패했을 경우 콤보와 콤보레벨 0으로 만들고, 점수를 30 깎는다
			//점수는 0 이하로 내려가지 않음.
			score = Math.max(score - 30, 0);
			combo = 0;
			comboLevel = 0; //콤보레벨도 초기화해서 5콤보를 두번째 달성했을 때도 점수부여. 이후도 마찬가지
			break;
		}
	}
	
	/**
	 * purpose : 던질 공을 준비하려고
	 * mechanism : 박자가 경신되는 순간에 던져질 것이기 때문에, 미리 준비만 해놓는다.
	 * comment : type을 3가지로 제한해야 함. throwQueue 배열 인덱스를 벗어나지 않게 하는것이 중요
	 * 또한 온라인 플레이시 어긋나지 않게 할 필요도 있다.
	 */
	public void ReadyThrow(int type) {
		//3종류는 한 박자에 각각 하나씩만 던질 수 있다. 이미 던질 준비가 돼있다면 다시 던질필요 없음
		if (throwQueue[type] == null) {
			Ball pickup = null;//던질공이 있나 체크할 변수
			//공이 있을때만 던져야 한다.
			if(!ballsGot.isEmpty()) {
				pickup = ballsGot.get(0); //아무거나 집는다. 비어있지 않다면 첫번째는 항상 존재
				ballsGot.remove(0);
			}
			if (pickup != null) {
				//빼온걸 집어넣는다.
				throwQueue[type] = pickup;
			}
		}
	}
	
	/**
	 * purpose : 진짜 공을 던질거임
	 * mechanism : 박자가 경신되는 순간에 호출할 메서드. throwQueue를 검사해서 위치에 따라 공을 던진다
	 * comment : 
	 */
	public void Throw() {
		boolean check;
		Ball shoot; //던질 공
		for(int i = 0; i < 3; i++) {
			shoot = throwQueue[i];
			if(shoot != null) {
				
				//초기화
				//아마도 (i+1)*beat만큼의 프레임동안 날아갈 것. shoot에 그 값을 할당한다
				shoot.ResetValue(xpos, ypos, (i*1), opponent);
				
				throwQueue[i] = null;//throwQueue 비우기
				opponent.catchQueue.add(shoot);
			}
		}
	}
}
