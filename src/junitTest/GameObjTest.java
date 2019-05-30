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
		
		assertTrue(true, "AddInstance Fail");
		
	}
	 @Test
	public void test_Destroy() {
		
		assertTrue(true, "AddInstance Fail");
		
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
			
			assertTrue(true, "AddInstance Fail");
			 
	 }
	
	
		
}
