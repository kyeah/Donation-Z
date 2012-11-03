import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player
{
	private double tx,ty;
	private int x,y;
	private int health=9400;
	public int kills=0;
	private static BufferedImage img,fireImg;
	private double angle;
	private double speed=2;
	Weapon weapon=new Weapon();
	public boolean isFiring=false;
	
	public Player(int x, int y)
	{
		tx=this.x=x;
		ty=this.y=y;
		try {
			loadImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		weapon.setDamage(1);
		weapon.setClipSize(6);
		weapon.addAmmo(48);
	}
	
	public void fire()
	{
		weapon.fire();
	}
	
	public static void loadImage() throws IOException
	{
		img=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"player.png"));
		fireImg=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"shot.png"));
	}
	
	public void draw(Graphics g)
	{
		x=(int) Math.round(tx);
		y=(int) Math.round(ty);
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(x,y);
		g2.rotate(angle);
		g2.drawImage(img,-img.getWidth()/2,-img.getHeight()/2,Runner.frame);
		if(isFiring)
			g2.drawImage(fireImg,-img.getWidth()/2,-img.getHeight()/2,Runner.frame);
		g2.rotate(-angle);
		g2.translate(-(x),-(y));
	}
	
	public void damage(int val)
	{
		health-=val;
	}

	public void setAngle(int mouseX, int mouseY) 
	{
		double diffY=1.0*mouseY-y;
		double diffX=1.0*mouseX-x;
		angle=Math.atan(diffY/diffX)+Math.PI/2;
		if(diffX<0)
			angle-=Math.PI;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void move(int moveHorizontal, int moveVertical)
	{
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

	public double getAngle()
	{
		return angle;
	}

	public int getHealth() 
	{
		return health;
	}

	public void giveHealth(int i) 
	{
		health+=i;
	}
}