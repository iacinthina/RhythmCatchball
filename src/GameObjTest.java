import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameObjTest {

	@Test
	void testDraw() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDestroy() {
		GameObj test = new GameObj();
		test.Destroy();
		
		assertTrue((test.alive == false), "Destroy error");
	}

	@Test
	void testShiftPos() {
		GameObj test = new GameObj();
		test.xpos = 1.0f;
		test.ypos = 1.0f;
		
		test.ShiftPos(3.0f, 4.0f);
		
		assertTrue((test.xpos == 4.0f)&&(test.ypos == 5.0f), "ShiftPos error");
	}

	@Test
	void testSetPos() {
		GameObj test = new GameObj();
		test.xpos = 1.0f;
		test.ypos = 1.0f;
		
		test.SetPos(3.0f, 4.0f);
		
		assertTrue((test.xpos == 3.0f)&&(test.ypos == 4.0f), "SetPos error");
	}


}
