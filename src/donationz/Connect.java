/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package donationz;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
/**
 *
 * @author Claudio
 */
public class Connect {
    String dbName;
    public Connect(String dbName) {
        this.dbName = dbName;
    }
    public void setDb(String dbName) {
        this.dbName = dbName;
    }
    public ArrayList<String> viewScoreboard(Connection con)
    throws SQLException {

    	ArrayList<String> leader=new ArrayList<String>();
    Statement stmt = null;
    String query = "select " + dbName + ".scoreboard.username, " + dbName + ".scoreboard.score, " + dbName + ".charities.charity_name " +
                   "from " + dbName + ".scoreboard INNER JOIN " + dbName + ".charities ON " + dbName + ".scoreboard.charity = " + dbName + ".charities.PRIMARY ORDER BY score DESC";
    System.out.println(query);
    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Username:   Score:  Charity Name:");
        while (rs.next()) {
            String user = rs.getString("username");
            String charity = rs.getString("charity_name");
            int score = rs.getInt("score");
            leader.add(user + "\t" + score + "\t" + charity);
        }
        return leader;
    } catch (SQLException e ) {
        System.err.println(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
    return null;
    
}
    public void addUser(Connection con, String user, String score, int charity, String paypal_email, String password) throws SQLException {
        con.setAutoCommit(false);
        String query = "INSERT INTO " + dbName + ".scoreboard" + " (username, score, charity, paypal_email, pword) VALUES (" + "'" + user + "', " + 
                score + ", " + Integer.toString(charity) + ", " + "'" + paypal_email + "'" + ", " + "'" + password + "'" + ")";
        Boolean duplicate = checkForDuplicates(con, user);
        Boolean valid = validCharity(con, charity);
        if(!duplicate && valid) {
            //No duplicates found
            Statement stmt = con.createStatement();
            stmt.addBatch(query);
            stmt.executeBatch();
            con.commit();
            System.out.println("User successfully added!");
        }
        else if (duplicate) {
            System.out.println("Someone already has that username!");
        }
        else if (!valid) {
            System.out.println("That is not a valid charity!");
        }
    }
    public void addCharity(Connection con, String charity_name, String username, String pay_pal) throws SQLException {
        int id = getCharityID(con, charity_name);
        if(id != 0) {
            updateCharity(con, username, id);
        }
        else {
            createCharity(con, charity_name);
        }
        updateEmail(con, username, pay_pal);
    }
    public void createCharity(Connection con, String charity_name) throws SQLException {
        con.setAutoCommit(false);
        String query = "INSERT INTO " + dbName + ".charities" + " (charity_name) VALUES (" + "'" + charity_name + "'" + ")";
        Statement stmt = con.createStatement();
        stmt.addBatch(query);
        stmt.executeBatch();
        con.commit();
    }
    public int getCharityID(Connection con, String charity_name) {
        int id = 0;
        String query = "select * " +
                   "from " + dbName + ".charities";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(charity_name.toLowerCase().compareTo(rs.getString("charity_name").toLowerCase()) == 0) {
                    id = rs.getInt("PRIMARY");
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return id;
        
    }
    public void updatePA_key(Connection con, String username, String pa_key) {
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rs.updateString("pa_key", pa_key);
                rs.updateRow();
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
    } 
    public void updateScore(Connection con, int score, String username) {
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int old_score = rs.getInt("score");
                if(old_score < score) {
                    rs.updateInt("score", score);
                    rs.updateRow();
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
    }
    public void updateCharity(Connection con, String username, int charity) {
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(validCharity(con, charity)) {
                    rs.updateInt("charity", charity);
                    rs.updateRow();
                }
                else {
                    System.out.println("That is not a valid charity!");
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
    }
    public void updateEmail(Connection con, String username, String email) {
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rs.updateString("paypal_email", email);
                rs.updateRow();
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
    }
    public void updatePassword(Connection con, String username, String password) {
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                rs.updateString("pword", password);
                rs.updateRow();
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
    }
    public int validAccount(Connection con, String username, String password) {
        String query = "select username, pword " +
                   "from " + dbName + ".scoreboard";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String user = rs.getString("username");
                String pass = rs.getString("pword");
                if((username.compareTo(user) == 0) && (pass.compareTo(password) == 0)) {
                    return 1;
                }
                else if ((username.compareTo(user) == 0) && (pass.compareTo(password) != 0)) {
                	return -1;
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return 0;
    }
    public String getEmail(Connection con, String username) {
        String email = "none";
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                email = rs.getString("paypal_email");
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return email;
    }
    public String getPA_key(Connection con, String username) {
        String PA_key = "none";
        String query = "select * " +
                   "from " + dbName + ".scoreboard WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                PA_key = rs.getString("pa_key");
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return PA_key;
    }
     public String getCharity(Connection con, String username) {
        String charity = "";
        String query = "select " + dbName + ".charities.charity_name" +
                   " from " + dbName + ".scoreboard" + " INNER JOIN " + "dbName" + ".charities ON " + dbName + ".scoreboard.charity = " + dbName + ".charities.PRIMARY" + " WHERE username = " + "'" + username + "'";
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                   ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                charity = rs.getString("charity_name");
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return charity;
    }
    public boolean validCharity(Connection con, int key) {
        String query = "select * " +
                   "from " + dbName + ".charities";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int charity = rs.getInt("PRIMARY");
                if(charity == key) {
                    return true;
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return false;
    }
    public boolean checkForDuplicates(Connection con, String user) {
        String query = "select username " +
                   "from " + dbName + ".scoreboard";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                if(username.compareTo(user) == 0) {
                    return true;
                }
            }
        } catch (SQLException e ) {
            System.err.println(e);
        }
        return false;
    }
    
}
