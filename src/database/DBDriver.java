/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import static database.DBUtils.printSQLException;

// notice , do not import com.mysql.jdbc.* 
// or you will have problems.

/**
 *
 * @author bp
 */
public class DBDriver {
    // Notice that each job has to have its own Connection.
    // Connections are not thread safe, they are in fact transactions
    String database;
    String user,passw;
    String server="localhost",port="3306";
    public DBDriver(String database, String user, String passw) throws Exception {
        this.database = database;
        this.user = user;
        this.passw = passw;
        
        //instead use Class newInstance method 
        //to load the module
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        
    }
    
    
    public Connection getConnection() throws SQLException{
        Properties connectionProps = new Properties();

        connectionProps.put("user",user);
        connectionProps.put("password",passw);
        
        //@FIX notice that auto commit adds overhead creating large rollback tables
        return DriverManager.getConnection(
                "jdbc:mysql://"+server+":"+port+"/"+database+"?useSSL=false",
                 connectionProps);
        
    }
     

}