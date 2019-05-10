import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class GameObj {
	Graphics buffg;
	Image buffimage;
	
	Image spr_Lettersign [11];
	Image spr_scoreboard [2];
	Image spr_0;
	Image spr_mes [12];
	Image spr_user;
	Image spr_result [3];
	Image spr_ball;
	Image spr_6 [11];
	Image spr_7 [4];
	for(i=0; i<11; i++)
	{
		spr_Lettersign[i] = new ImageIcon("spr_lettersign_" +i+ ".png").getImage;
	}
	for(i=0; i<2; i++)
	{
		spr_scoreboard[i] = new ImageIcon("spr_scoreboard_" +i+ ".png").getImage;
	}
	
	spr_0 = new ImageIcon("sprite0_0.png").getImage;
	
	for(i=0; i<12; i++)
	{
		spr_spr_mes[i] = new ImageIcon("sprite1_" +i+ ".png").getImage;
	}
	
	spr_user = new ImageIcon("sprite2_0.png").getImage;
	
	for(i=0; i<3; i++)
	{
		spr_result[i] = new ImageIcon("sprite3_" +i+ ".png").getImage;
	}
	
	spr_ball = new ImageIcon("sprite4_0.png").getImage;
	
	for(i=0; i<11; i++)
	{
		spr_6[i] = new ImageIcon("sprite6_" +i+ ".png").getImage;
	}
	for(i=0; i<4; i++)
	{
		spr_7[i] = new ImageIcon("sprite7_" +i+ ".png").getImage;
	}
	
	public float xpos;
	public float ypos;
	public int imageIndex;
	public int xoffset;
	public int yoffset;
	public Boolean alive;
	
	public void Draw_lettersign(int index)
	{
		buffg.drawImage(spr_Lettersign[index],x,y,this);//언제그릴지 조건넣어야 한다
	}
	public void Update(Graphics g)
	{
		//buffImage = createImage(화면크기); 
		buffg = buffImage.getGraphics();
		//Draw_lettersign();
		//전부 드로우.
		g.drawImage(buffImage,0,0,this);
	}
	
	public void Destroy()
	{
		alive = false;
	}
	public void ShiftPos(float x, float y)
	{
		xpos += x;
		ypos += y;
		
	}
	public void SetPos(float x, float y)
	{
		xpos = x;
		ypos = y;
	}
	public void SetOffset(int x, int y)
	{
		//�궘�젣 �삁�젙 
	}
}
