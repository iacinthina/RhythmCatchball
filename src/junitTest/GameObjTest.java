package junitTest;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.rhythmcatchball.gameplay.GameObj;

class GameObjTest {
	@Test
	void testDestroy() {
		GameObj test = new GameObj();
		test.destroy();
		
		assertTrue((test.isAlive() == false), "Destroy error");
	}


}
