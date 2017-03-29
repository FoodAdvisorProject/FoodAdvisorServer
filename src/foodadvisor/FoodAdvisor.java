/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foodadvisor;

import classes.Photo;
import database.DBDriver;
import database.DBFunctions;
import java.sql.Connection;
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
        System.out.println("Hello, this is FoodAdvisor.");
        System.out.println("Work in progress.");
        try {
            DBDriver dd = new DBDriver("test_db","root","root");
            DBFunctions df = new DBFunctions(dd,"UTENTE","ARTICOLO","TRANSAZIONE");
            //df.addArticle("T1", 1, "ASDS", 1, 1, new Photo("123456"));
            
            System.out.println(df.getUserArticles(1));
            
        } catch (Exception ex) {
            Logger.getLogger(FoodAdvisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
