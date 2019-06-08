package org.rhythmcatchball.core;

public enum Key {
	LOW(0),
	MIDDLE(1), 
	HIGH(2), 
	RECEIVE(3);
	
	int indexNum;
	Key(int indexNum){this.indexNum = indexNum;}
	
	public int getIndex() {
		return indexNum;
	}
}