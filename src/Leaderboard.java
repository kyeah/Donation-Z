import java.awt.Color;
import java.awt.Font;
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
	
	public static void draw(Graphics g)
	{
		g.setColor(Color.black);
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.PLAIN,24));
		ConnectDetails connect = new ConnectDetails();
        String database = connect.getDBName();
        Connection con;
        try {
            Connect dz = new Connect(database);
            con = connect.getConnection();
            ArrayList<String> leader=dz.viewScoreboard(con);
            int i=0;
            for(String s:leader)
            {
            	g.drawString(s, frame.getWidth()/2-100, 300+i*30);
            	i++;
            }
        }
        catch(Exception e)
        {
        	e.getMessage();
        }
	}

	public static void handleKey(char keyChar, int keyCode) 
	{
		if(keyCode==KeyEvent.VK_ESCAPE)
		{
			Runner.currentPage=0;
		}
	}
}