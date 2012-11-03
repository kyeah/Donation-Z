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
	private static Clip clip;
	
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
	
	public void newWeapon()
	{
		ammo=0;
	}
	
	public static void loadSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		clip = AudioSystem.getClip();
		File f=new File("resources"+File.separator+"sounds"+File.separator+"rifle.wav");
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(f);
        clip.open(inputStream);
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
			clip.setFramePosition(0);
			clip.start(); 
			//currentClip--;
			checkIntersection();
		}
	}

	private void checkIntersection() 
	{
		Bullet bull=new Bullet(Runner.player.getX(),Runner.player.getY(),Runner.player.getAngle());
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