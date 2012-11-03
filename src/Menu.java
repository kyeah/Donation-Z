import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Menu
{
	static JFrame frame=Runner.frame;
	static BufferedImage background,logo;
	
	static
	{
		try {
			background=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"menu_background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			logo=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void draw(Graphics g)
	{
		g.drawImage(background, -(background.getWidth()-frame.getWidth())/2, -(background.getHeight()-frame.getHeight())/2, null);
		g.drawImage(logo,frame.getWidth()/2-logo.getWidth()/2,frame.getHeight()/2-logo.getHeight()/2-frame.getInsets().top,null);
	}
}