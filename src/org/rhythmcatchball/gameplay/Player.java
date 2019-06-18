package org.rhythmcatchball.gameplay;
import java.util.ArrayList;

import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.service.Connection;

/**
 * Player.java
 * @author pc1
 * @date   2019. 5. 10.
 * 게임이 시작되면 생성하는 플레이어 오브젝트. 게임 진행중에 항상 2개 이상이 존재해야 한다.
 */
public class Player extends GameObj {
	private static final int ONONE = 0;
	private static final int OSELF = 1;
	private static final int OTHER = 2;
	
	public Player opponent; //상대방 플레이어. 내가 공을 던질 타겟(나한테 공을 던지는 대상은 알 수 없음)
	private Ball[] throwQueue; //박자가 경신되는 순간에, 이 배열에 있는 공을 위치에 따라 다른 속도로 던진다.
	private ArrayList<Ball> catchQueue; //내가 받을 공들. 상태를 체크할 수 있다.
	private ArrayList<Ball> ballsGot; //내가 가진 공들. throwQueue로만 이동할 수 있다.
	private int score; //점수. 출력할 수 있어야 한다
	private int combo; //놓치지만 않으면 하나씩 쌓인다.
	private int comboLevel; //콤보에 따른 보너스 점수를 얻기 위한 마커

	private int onlineType;
	ArrayList<Checkout> lastResult;
	private int onlineSync;
	
	/**
	 * 생성자. 리스트 초기화
	 */
	private Player() {
		opponent = null;
		throwQueue = new Ball[3];
		catchQueue = new ArrayList<>();
		ballsGot = new ArrayList<>();
		score = 0;
		combo = 0;
		comboLevel = 0;
		
		onlineType = ONONE;
		lastResult = null;
		onlineSync = 0;
	}
	
	@Override
	public void update() {
		onlineMakeBallWait();
		ArrayList<Ball> removeList = new ArrayList<>();
		for(Ball b : catchQueue) {
			if (b.isOver()) {
				b.checkout = Checkout.LAME;
				addScore(Checkout.LAME);
				opponent.takeBall(b);
				removeList.add(b);
				b.caught(true);
			}
		}
		for(Ball b : removeList) {
			catchQueue.remove(b);
		}
	}
	@Override
	public void onBeat() {
		if (onlineType == ONONE || onlineSync == onlineGetThrow())
			throwAll();
		int holdingPenalty = countBall();
		score = (int) Math.max(0, score - Math.pow(holdingPenalty, 2));
	}
	
	public static GameObj create(float xpos, float ypos) {
		/**
		 * (반드시 register() 사용하고, Player형 인스턴스 생성해서 리턴해야 한다.)
		 * 이미지 설정도 같이 해주세요
		 * 코드 작성 전이나 중이나 후나 프로젝트 매니저한테 물어본 내용 메모해가면서 코드 짤 수 있도록 부탁드립니다.
		 */
		Player player = new Player();
		player.xpos = xpos;
		player.ypos = ypos;
		player.opponent = null;
		player.setSpriteKey("spr_player");
		register(player);
		
		return player;
	}
	
	/**
	 * purpose : 잡기키를 누르면 공을 잡으려고
	 * mechanism : 나한테 오고있는 공을 모두 검사해서 받을지 안받을지 검사
	 * comment : 
	 */
	public void catchOnce() {
		Checkout precision = null; //잡은 타이밍에 따른 판정. 자료형 고민
		boolean canGrab = true; //한번에 하나의 공만 잡을 수 있다.
		Ball removeOnce = null; //잡은 공은 ballsGot리스트에서 제외
		
		//나한테 오고있는 모든 공을 검사
		for(Ball grab: catchQueue) {
			//공의 위치를 검사한다. 받지 못할경우엔 무시해야 함.
			precision = grab.judgement();
			if (precision != null) {
				if (canGrab) {
					ballsGot.add(grab);
					removeOnce = grab;
					grab.caught(true);
					canGrab = false;
					addScore(precision); //정확도에 따라 점수를 얻는다. 안좋은 판정을 받더라도
					
				} else {
					grab.caught(false);
				}
			}
		}
		if (removeOnce != null)
			catchQueue.remove(removeOnce);
	}

	/**
	 * purpose : 던질 공을 준비하려고
	 * mechanism : 박자가 경신되는 순간에 던져질 것이기 때문에, 미리 준비만 해놓는다.
	 * comment : type을 3가지로 제한해야 함. throwQueue 배열 인덱스를 벗어나지 않게 하는것이 중요
	 * 또한 온라인 플레이시 어긋나지 않게 할 필요도 있다.
	 */
	public void readyToThrow(int type) {
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
	 * purpose : 준비된 공을 모두 던집니다. 단 준비된 공만 던진다.
	 * mechanism : 박자가 경신되는 순간에 호출할 메서드. throwQueue를 검사해서 위치에 따라 공을 던진다
	 * comment : 
	 */
	public void throwAll() {
		Ball shoot; //던질 공
		boolean targetMissing = (opponent == null);
		for(int i = 0; i < 3; i++) {
			shoot = throwQueue[i];
			if(shoot != null) {
				throwQueue[i] = null;//throwQueue 비우기
				if (targetMissing) {
					//적이 없다
					ballsGot.add(shoot);
				} else {
					//(i+1)*beat만큼의 프레임동안 날아갈 것. shoot에 그 값을 할당한다
					shoot.reset(xpos, ypos, (i+1), opponent);
					opponent.catchQueue.add(shoot);
					GameManager.playSound("snd_throw");
				}
			}
		}
	}
	
	/**
	 * purpose : 판정에 따른 점수를 얻기 위함
	 * mechanism : 콤보에 따른 보너스 점수도 부여.
	 * comment : 현재 판정의 자료형을 고민중
	 */
	public void addScore(Checkout precision) {
		int[] comboReq = {5, 10, 20, 40, 70, 99999};//각 index는 요구 콤보수를 뜻함
		int[] bonus = {25, 50, 100, 200, 300, 0}; //요구 콤보수에 도달했을 때 얻는 점수
		
		//판정에 따라 다른 점수.
		onlineSetBallResult(precision);
		if (precision != null) {
			score = Math.max(score + precision.getScore(), 0);
			if (precision == Checkout.LAME) {
				if (combo > 1)
					FloatMessage.create(xpos, ypos, "combo_break", true);
				combo = 0;
				comboLevel = 0; //콤보레벨도 초기화해서 5콤보를 두번째 달성했을 때도 점수부여. 이후도 마찬가지
				GameManager.playSound("snd_fall");
			} else {
				if (++combo >= comboReq[comboLevel]) {
					score += bonus[comboLevel];
					comboLevel = Math.max(comboLevel+1, 5);
				}
				GameManager.playSound("snd_grab");
			}
		}
	}
	
	/**
	 * purpose : score은 접근전용
	 * mechanism : 
	 * comment : 
	 */
	public int getScore() { return score; }
	
	/**
	 * purpose : 가져가겠다
	 * mechanism : 
	 * comment : 
	 */
	public void takeBall(Ball b) {
		ballsGot.add(b);
	}
	
	public int countBall() {
		if (ballsGot == null) return 0;
		return ballsGot.size();
	}

	public void onlineIsSelf() {
		onlineType = OSELF;
		lastResult = new ArrayList<>();
	}
	public void onlineIsOther() {
		onlineType = OTHER;
	}
	
	private void onlineMakeBallWait() {
		if (onlineType != OTHER) return;
		//실질적인 판정을 따기 전에 공을 멈추게하기
		Checkout precision = null;
		
		for(Ball grab: catchQueue) {
			precision = grab.judgement();
			if (precision == Checkout.EXACTLY) {
				grab.caught(false);
			}
		}
	}
	
	private void onlineSetBallResult(Checkout result) {
		if (onlineType != OSELF) return;
		lastResult.add(result);
	}
	
	public Checkout onlineGetBallResult() {
		if (lastResult == null) return null;
		if (lastResult.isEmpty()) return null;
		Checkout result = lastResult.get(0);
		lastResult.remove(0);
		return result;
	}
	
	public void onlineProcessWaitingBall(Checkout precision) {
		if (onlineType != OTHER) return;
		Ball grab = null; //잡은 공은 ballsGot리스트에서 제외
		
		if (precision != null) {
			if (catchQueue.isEmpty()) {
				System.out.println("error : checkout exists but no ball to proceed");
			} else {
				grab = catchQueue.get(0);
				ballsGot.add(grab);
				catchQueue.remove(grab);
				grab.checkout = precision;
				grab.caught(true);
				addScore(precision); //정확도에 따라 점수를 얻는다. 안좋은 판정을 받더라도
			}
		}
	}

	public void onlineSyncThrow(boolean row[]) {
		int bitmask = 1;
		int data = 0;
		
		for(int i = 0; i < row.length; i++) {
			bitmask *= 2;
			if (row[i])
				data += bitmask;
		}
		
		onlineSync = data;
	}
	
	public int onlineGetThrow() {
		int bitmask = 1;
		int data = 0;
		
		for(int i = 0; i < throwQueue.length; i++) {
			bitmask *= 2;
			if (throwQueue[i] != null)
				data += bitmask;
		}
		
		return data;
	}
}
