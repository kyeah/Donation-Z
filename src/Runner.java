import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Runner extends JPanel implements MouseMotionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = -318708369614176297L;
	static JFrame frame;
	static long refreshTimer=System.currentTimeMillis();
	static long fireTimer=System.currentTimeMillis();
	static long drawFireTimer=System.currentTimeMillis();
	static Player player;
	static int mouseX,mouseY;
	static int moveHorizontal,moveVertical;
	static boolean firing=false;
	static ArrayList<Zombie> zombies=new ArrayList<Zombie>();
	static ArrayList<Point> bloods=new ArrayList<Point>();
	static ArrayList<Point> bloods_small=new ArrayList<Point>();
	static BufferedImage blood;
	static BufferedImage sand;
	static BufferedImage blood_small;
	
	static boolean init=false;
	
	static int currentPage=0;
	
	static Point line;
	
	public Runner()
	{
		frame=new JFrame("DonationZ");
		frame.setSize(1024,768);
		frame.add(this);
        frame.setVisible(true);
		frame.setFocusable(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		//frame.addMouseWheelListener(this);
		frame.addKeyListener(this);
	}
	
	public static void main(String args[])
	{
		player=new Player(100,100);
		new Runner();
		
		loadResources();
	}
	
	private static void loadResources() 
	{
		try
		{
			sand=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"sand.jpg"));
			blood=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"blood.png"));
			blood_small=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"blood_small.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
		if(System.currentTimeMillis()-refreshTimer>32)
		{
			if(currentPage==0)
			{
				Menu.draw(g);
			}
			else if(currentPage==1)
			{
				Login.draw(g);
			}
			else
			{
				if(!init)
				{
					init=true;
					player.setPosition(frame.getWidth()/2,frame.getHeight()/2);
				}
				refreshTimer=System.currentTimeMillis();
				drawBackground(g);
				drawBlood(g);
				moveZombies();
				drawZombies(g);
				generateZombies();
				player.move(moveHorizontal, moveVertical);
				player.setAngle(mouseX,mouseY);
				player.draw(g);
				InfoBar.draw(g);
			}
		}
		if(line!=null)
		{
			g.drawLine(player.getX(), player.getY(), line.x, line.y);
		}
		if(firing && System.currentTimeMillis()-fireTimer>player.weapon.fireDelay)
		{
			drawFireTimer=fireTimer=System.currentTimeMillis();
			player.isFiring=true;
			player.fire();
		}
		if(player.isFiring && System.currentTimeMillis()-drawFireTimer>200)
		{
			player.isFiring=false;
		}
		repaint();
	}

	private void generateZombies() 
	{
		if(zombies.size()<=0)
			ZombieGenerator.generate(zombies);
	}

	private void drawBlood(Graphics g) 
	{
		for(Point p:bloods)
		{
			g.drawImage(blood, p.x, p.y, null);
		}
		for(Point p:bloods_small)
		{
			g.drawImage(blood_small, p.x, p.y, null);
		}
	}

	private void drawZombies(Graphics g) 
	{
		for(int i=0; i<zombies.size(); i++)
		{
			Zombie z=zombies.get(i);
			if(z.getHealth()<0)
			{
				zombies.remove(i);
				player.kills++;
				bloods.add(new Point(z.getX(),z.getY()));
				i--;
				continue;
			}
			z.setAngle();
			z.draw(g);
		}
	}

	private void moveZombies() 
	{
		for(Zombie z:zombies)
		{
			z.move();
		}
	}

	private void drawBackground(Graphics g) 
	{
		//g.setColor(new Color(168,168,168));
		//g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		
		g.drawImage(sand, 0, 0, frame);
	}

	public void mouseDragged(MouseEvent e) 
	{
		int x=e.getX();
		int y=e.getY();
		mouseX=x-frame.getInsets().left;
		mouseY=y-frame.getInsets().top;
	}

	public void mouseMoved(MouseEvent e) 
	{
		int x=e.getX();
		int y=e.getY();
		mouseX=x-frame.getInsets().left;
		mouseY=y-frame.getInsets().top;
	}

	public void keyPressed(KeyEvent e) 
	{
		if(currentPage==1)
		{
			Login.handleKey(e.getKeyChar(),e.getKeyCode());
			return;
		}
		int key=e.getKeyCode();
		switch(key)
		{
			case KeyEvent.VK_LEFT:moveHorizontal=-1;break;
			case KeyEvent.VK_RIGHT:moveHorizontal=1;break;
			case KeyEvent.VK_UP:moveVertical=-1;break;
			case KeyEvent.VK_DOWN:moveVertical=1;break;
			case KeyEvent.VK_A:moveHorizontal=-1;break;
			case KeyEvent.VK_D:moveHorizontal=1;break;
			case KeyEvent.VK_W:moveVertical=-1;break;
			case KeyEvent.VK_S:moveVertical=1;break;
			case KeyEvent.VK_ENTER:if(currentPage<2)currentPage++;break;
		}
	}

	public void keyReleased(KeyEvent e) 
	{
		int key=e.getKeyCode();
		switch(key)
		{
			case KeyEvent.VK_LEFT:moveHorizontal=0;break;
			case KeyEvent.VK_RIGHT:moveHorizontal=0;break;
			case KeyEvent.VK_UP:moveVertical=0;break;
			case KeyEvent.VK_DOWN:moveVertical=0;break;
			case KeyEvent.VK_A:moveHorizontal=0;break;
			case KeyEvent.VK_D:moveHorizontal=0;break;
			case KeyEvent.VK_W:moveVertical=0;break;
			case KeyEvent.VK_S:moveVertical=0;break;
		}
	}

	public void keyTyped(KeyEvent e) 
	{
		
	}

	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{
		
	}

	public void mouseExited(MouseEvent e) 
	{
		
	}

	public void mousePressed(MouseEvent e) 
	{
		int x=e.getX();
		int y=e.getY();
		mouseX=x-frame.getInsets().left;
		mouseY=y-frame.getInsets().top;
		if(currentPage==1)
		{
			Login.handleMouse(mouseX, mouseY);
			return;
		}
		if(e.getButton()==MouseEvent.BUTTON1)
		{
			firing=true;
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		if(e.getButton()==MouseEvent.BUTTON1)
		{
			firing=false;
		}
	}
}