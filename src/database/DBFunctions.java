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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bp
 */

// This class contains all the functions that interact with the DB.
// All the functions are developed with the Fast-Fail policy 
// Runtime exception will be thrown in case of errors.
public class DBFunctions {
    private DBDriver driver;
    private String user_table, 
            article_table,
            transaction_table;
    
    // Public constructor that initializes this utility with necessary data
    public DBFunctions(DBDriver driver, String user_table, String article_table, String transaction_table) {
        this.driver = driver;
        this.user_table = user_table;
        this.article_table = article_table;
        this.transaction_table = transaction_table;
    }

    // @NEW Add the transaction with the NULL-seller
    // at the time of article creation.
    // This is used to find the end of the trip of the article.
    // 
    // the tuple (name, id_creator) has to be unique 
    public void addArticle(String name,
                           long creator_id,
                           String description,
                           float longitude,
                           float latitude,
                           Photo photo) throws SQLException{
        String query ="INSERT INTO "+article_table+
                "( name,id_creator,description) values (?,?,?)";
        
        String query1 ="INSERT INTO "+transaction_table+
                "( id_article,id_buyer,longitude,latitude,id_seller) values (?,?,?,?,NULL)";
        Connection conn = driver.getConnection();
        conn.setAutoCommit(false);
        
        try{
        PreparedStatement pstmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
        
        pstmt.setString (1, name);
        pstmt.setLong   (2, creator_id);
        pstmt.setString (3, description);
        
        
        pstmt.executeUpdate();
        
        ResultSet rs = pstmt.getGeneratedKeys();
        
        long article_id=-1;
        if(rs.next()) article_id=rs.getLong(1);
        else throw new SQLException("Cannot retrieve the generated key");
        
        pstmt=conn.prepareStatement(query1);
        
        pstmt.setLong  (1, article_id);
        pstmt.setLong  (2, creator_id);
        pstmt.setFloat (3, longitude);
        pstmt.setFloat (4, latitude );
        
        pstmt.executeUpdate();
        
        conn.commit();
        }catch(SQLException ex){
            conn.rollback();
            throw ex;
        }finally{
        conn.close();
        }
    }
    
    // This function adds an user to the DB with proper data
    // @FIX The photo need to be added
    // this depend on the App architecture, 
    // Photo will contain path or data?
    //
    // The login_name and the email has to be unique
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
    
    public void addUser(String login_name,
                        String login_passw,
                        String email,
                        String name,
                        String second_name,
                        Integer is_enterprise,
                        String enterprise_description,
                        String photo) throws SQLException{
        addUser(login_name,login_passw,email,name,second_name,is_enterprise,enterprise_description,new Photo(photo));
        
    }
    

    // This function adds a Transaction to the DB with proper data
    // Note that the tuple (id_article,id_buyer,id_seller) has to be unique
    // otherwise an exception will be thrown
    public void addTransaction(long id_article,
                               long id_buyer,
                               long id_seller,
                               float longitude,
                               float latitude  ) throws SQLException{
        String query;
        
        query="INSERT INTO "+transaction_table+
                "( id_article,id_buyer,longitude,latitude,id_seller) values (?,?,?,?,?)";
        
        Connection conn = driver.getConnection();
        
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        pstmt.setLong  (1, id_article);
        pstmt.setLong  (2, id_buyer);
        pstmt.setFloat (3, longitude);
        pstmt.setFloat (4, latitude );
        pstmt.setLong  (5, id_seller);
        
        
        pstmt.executeUpdate();
        
        //conn.commit();
        conn.close();
    }
    
    // This functions retrieve an Article from a given ID
    public Article getArticle(long id_article) throws SQLException{
        String query ="SELECT * FROM "+article_table+" WHERE id="+id_article;
        
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        Blob b = rset.getBlob(5);
        Photo p;
        if(b!= null) p=new Photo(b.getBytes(1,(int)b.length())); else p = new Photo("");
        
        Article ret = new Article(rset.getLong(1),
                            rset.getString(2),
                            rset.getLong(3),
                            rset.getString(4),
                            p);
        
        conn.close();
        return ret;
        
    }
    
    // this function gets an User from a given id_user
    public User getUser(long id_user) throws SQLException{
        String query ="SELECT * FROM "+user_table+" WHERE id="+id_user;
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return null;
        Blob b = rset.getBlob(9);
        Photo p;
        if(b!= null) p=new Photo(b.getBytes(1,(int)b.length())); else p = new Photo("");
        User ret = new User(rset.getLong(1),
                rset.getString(2),
                rset.getString(3),
                rset.getString(4),
                rset.getString(5),
                rset.getString(6),
                rset.getBoolean(7),
                rset.getString(8),
                p);
        
        conn.close();
        return ret;
    }
    public long getUserIdByEmail(String email) throws SQLException{
        String query ="SELECT id FROM "+user_table+" WHERE email='"+email+"'";
        
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return -1;
        long id = rset.getLong(1);
        
        conn.close();
        return id;
    }
    
    public long getUserIdByLogin(String login) throws SQLException{
        String query ="SELECT id FROM "+user_table+" WHERE login_name='"+login+"'";
        
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        if(!rset.next()) return -1;
        long id = rset.getLong(1);
        
        conn.close();
        return id;
    }
    // This function gets a Transaction from a given id_transaction
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
    
    // This is the overload of the previous function,
    // usefull when searching transaction by values
    public Transaction getTransaction(long article_id,long buyer_id) throws SQLException{
        String query ="SELECT * FROM "+transaction_table+
                " WHERE id_article = "+article_id +
                " AND   id_buyer = "+buyer_id;
        
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
    // Overload of the previous function
    public Transaction getTransaction(Article art,User user) throws SQLException{
        return getTransaction(art.article_id, user.user_id);
    }
    
    // This function retrieve an ordered list of transaction 
    // that starts with the one in which user_buyer bought article
    // to the one in which the article has been created
    public Travel  getArticleTravel(long article_id,long user_buyer_id) throws SQLException{
        
        LinkedList<Transaction> ret = new LinkedList();
        
        Transaction t = getTransaction(article_id,user_buyer_id);
        
        while(t.seller_id !=0) {
            ret.add(t);
            t=getTransaction(article_id,t.seller_id);
        }
        ret.add(t);
        
        return new Travel(ret);
        
    }
    public Travel getArticleTravel(long tran_id) throws SQLException{
        Transaction t = getTransaction(tran_id);
        return getArticleTravel(t.article_id,t.buyer_id);
    }
    // Overload of the previous one
    public Travel getArticleTravel(Article art,User user_buyer) throws SQLException{
        return getArticleTravel(art.article_id, user_buyer.user_id);
    }
    
    // @IMPL 
    // This function returns a list of articles created by the user
    public List<Article> getUserArticles(long user_id) throws SQLException{
        String query ="SELECT * FROM "+article_table+
                " WHERE id_creator = "+user_id;
        
        Connection conn = driver.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery(query);
        
        List<Article> l = new LinkedList<>();
        
        
        while(rset.next()){
            Blob b = rset.getBlob(5);
            Photo p;
            if(b!=null) p=new Photo(b.getBytes(1, (int) b.length())); else p=new Photo("");
            l.add(new Article(rset.getLong(1),
                    rset.getString(2),
                    rset.getLong(3),
                    rset.getString(4),
                    p));
        
        }
        
        query ="SELECT id_article FROM "+transaction_table+
                " WHERE id_buyer="+user_id;
        
        rset = stmt.executeQuery(query);
        
        while(rset.next()){
            l.add(getArticle(rset.getLong(1)));
        }
        
        conn.close();
        return l;
    }
    
    public List<Article> getUserArticles(User user) throws SQLException{
        return getUserArticles(user.user_id);
    }
}
