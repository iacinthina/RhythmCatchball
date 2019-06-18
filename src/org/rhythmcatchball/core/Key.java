package org.rhythmcatchball.core;

public enum Key {
	LOW(0, "Fast Throw"),
	MIDDLE(1, "Middle Throw"), 
	HIGH(2, "Long Throw"), 
	RECEIVE(3, "Catch");
	
	int indexNum;
	String desc;
	Key(int indexNum, String desc){
		this.indexNum = indexNum;
		this.desc = desc;
	}
	
	public int getIndex() {
		return indexNum;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}