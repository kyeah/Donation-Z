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
	private BufferedImage img;
	private static BufferedImage normal,devil,goliath;
	private double angle;
	private int damage=1;
	private double speed=1.5;
	
	public int ID=0;
	
	public Zombie(int x, int y)
	{
		tx=this.x=x;
		ty=this.y=y;
		health=5;
		
		
		pickType();
	}
	
	static
	{
		try {
			normal=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"zombie.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			devil=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"devil.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			goliath=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"goliath.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void pickType() 
	{
		int num=0;
		if(Math.random()<0.1)
			num=1;
		else if(Math.random()<0.99)
			num=2;
		try {
			loadImage(num);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadImage(int i) throws IOException
	{
		if(i==0)
		{
			img=normal;
		}
		else if(i==1)
		{
			img=devil;
			speed=3;
			damage=5;
			health=15;
		}
		else
		{
			img=goliath;
			speed=0.3;
			damage=50;
			health=100;
		}
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
		int changeX=Runner.player.getX()-getX();
		int changeY=Runner.player.getY()-getY();
		
		int totalChange=Math.abs(changeY)+Math.abs(changeX);
		
		double ratioX=1.0*changeX/totalChange;
		double ratioY=1.0*changeY/totalChange;
		
		double cx=ratioX*speed;
		double cy=ratioY*speed;
		
		double nx=tx;
		double ny=ty;
		
		nx+=cx;
		ny+=cy;
		
		boolean movePossible=true;
		for(Zombie z:Runner.zombies)
		{
			if(!equals(z))
			{
				if(z.createBox().contains(nx,ny))
				{
					movePossible=false;
				}
			}
		}
		
		if(movePossible)
		{
			tx=nx;
			ty=ny;
		}
		
		if(Math.sqrt(Math.pow(getX()-Runner.player.getX(),2)+Math.pow(getY()-Runner.player.getY(),2))<img.getWidth()*6/5)
		{
			Runner.player.damage(damage);
		}
	}
	
	public boolean equals(Zombie other)
	{
		return ID==other.ID;
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