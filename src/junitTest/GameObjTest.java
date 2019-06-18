package junitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.gameplay.GameObj;

class GameObjTest {

	@Test // GameObj test
	public void test_GameObj() throws IllegalArgumentException, IllegalAccessException {
		GameObj test = new GameObj();
		Field field;
		boolean flag = true;
		float x = test.xpos;
		assertEquals(0, x, 0);
		float y = test.ypos;
		assertEquals(0, y, 0);
		try {
			field = test.getClass().getDeclaredField("sprKey");
			field.setAccessible(true);
			flag = flag && (field.get(test) == "");
			field = test.getClass().getDeclaredField("alive");
			field.setAccessible(true);
			flag = flag && (Boolean) field.get(test);
			field = test.getClass().getDeclaredField("active");
			field.setAccessible(true);
			flag = flag && (Boolean) field.get(test);
			field = test.getClass().getDeclaredField("visible");
			field.setAccessible(true);
			flag = flag && (Boolean) field.get(test);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		assertTrue(flag, "Create success");
	}

	@Test // create
	public void test_Create() {
		boolean testResult = true;
		Field field;
		GameObj test = new GameObj();
		GameObj result = new GameObj();
		// 초기화
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
			result = (GameObj) m.invoke(result, 3, 3);
		} catch (Exception e) {
			e.printStackTrace();
			testResult = false;
		}
		assertTrue(testResult, "Create success");
	}

	@Test
	public void test_getBeatrate() {
		int beat_test;
		beat_test = GameObj.getBeatrate();
		assertEquals(GameManager.getref().modeBeatrate, beat_test);
	}

	@Test
	public void test_register() {
		GameObj test = new GameObj();
		GameObj instance = new GameObj();
		Field field;
		ArrayList testInst = new ArrayList<>();
		testInst = null;
		Boolean flag = false;
		try {
			Method m = test.getClass().getDeclaredMethod("register", GameObj.class);
			m.setAccessible(true);
			m.invoke(test, instance);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		try {
			field = GameManager.getref().getClass().getDeclaredField("gameInst");
			field.setAccessible(true);
			testInst = (ArrayList) field.get(GameManager.getref());
		}
		
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if(testInst != null)
		{
			flag = true;
		}
		assertTrue(flag, "Create success");
	}	
	
	@Test
	public void test_destroy() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		test.destroy();
		try {
			field = test.getClass().getDeclaredField("alive");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertTrue(!flag, "Create success");
	}
	@Test
	public void test_isAlive() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		Boolean flag2 = false;
		test.isAlive();
		try {
			field = test.getClass().getDeclaredField("alive");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);
			if(flag != null)
			{
				flag2 = true;
			}
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertTrue(flag2, "Create success");
	}
	
	@Test
	public void test_setVisible() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		test.setVisible(true);//함수실행 true가 visible에 대입됨
		try {
			field = test.getClass().getDeclaredField("visible");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertTrue(flag, "Create success");
	}
	@Test
	public void test_getVisible() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		Boolean flag2 = false;
		Boolean flag3 = false;
		flag2 = test.getVisible();
		try {
			field = test.getClass().getDeclaredField("visible");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);//test의 visible이 플래그
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if(flag == flag2)
			flag3 = true;
		
		assertTrue(flag3, "Create success");
	}
	@Test
	public void test_setSpriteKey() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		String str = "";
		test.setSpriteKey("abc");
		try {
			field = test.getClass().getDeclaredField("sprKey");
			field.setAccessible(true);
			str = (String) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertEquals("abc",str);
}
	@Test
	public void test_getSpriteKey() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		String str = "";
		String str2 = "";
		test.setSpriteKey("abc");//테스트 내부 스프라이트는 abc임
		str = test.getSpriteKey();//str테스트 내부것이 들어감 abc
		try {
			field = test.getClass().getDeclaredField("sprKey");
			field.setAccessible(true);
			 str2 = (String) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		flag = str.equals(str2);
			
		assertTrue(flag, "Create success");
	}
	
	@Test
	public void test_setActive() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		test.setActive(true);
		try {
			field = test.getClass().getDeclaredField("active");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertTrue(flag, "Create success");
	}
	
	@Test
	public void test_isActive() {
		GameObj test = new GameObj();
		Field field;
		Boolean flag = false;
		test.setActive(true);
		flag = test.isActive();
		try {
			field = test.getClass().getDeclaredField("active");
			field.setAccessible(true);
			flag = (Boolean) field.get(test);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		 catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertTrue(flag, "Create success");
	}
}
