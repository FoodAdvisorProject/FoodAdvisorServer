/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import classes.Article;
import classes.Photo;
import classes.Transaction;
import classes.Travel;
import classes.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

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

    
    public void addArticle(String name,
                           long creator_id,
                           String description,
                           Photo photo) throws SQLException{
        String query ="INSERT INTO "+article_table+
                "( name,id_creator,description) values (?,?,?)";
        
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setString (1, name);
        pstmt.setLong   (2, creator_id);
        pstmt.setString (3, description);
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    
    
    public void addUser(String login_name,
                        String login_passw,
                        String email,
                        String name,
                        String second_name,
                        Integer is_enterprise,
                        String enterprise_description,
                        Photo photo) throws SQLException{
        
        String query1 = "INSERT INTO "+user_table+
                " (login,passw,email,name,second_name,is_enterprise,enterprise_description) values (?,?,?,?,?,?,?);";
        String query2 = "INSERT INTO "+user_table+
                " (login,passw,email,name,second_name,is_enterprise) values (?,?,?,?,?,?);";
        
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt;
        if(is_enterprise>0)
            pstmt= conn.prepareStatement(query1);
        else 
            pstmt= conn.prepareStatement(query2);
            
        pstmt.setString (1, login_name);
        pstmt.setString (2, login_passw);
        pstmt.setString (3, email);
        pstmt.setString (4, name);
        pstmt.setString (5, second_name);
        pstmt.setInt    (6, is_enterprise);
        if (is_enterprise>0) pstmt.setString (7, enterprise_description);
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    

    //@FIX check that transaction doesn't exist yet
    //id_seller == 0 means that buyer is the creator of the article
    public void addTransaction(long id_article,
                               long id_buyer,
                               long id_seller,
                               float longitude,
                               float latitude  ) throws SQLException{
        String query;
        if(id_seller>0){
         query="INSERT INTO "+transaction_table+
                "( id_article,id_buyer,longitude,latitude,id_seller) values (?,?,?,?,?)";
        }
        else query = "INSERT INTO "+transaction_table+
                "( id_article,id_buyer,longitude,latitude) values (?,?,?,?)";
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setLong  (1, id_article);
        pstmt.setLong  (2, id_buyer);
        pstmt.setFloat (3, longitude);
        pstmt.setFloat (4, latitude );
        if (id_seller>0) pstmt.setLong  (5, id_seller);
        
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    
    public Article getArticle(long id_article) throws SQLException{
        String query ="SELECT * FROM "+article_table+" WHERE id="+id_article;
        
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        
        Article ret = new Article(rset.getLong(1),
                            rset.getString(2),
                            rset.getLong(3),
                            rset.getString(4),
                            new Photo(rset.getBlob(5)));
        
        conn.close();
        return ret;
        
    }
    
    public User getUser(long id_user) throws SQLException{
        String query ="SELECT * FROM "+user_table+" WHERE id="+id_user;
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        
        User ret = new User(rset.getLong(1),
                rset.getString(2),
                rset.getString(3),
                rset.getString(4),
                rset.getString(5),
                rset.getString(6),
                rset.getBoolean(7),
                rset.getString(8),
                new Photo(rset.getBlob(9)));
        
        conn.close();
        return ret;
    }
    
    public Transaction getTransaction(long id_transaction) throws SQLException{
        String query ="SELECT * FROM "+transaction_table+" WHERE id="+id_transaction;
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        
        Transaction ret= new Transaction(rset.getLong(1),
                rset.getLong(2),
                rset.getLong(3),
                rset.getLong(4),
                rset.getFloat(5),
                rset.getFloat(6));
        
        conn.close();
        return ret;
    
    }
    
    public Transaction getTransaction(long article_id,long buyer_id) throws SQLException{
        String query ="SELECT * FROM "+transaction_table+" WHERE id_article = "+article_id +" AND id_buyer = "+buyer_id;
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        
        Transaction ret= new Transaction(rset.getLong(1),
                rset.getLong(2),
                rset.getLong(3),
                rset.getLong(4),
                rset.getFloat(5),
                rset.getFloat(6));
        
        conn.close();
        return ret;
    }
    
    public Transaction getTransaction(Article art,User user) throws SQLException{
        return getTransaction(art.article_id, user.user_id);
    }
    
    //@IMPL
    public Travel  getArticleTravel(Article art,User user) throws SQLException{
        
        LinkedList<Transaction> ret = new LinkedList();
        
        Transaction t = getTransaction(art.article_id,user.user_id);
        while(t.seller_id != 0) {
            ret.add(t);
            t=getTransaction(art.article_id,t.seller_id);
            
        }
        return new Travel(ret);
    }
}
