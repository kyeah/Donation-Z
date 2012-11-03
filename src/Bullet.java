import java.awt.Point;

public class Bullet
{
	double x,y,angle;
	
	public Bullet(double x, double y, double angle)
	{
		this.x=x;
		this.y=y;
		this.angle=angle-Math.PI/2;
		//Runner.line=new Point((int)(Runner.player.getX()+1000*Math.cos(this.angle)),(int)(Runner.player.getY()+1000*Math.sin(this.angle)));
	}
	
	public void move()
	{
		x+=Math.cos(angle);
		y+=Math.sin(angle);
	}
}