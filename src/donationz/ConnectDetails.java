/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package donationz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Claudio
 */

public class ConnectDetails {
    private String dbms = "mysql";
    private String serverName = "localhost"; //"mysql13.000webhost.com";
    private String portNumber = "3306";
    private String userName = "claudio";//"a4577019_claudio";
    private String password = "cloud999";
    private String dbName = "donationz";//"a4577019_leader";
    
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                       "jdbc:" + this.dbms + "://" +
                       this.serverName +
                       ":" + this.portNumber + "/",
                       connectionProps);
        } else if (this.dbms.equals("derby")) {
            conn = DriverManager.getConnection(
                       "jdbc:" + this.dbms + ":" +
                       this.dbName +
                       ";create=true",
                       connectionProps);
        }
        System.out.println("Connected to database");
        return conn;
    }
    public String getDBName() {
        return this.dbName;
    }
}
