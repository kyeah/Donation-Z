import java.util.ArrayList;

public class ZombieGenerator
{
	static int numZombies=20;
	
	public static void generate(ArrayList<Zombie> zombies)
	{
		if(numZombies<1000)
			numZombies*=1.2;
		zombies.clear();
		
		for(int i=0; i<numZombies; i++)
		{
			zombies.add(new Zombie(getRandomX(),getRandomY()));
		}
	}

	private static int getRandomY() 
	{
		if(Math.random()<0.5)
		{
			return (int) (-Math.random()*300-50);
		}
		return (int) (Math.random()*300+Runner.frame.getWidth()+50);
	}

	private static int getRandomX() 
	{
		if(Math.random()<0.5)
		{
			return (int) (-Math.random()*300-50);
		}
		return (int) (Math.random()*300+Runner.frame.getHeight()+50);
	}
}