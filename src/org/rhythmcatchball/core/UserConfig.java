package org.rhythmcatchball.core;

public class UserConfig {
	float confVolume;
	int[] confResolu = new int[2];
	int[] confKey1Set = new int[4];	
	int[] confKey2Set = new int[4];	
	
	UserConfig() {
		confResolu[0] = 640;
		confResolu[1] = 360;
		//Player1 key값 초기설정
		confKey1Set[Key.LOW.getIndex()] = 68;
		confKey1Set[Key.MIDDLE.getIndex()] = 83;
		confKey1Set[Key.HIGH.getIndex()] = 65;
		confKey1Set[Key.RECEIVE.getIndex()] = 32;
		
		//Player2 key값 초기설정
		confKey2Set[Key.LOW.getIndex()] = 37;
		confKey2Set[Key.MIDDLE.getIndex()] = 40;
		confKey2Set[Key.HIGH.getIndex()] = 39;
		confKey2Set[Key.RECEIVE.getIndex()] = 16;
		
	}
	
	//일단 만들어놓고 나중에 변경
	float ChangeVolume(float tmp) { return 0.0f;}

	public void setResolusion(int width, int height) {
		confResolu[0] = width;
		confResolu[1] = height;
	}
	public int getResWidth() {return confResolu[0];}
	public int getResHeight() {return confResolu[1];}
}
