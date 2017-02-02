/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodadvisor;

import static database.DBUtils.printSQLException;
import database.DBDriver;
import database.DBFunctions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bp
 */
public class FoodAdvisor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DBDriver d=null;
        String utente="UTENTE",articolo="ARTICOLO",transazione="TRANSAZIONE";
        try {
            d = new DBDriver("test_db","root","root");
        } catch (Exception ex) {
            Logger.getLogger(FoodAdvisor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
        if(d==null){
            System.out.println("Failed to initialize DBDriver. ");
            return ;
        }
        
        DBFunctions dbf = new DBFunctions(d,utente,articolo,transazione);
        
        try {
            /*dbf.addUser("utente2", 
                    "lamiapass", 
                    "lamia@email.com", 
                    "maria", 
                    "defilippi",
                    1, 
                    "Qua vendo carciofi e cipolle",
                   "");
            
            System.out.println("added user");
            
            dbf.addArticle("Carciof", 1, "i miei son meglio", "");
            */
            System.out.println("Added article");
            
            dbf.addTransaction(new Long(1), 
                    1, 
                    3, 
                    0);
            System.out.println("Added transaction.");
        } catch (SQLException ex) {
            printSQLException(ex);
            System.out.println("Failed SOMETHING.");
        }
    }
    
}
