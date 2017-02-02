/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author bp
 */
public class DBFunctions {
    private DBDriver driver;
    private String user_table, 
            article_table,
            transaction_table;

    public DBFunctions(DBDriver driver, String user_table, String article_table, String transaction_table) {
        this.driver = driver;
        this.user_table = user_table;
        this.article_table = article_table;
        this.transaction_table = transaction_table;
    }

    
    //@FIX photo need to be added
    public void addUser(String login_name,
                        String login_passw,
                        String email,
                        String nome,
                        String cognome,
                        Integer is_azienda,
                        String descrizione_azienda,
                        String photo) throws SQLException{
        
        String query1 = "INSERT INTO "+user_table+
                " (login,passw,email,nome,cognome,is_azienda,descrizione_azienda) values (?,?,?,?,?,?,?);";
        String query2 = "INSERT INTO "+user_table+
                " (login,passw,email,nome,cognome,is_azienda) values (?,?,?,?,?,?);";
        
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt;
        if(is_azienda>0)
            pstmt= conn.prepareStatement(query1);
        else 
            pstmt= conn.prepareStatement(query2);
            
        pstmt.setString (1, login_name);
        pstmt.setString (2, login_passw);
        pstmt.setString (3, email);
        pstmt.setString (4, nome);
        pstmt.setString (5, cognome);
        pstmt.setInt    (6, is_azienda);
        if (is_azienda>0) pstmt.setString (7, descrizione_azienda);
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    
    //@FIX photo need to be added
    public void addArticle(String name,
                           long creator_id,
                           String description,
                           String photo) throws SQLException{
        String query ="INSERT INTO "+article_table+
                "( nome,id_creatore,descrizione) values (?,?,?)";
        
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setString (1, name);
        pstmt.setLong   (2, creator_id);
        pstmt.setString (3, description);
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    
    //@FIX coord need to be added
    //id_seller == 0 means that buyer is the creator of the article
    public void addTransaction(long id_article,
                               long id_buyer,
                               long id_seller,
                               float coord ) throws SQLException{
        String query;
        if(id_seller>0){
         query="INSERT INTO "+transaction_table+
                "( id_articolo,id_compratore,coordinate,id_venditore) values (?,?,?,?)";
        }
        else query = "INSERT INTO "+transaction_table+
                "( id_articolo,id_compratore,coordinate) values (?,?,?)";
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setLong  (1, id_article);
        pstmt.setLong  (2, id_buyer);
        pstmt.setFloat (3, coord);
        
        if (id_seller>0) pstmt.setLong  (4, id_seller);
        
        
        pstmt.executeUpdate();
        
        //conn.commit();
        //conn.close();
    }
}
