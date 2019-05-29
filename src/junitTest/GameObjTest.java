package junitTest;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.GameObj;

class GameObjTest {
	
	@Test //create
	public void test_Create() {
		boolean testResult = true;
		Field field;
		GameObj test = new GameObj();
		GameObj result = new GameObj();
		//초기화
		try {
			Method m = test.getClass().getDeclaredMethod("create", float.class, float.class);
			m.setAccessible(true);
			field = test.getClass().getDeclaredField("alive");
			field.setAccessible(true);		
			field.set(test, true);
			testResult = testResult && (field.get(test) == field.get(result));
			field = test.getClass().getDeclaredField("visible");
			field.setAccessible(true);
			field.set(test, true);
			testResult = testResult && (field.get(test) == field.get(result));
			field = test.getClass().getDeclaredField("active");
			field.setAccessible(true);
			field.set(test, true);
			testResult = testResult && (field.get(test) == field.get(result));
			field = test.getClass().getDeclaredField("sprKey");
			field.setAccessible(true);
			field.set(test, "");
			testResult = testResult && (field.get(test) == field.get(result));
			test.xpos = 3;
			test.ypos = 3;
			result = (GameObj)m.invoke(result, 3, 3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			testResult = false;
		}		
		assertTrue(testResult, "Create success");		
	}
	 @Test
	public void test_Destroy() {
		GameObj test = new GameObj();
		test.destroy();
		
		assertTrue((test.isAlive() == false), "Destroy error");
	}
	/* @Test
	 public void test_register() {
		Field field =null;
		GameObj test = new GameObj();
		GameObj result = new GameObj();
		try {
			Method m = test.getClass().getDeclaredMethod("register", GameObj.class);
			m.setAccessible(true);
			field = GameManager.getref().getClass().getDeclaredField("gameInst");
			field.setAccessible(true);
			m.invoke(field,test);
			result = (GameObj) field.get(test);
		}
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}*/
	 
	 @Test
	 public void test_getBeatrate()
	 {
		int beat_test;
		beat_test = GameObj.getBeatrate();
		
		assertEquals(GameManager.getref().modeBeatrate , beat_test );		 
	 }
	
	
		
}
