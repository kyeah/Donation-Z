import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.Box;

import donationz.Connect;
import donationz.ConnectDetails;


public class InfoPrompt extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField email;
	private JTextField charity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InfoPrompt dialog = new InfoPrompt();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InfoPrompt() {
		setBounds(100, 100, 340, 177);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblPayPalEmail = new JLabel("Pay Pal Email");
			contentPanel.add(lblPayPalEmail);
		}
		{
			email = new JTextField();
			contentPanel.add(email);
			email.setColumns(25);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			contentPanel.add(horizontalStrut);
		}
		{
			JLabel lblCharityEmail = new JLabel("Charity Name");
			contentPanel.add(lblCharityEmail);
		}
		{
			charity = new JTextField();
			contentPanel.add(charity);
			charity.setColumns(25);
		}
		{
			Component horizontalStrut = Box.createHorizontalStrut(20);
			contentPanel.add(horizontalStrut);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(!(email.getText().length()>0 && charity.getText().length()>0 && email.getText().contains("@")))
							return;
						ConnectDetails connect = new ConnectDetails();
				        String database = connect.getDBName();
				        Connection con;
				        try {
				            Connect dz = new Connect(database);
				            con = connect.getConnection();
				            dz.addCharity(con, charity.getText(), Login.username, email.getText());
				            Runner.currentPage=3;
				            dispose();
				        }
				        catch(Exception ee)
				        {ee.getMessage();}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
