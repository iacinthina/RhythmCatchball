package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.GameObj;

class GameManagerTest {

	@Test
	void testAddInstance() {
		boolean checkReturn;

		GameObj test = new GameObj();
		
		checkReturn = GameManager.getref().addInstance(test);
				
		assertTrue(checkReturn, "AddInstance Fail");
		
	}

}
