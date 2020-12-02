/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lamit
 */
public class ConnectDatabase {
    private Connection conn;
    private String jdbcURL = "jdbc:mysql://localhost:3306/testdbgame";
    private String jdbcUsername = "root";
    private String jdbcPassword = "xuankien99";
    private static ConnectDatabase connectDb = null;
    
    private ConnectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername,
                    jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static ConnectDatabase getInstance(){
        if(connectDb == null){
            connectDb = new ConnectDatabase();
        }
        return connectDb;
    }
    public Connection getConnection(){
        return conn;
    }
}
