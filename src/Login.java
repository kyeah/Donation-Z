import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

import donationz.Connect;
import donationz.ConnectDetails;

public class Login
{
	static JFrame frame=Runner.frame;
	static BufferedImage background,textFields;
	static String username="aa";
	static String password="asdfsd";
	static boolean selectedUsername=true;
	
	static
	{
		try {
			background=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"login_background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			textFields=ImageIO.read(new File("resources"+File.separator+"graphics"+File.separator+"login_text.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void draw(Graphics g)
	{
		g.drawImage(background, -(background.getWidth()-frame.getWidth())/2, -(background.getHeight()-frame.getHeight())/2, null);
		g.drawImage(textFields,frame.getWidth()/2-textFields.getWidth()/2,frame.getHeight()/2-textFields.getHeight()/2-frame.getInsets().top,null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,56));
		int x=frame.getWidth()/2-830/2+55;
		int y=frame.getHeight()/2-frame.getInsets().top-100;
		g.drawString(username, x, y);
		if(selectedUsername)
		{
			g.drawLine(x, y+15, x+500, y+15);
		}
		
		String pass="";
		for(int i=0; i<password.length(); i++)
			pass+="*";
		x=frame.getWidth()/2-830/2+55;
		y=frame.getHeight()/2-frame.getInsets().top+140;
		g.drawString(pass, x, y);
		if(!selectedUsername)
		{
			g.drawLine(x, y+15, x+500, y+15);
		}
	}
	
	public static void handleMouse(int x, int y)
	{
		if(x>frame.getWidth()/2-textFields.getWidth()/2 && x<frame.getWidth()/2+textFields.getWidth()/2)
		{
			if(y>frame.getHeight()/2-textFields.getHeight()/2-frame.getInsets().top && y<frame.getHeight()/2+textFields.getHeight()/2-frame.getInsets().top)
			{
				if(y<frame.getHeight()/2)
				{
					selectedUsername=true;
				}
				else
				{
					selectedUsername=false;
				}
			}
		}
	}
	
	public static void handleKey(char ch, int val)
	{
		if(val==KeyEvent.VK_ENTER)
		{
			ConnectDetails connect = new ConnectDetails();
	        String database = connect.getDBName();
	        Connection con;
	        try {
	            Connect dz = new Connect(database);
	            con = connect.getConnection();
	            if(dz.validAccount(con, username, password))
	            {
	            	Runner.currentPage++;
	            }
	            else
	            {
	            	dz.addUser(con, username, "0", 4, "none", password);
	            }
	        }
	        catch(SQLException e) {
	            System.err.println(e);
	        }
			
		}
		else if(val==KeyEvent.VK_BACK_SPACE)
		{
			if(selectedUsername && username.length()>0)
			{
				username=username.substring(0, username.length()-1);
			}
			else if(!selectedUsername && password.length()>0)
			{
				password=password.substring(0, password.length()-1);
			}
		}
		if(!(Character.isDigit(ch) || Character.isLetter(ch)))
			return;
		if(selectedUsername)
		{
			username+=ch;
		}
		else
		{
			password+=ch;
		}
	}
}

