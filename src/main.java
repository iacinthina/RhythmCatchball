import org.rhythmcatchball.core.GameManager;
import org.rhythmcatchball.core.GameSprite;
import org.rhythmcatchball.core.Key;
import org.rhythmcatchball.core.UserConfig;
import org.rhythmcatchball.gameplay.Ball;
import org.rhythmcatchball.gameplay.FloatMessage;
import org.rhythmcatchball.gameplay.Player;
import org.rhythmcatchball.gameplay.RoundManager;
import org.rhythmcatchball.service.AIController;
import org.rhythmcatchball.service.KeyboardController;

public class main {
	
	public static void main(String[] args)
	{
		GameManager gm;
		gm = GameManager.getref();
		GameSprite.loadImages(gm);
		int f_width = 640;
		int f_height = 360;
		int P1Score = 0;
		int P2Score = 0;
		
		//test
		int xpos = 0;
		int ypos = 0;
		String sprkey;
		for(int i=0; i<11; i++) {
			sprkey = "spr_message_"+i;
			xpos = 40+i*40;
			ypos = 30+i*30;

			FloatMessage.create(xpos, ypos, sprkey, true);
		}
		
		gm.setSize(f_width, f_height);
		gm.setLayout(null);
		gm.setVisible(true);

		RoundManager rm = (RoundManager) RoundManager.create(f_width/2, f_height/2);
		rm.launchLocal();
		
		
		/*Player P1 = (Player) Player.create(70, 300);
		Player P2 = (Player) Player.create(570, 300);
		P1.opponent = P2;
		P2.opponent = P1;
		Ball b = (Ball) Ball.create(70, 300);
		b.setActive(false);
		P1.takeBall(b);
		b = (Ball) Ball.create(70, 300);
		b.setActive(false);
		P1.takeBall(b);
		b = (Ball) Ball.create(70, 300);
		b.setActive(false);
		P1.takeBall(b);*/

		/*AIController P1C = new AIController(null);
		P1C.setPlayer(rm.getEntry(0));
		rm.addController(P1C);*/
		
		AIController P2C = new AIController(null);
		P2C.setPlayer(rm.getEntry(1));
		rm.addController(P2C);
		
		KeyboardController P1C = new KeyboardController();
		P1C.setPlayer(rm.getEntry(0));
		P1C.keyval[0] = 68;
		P1C.keyval[1] = 83;
		P1C.keyval[2] = 65;
		P1C.keyval[3] = 32;
		gm.addKeyListener(P1C);
		
		/*KeyboardController P2C = new KeyboardController();
		
		P2C.player = P2;
		P2C.keyval[0] = 37;
		P2C.keyval[1] = 40;
		P2C.keyval[2] = 39;
		P2C.keyval[3] = 16;
		gm.addKeyListener(P2C);*/
		
		int beatrate = gm.modeBeatrate;
		int beatcount = 0;
		try {
			while(true) {
				Thread.sleep(16);
				if (++beatcount >= beatrate) {
					beatcount = 0;
					//gm.testBeat();
					FloatMessage.create(xpos, ypos, "spr_message_4", true);
					//System.out.println("beat");
				}
				gm.Update();
				gm.repaint();
				//P1Score = P1.getScore();
				//P2Score = P2.getScore();
				//System.out.println("beatcount = "+beatcount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
