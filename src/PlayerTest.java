import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.rhythmcatchball.gameplay.Ball;

class PlayerTest {
	Method m;
	Ball testplayer = new Ball();

	@Test
	void testnormal() {

		try {
			m = testplayer.getClass().getDeclaredMethod("lerp", float.class, float.class, float.class);
			m.setAccessible(true);

			float result = (float) m.invoke(testplayer, 3, 5, 0.5f);

			System.out.println(result);

			assertEquals(result, 4f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
