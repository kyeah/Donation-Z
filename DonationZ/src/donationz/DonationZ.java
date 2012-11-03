/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package donationz;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Claudio
 */
public class DonationZ {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectDetails connect = new ConnectDetails();
        String database = connect.getDBName();
        Connection con;
        try {
            Connect dz = new Connect(database);
            con = connect.getConnection();
            //dz.addUser(con, "test", "-5", 2, "derp@derp.com", "11111");
            dz.viewScoreboard(con);
            //System.out.println(dz.getCharity(con, "claudio"));
            //dz.updatePA_key(con, "claudio", "9412831287");
        }
        catch(SQLException e) {
            System.err.println(e);
        }
    }
}
