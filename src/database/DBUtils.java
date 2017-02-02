/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;

/**
 *
 * @author bp
 */
public class DBUtils {
    
    public static boolean ignoreSQLException(String sqlState) {

    if (sqlState == null) {
      System.out.println("The SQL state is not defined!");
      return false;

    }
    // X0Y32: Jar file already exists in schema
    if (sqlState.equalsIgnoreCase("X0Y32"))
      return true;

    // 42Y55: Table already exists in schema
    if (sqlState.equalsIgnoreCase("42Y55"))
      return true;

    return false;

  }
    
    
  
  public static void printSQLException(SQLException ex) {

    for (Throwable e : ex) {
      if (e instanceof SQLException) {
        if (ignoreSQLException(((SQLException)e).getSQLState()) == false) {

          e.printStackTrace(System.err);

          System.err.println("SQLState: " + ((SQLException)e).getSQLState());

          System.err.println("Error Code: " + ((SQLException)e).getErrorCode());

          System.err.println("Message: " + e.getMessage());

          Throwable t = ex.getCause();

          while (t != null) {
            System.out.println("Cause: " + t);
            t = t.getCause();
          }
        }
      }
    }
  }
  
}
