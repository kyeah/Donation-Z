import java.awt.Frame;
import java.awt.Point;
import java.util.ArrayList;

public class ZombieGenerator
{
	static int numZombies=100;
	
	public static void generate(ArrayList<Zombie> zombies)
	{
		generateGun();
		Runner.player.giveHealth(100);
		
		if(numZombies<1000)
			numZombies*=1.2;
		zombies.clear();
		
		for(int i=0; i<numZombies; i++)
		{
			Point p=getPoint();
			Zombie newZombie=new Zombie(p.x,p.y);
			newZombie.ID=i;
			zombies.add(newZombie);
		}
	}
	
	private static Point getPoint() 
	{
		Point p=new Point();
		double angle=Math.random()*Math.PI*2;
		int constantx=(int) (Runner.frame.getWidth()+Math.random()*300);
		int constanty=(int) (Runner.frame.getHeight()+Math.random()*300);
		p.x=Runner.frame.getWidth()/2+(int) (Math.cos(angle)*constantx);
		p.y=Runner.frame.getHeight()/2+(int) (Math.sin(angle)*constanty);
		return p;
	}

	private static void generateGun()
	{
		Runner.player.weapon.newWeapon((int)(Math.random()*2));
	}
}