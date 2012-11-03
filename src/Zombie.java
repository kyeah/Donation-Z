import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Zombie
{
	private double tx,ty;
	private int x,y;
	private int health;
	private static BufferedImage img;
	private double angle;
	private double speed=0.75;
	
	public Zombie(int x, int y)
	{
		tx=this.x=x;
		ty=this.y=y;
		health=5;
		try {
			loadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadImage() throws IOException
	{
		img=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"zombie.png"));
	}
	
	public void draw(Graphics g)
	{
		x=(int) Math.round(tx);
		y=(int) Math.round(ty);
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(x,y);
		g2.rotate(angle);
		g2.drawImage(img,-img.getWidth()/2,-img.getHeight()/2,Runner.frame);
		g2.rotate(-angle);
		g2.translate(-(x),-(y));
	}

	public void setAngle() 
	{
		double diffY=1.0*Runner.player.getY()-y;
		double diffX=1.0*Runner.player.getX()-x;
		angle=Math.atan(diffY/diffX)+Math.PI/2;
		if(diffX<0)
			angle-=Math.PI;
	}
	
	public void move()
	{
		int moveHorizontal=0;
		int moveVertical=0;
		
		if(Runner.player.getX()<x+img.getWidth()/2)
			moveHorizontal=-1;
		else if(Runner.player.getX()>x+img.getWidth()/2)
			moveHorizontal=1;
		
		if(Runner.player.getY()<y+img.getHeight()/2)
			moveVertical=-1;
		else if(Runner.player.getY()>y+img.getHeight()/2)
			moveVertical=1;
		
		switch(moveHorizontal)
		{
		case -1:tx-=speed;break;
		case 1:tx+=speed;break;
		}
		switch(moveVertical)
		{
		case -1:ty-=speed;break;
		case 1:ty+=speed;break;
		}
	}

	public int getHealth() 
	{
		return health;
	}

	public Rectangle createBox() 
	{
		Rectangle rect=new Rectangle(x-img.getWidth()/2,y-img.getHeight()/2,img.getWidth(),img.getHeight());
		return rect;
	}

	public void damage(int damage) 
	{
		Runner.bloods_small.add(new Point(x-img.getWidth()/2,y-img.getHeight()/2));
		health-=damage;
	}

	public int getX() 
	{
		return x-img.getWidth()/2;
	}
	
	public int getY() 
	{
		return y-img.getHeight()/2;
	}
}