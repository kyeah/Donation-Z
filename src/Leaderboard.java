import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import donationz.Connect;
import donationz.ConnectDetails;

public class Leaderboard
{
	static JFrame frame=Runner.frame;
	static ArrayList<String> leader=new ArrayList<String>();
	
	static
	{
		ConnectDetails connect = new ConnectDetails();
        String database = connect.getDBName();
        Connection con;
        try {
            Connect dz = new Connect(database);
            con = connect.getConnection();
            leader=dz.viewScoreboard(con);
            System.out.println(leader);
        }
        catch(Exception e)
        {
        	e.getMessage();
        }
	}
	
	public static void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(Color.white);
		try {
			g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("resources"+File.separator+"fonts"+File.separator+"black.ttf")));
			g.setFont(g.getFont().deriveFont(36f));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawString("Current Leaders:", frame.getWidth()/2-100, 250);
		int i=0;
        for(String ss:leader)
        {
        	String arr[]=ss.split("\t");
        	g.drawString(arr[0], frame.getWidth()/2-450, 300+i*30);
        	g.drawString(arr[1], frame.getWidth()/2-200, 300+i*30);
        	g.drawString(arr[2], frame.getWidth()/2+50, 300+i*30);
        	i++;
        }
        g.drawString("Press ESC to return to Main Menu", frame.getWidth()/2-200, frame.getHeight()-50);
	}

	public static void handleKey(char keyChar, int keyCode) 
	{
		if(keyCode==KeyEvent.VK_ESCAPE)
		{
			Runner.currentPage=0;
		}
	}
}