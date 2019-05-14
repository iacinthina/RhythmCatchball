package org.rhythmcatchball.core;

public class UserConfig {
	float confVolume;
	float [] confResolu;
	int[] confKey1Set = new int[4];	
	int[] confKey2Set = new int[4];	
	
	UserConfig() {
		//Player1 key값 초기설정
		confKey1Set[Key.LOW.getIndex()] = 65;
		confKey1Set[Key.MIDDLE.getIndex()] = 83;
		confKey1Set[Key.HIGH.getIndex()] = 68;
		confKey1Set[Key.RECEIVE.getIndex()] = 16;
		
		//Player2 key값 초기설정
		confKey2Set[Key.LOW.getIndex()] = 37;
		confKey2Set[Key.MIDDLE.getIndex()] = 38;
		confKey2Set[Key.HIGH.getIndex()] = 39;
		confKey2Set[Key.RECEIVE.getIndex()] = 32;
		
	}
	
	//일단 만들어놓고 나중에 변경
	float ChangeVolume(float tmp) { return 0.0f;}
}
