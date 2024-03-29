import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

public class InfoBar
{
	static JFrame frame=Runner.frame;
	static final int HEIGHT=100;
	
	public static void draw(Graphics g)
	{
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, frame.getHeight()-HEIGHT, frame.getWidth(), HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,12));
		g.drawString("Health: "+Runner.player.getHealth(),12,frame.getHeight()-HEIGHT+20);
		g.drawString("Zombies Left: "+Runner.zombies.size(),152,frame.getHeight()-HEIGHT+20);
		g.drawString("Score: "+Runner.player.kills,12,frame.getHeight()-HEIGHT+40);
		g.drawString("Weapon: "+Runner.player.whichWeapon(),152,frame.getHeight()-HEIGHT+40);
	}
}