
public class UserConfig {
	float confVolume;
	float [] confResolu;
	int[] confKey1Set = new int[4];	
	int[] confKey2Set = new int[4];	
	
	UserConfig() {
		//Player1 key값 초기설정
		confKey1Set[Key.LOW.index_num] = 65;
		confKey1Set[Key.MIDDLE.index_num] = 83;
		confKey1Set[Key.HIGH.index_num] = 68;
		confKey1Set[Key.RECEIVE.index_num] = 16;
		
		//Player2 key값 초기설정
		confKey2Set[Key.LOW.index_num] = 37;
		confKey2Set[Key.MIDDLE.index_num] = 38;
		confKey2Set[Key.HIGH.index_num] = 39;
		confKey2Set[Key.RECEIVE.index_num] = 32;
		
	}
	
	//일단 만들어놓고 나중에 변경
	float ChangeVolume(float tmp) { return 0.0f;}
}
