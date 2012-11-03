import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player
{
	private int x,y;
	private int health;
	private static BufferedImage img;
	private double angle;
	private double speed=2;
	
	public Player(int x, int y)
	{
		this.x=x;
		this.y=y;
		try {
			loadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadImage() throws IOException
	{
		img=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"player.png"));
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(x,y);
		g2.rotate(angle);
		g2.drawImage(img,-img.getWidth()/2,-img.getHeight()/2,Runner.frame);
		g2.translate(-(x+img.getWidth()/2),-(y+img.getHeight()/2));
	}

	public void setAngle(int mouseX, int mouseY) 
	{
		double diffY=1.0*mouseY-y;
		double diffX=1.0*mouseX-x;
		angle=Math.atan(diffY/diffX)+Math.PI/2;
		if(diffX<0)
			angle-=Math.PI;
	}
	
	public void move(int moveHorizontal, int moveVertical)
	{
		switch(moveHorizontal)
		{
		case -1:x-=speed;break;
		case 1:x+=speed;break;
		}
		switch(moveVertical)
		{
		case -1:y-=speed;break;
		case 1:y+=speed;break;
		}
	}
}