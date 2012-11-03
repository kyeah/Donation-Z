import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Weapon
{
	private int clipSize,currentClip,ammo,damage;
	public long fireDelay=300;
	private static Clip clip,shotgun,machine;
	int type=0;
	private int numBullets=1;
	
	static
	{
		try {
			loadSound();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void newWeapon(int type)
	{
		ammo=0;
		this.type=type;
		if(type==0)
		{
			this.damage=(int) 4;
			fireDelay=300;
			numBullets=1;
		}
		else if(type==1)
		{
			this.damage=(int) 1;
			fireDelay=50;
			numBullets=1;
		}
		else if(type==2)
		{
			this.damage=(int) 2;
			fireDelay=750;
			numBullets=6;
		}
	}
	
	public static void loadSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		clip = AudioSystem.getClip();
		File f=new File("resources"+File.separator+"sounds"+File.separator+"rifle.wav");
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
        clip.open(inputStream);
        
        machine = AudioSystem.getClip();
		f=new File("resources"+File.separator+"sounds"+File.separator+"machinegun.wav");
        inputStream = AudioSystem.getAudioInputStream(f);
        machine.open(inputStream);
        
        shotgun = AudioSystem.getClip();
		f=new File("resources"+File.separator+"sounds"+File.separator+"shotgun.wav");
        inputStream = AudioSystem.getAudioInputStream(f);
        shotgun.open(inputStream);
	}
	
	public void setClipSize(int i)
	{
		clipSize=i;
		currentClip=clipSize;
	}
	
	public void setDamage(int d)
	{
		damage=d;
	}
	
	public void addAmmo(int a)
	{
		ammo+=a;
	}

	public int getAmmo() 
	{
		return ammo;
	}

	public void fire() 
	{
		if(currentClip>0)
		{
			if(type==0)
			{
				clip.setFramePosition(0);
				clip.start(); 
			}
			else if(type==1)
			{
				machine.setFramePosition(0);
				machine.start();
			}
			else if(type==2)
			{
				shotgun.setFramePosition(0);
				shotgun.start();
			}
			//currentClip--;
			for(int i=0; i<numBullets; i++)
			{
				double change=0.0;
				if(i!=0)
					change=Math.random()*.2-.1;
				checkIntersection(change);
			}
		}
	}

	private void checkIntersection(double change) 
	{
		Bullet bull=new Bullet(Runner.player.getX(),Runner.player.getY(),Runner.player.getAngle()+change);
		while(inBounds(bull))
		{
			bull.move();
			for(int i=0; i<Runner.zombies.size(); i++)
			{
				Zombie z=Runner.zombies.get(i);
				Rectangle rect=z.createBox();
				if(rect.contains(bull.x, bull.y))
				{
					z.damage(damage);
					return;
				}
			}
		}
	}

	private boolean inBounds(Bullet bull)
	{
		return bull.x>0 && bull.x<Runner.frame.getWidth() && bull.y>0 && bull.y<Runner.frame.getHeight();
	}
}