import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;

public class Runner extends JPanel implements MouseMotionListener, KeyListener, MouseListener
{
	private static final long serialVersionUID = -318708369614176297L;
	static JFrame frame;
	static long refreshTimer=System.currentTimeMillis();
	static Player player;
	static int mouseX,mouseY;
	static int moveHorizontal,moveVertical;
	static boolean firing=true;
	
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
	}
	
	public void paintComponent(Graphics g)
	{
		if(System.currentTimeMillis()-refreshTimer>32)
		{
			refreshTimer=System.currentTimeMillis();
			drawBackground(g);
			player.move(moveHorizontal, moveVertical);
			player.setAngle(mouseX,mouseY);
			player.draw(g);
		}
		repaint();
	}

	private void drawBackground(Graphics g) 
	{
		g.setColor(Color.white);
		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
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