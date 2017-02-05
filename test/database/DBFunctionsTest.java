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
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bp
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// this signature force JUnit to run method based on name order
public class DBFunctionsTest {
    private static DBDriver d;
    private static DBFunctions dbf;
    
    private static String user_table="UTENTE",
            article_table="ARTICOLO",
            transaction_table="TRANSAZIONE";
    
    //@FIX
    private static String database="test_db",
                user="root",
                password="root";
        
    public DBFunctionsTest() {

        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        d  =null;
        dbf=null;
        
        try {
            d =   new DBDriver(database,user,password);
            
        } catch (Exception ex) {
            System.out.println("FATAL: Error loading DBDrivers.");
            System.exit(1);
        }
        dbf = new DBFunctions(d,user_table,article_table,transaction_table);
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of addArticle method, of class DBFunctions.
     */
    // Note that the tuple (name,creator_id) has to be unique.
    @Test
    public void testAddArticle() throws Exception {
        System.out.println("AddArticle");
        
        String name = "Test12";
        long creator_id = 1;
        String description = "This is a test Article.";
        Photo photo = null;
        
        dbf.addArticle(name, creator_id, description,1,2, photo);
        
    }

    /**
     * Test of addUser method, of class DBFunctions.
     */
    // Note that login_name and email email has to be unique.
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        
        String login_name = "Test12";
        String login_passw = "Test1";
        String email = "Test12";
        String name = "test";
        String second_name = "test";
        Integer is_enterprise = 1;
        String enterprise_description = "i am an enterprise";
        Photo photo = null;
        
        dbf.addUser(login_name, login_passw, email, name, second_name, is_enterprise, enterprise_description, photo);
    
    }
    
    //@Test
    public void testAddUser1() throws Exception {
        System.out.println("addUser1");
        
        String login_name = "Test2";
        String login_passw = "Test1";
        String email = "Test2";
        String name = "test";
        String second_name = "test";
        Integer is_enterprise = 1;
        String enterprise_description = "i am an enterprise";
        Photo photo = null;
        
        dbf.addUser(login_name, login_passw, email, name, second_name, is_enterprise, enterprise_description, photo);
    
    }

    /**
     * Test of addTransaction method, of class DBFunctions.
     */
    // Note that the tuple (article,seller,buyer) has to be unique.
    @Test
    public void testAddTransaction() throws Exception {
        System.out.println("addTransaction");
        
        long id_article = 1;
        long id_buyer = 11;
        long id_seller = 2;
        float longitude = 13.3F;
        float latitude = 0.0F;
        
        dbf.addTransaction(id_article, id_buyer, id_seller, longitude, latitude);
    
    }

    /**
     * Test of getArticle method, of class DBFunctions.
     */
    @Test
    public void testGetArticle() throws Exception {
        System.out.println("getArticle");
        
        long id_article = 1;
        Article result = dbf.getArticle(id_article);
        
        System.out.println(result);
    }

    /**
     * Test of getUser method, of class DBFunctions.
     */
    @Test
    public void testGetUser() throws Exception {
        System.out.println("getUser");
        long id_user = 1;
        
        User result = dbf.getUser(id_user);
        System.out.println(result);
    }

    /**
     * Test of getTransaction method, of class DBFunctions.
     */
    @Test
    public void testGetTransaction_long() throws Exception {
        System.out.println("getTransaction");
        long id_transaction = 1L;
        Transaction result = dbf.getTransaction(id_transaction);
        System.out.println(result);
    }

    /**
     * Test of getTransaction method, of class DBFunctions.
     */
    @Test
    public void testGetTransaction_long_long() throws Exception {
        System.out.println("getTransaction1");
        long article_id = 1L;
        long user_id = 1L;
        Transaction result = dbf.getTransaction(article_id, user_id);
        System.out.println(result);
    }

    /**
     * Test of getTransaction method, of class DBFunctions.
     */
    @Test
    public void testGetTransaction_Article_User() throws Exception {
        System.out.println("getTransaction2");
        System.out.println("This fails due to the parallel execution of tests.");
        Article art = dbf.getArticle(1);
        User user = dbf.getUser(1);
        
        Transaction result = dbf.getTransaction(art, user);
        
        System.out.println(result);
    }

    /**
     * Test of getArticleTravel method, of class DBFunctions.
     */
    @Test
    public void testGetArticleTravel() throws Exception {
        System.out.println("getArticleTravel ");
        
        Travel t = dbf.getArticleTravel(1,2);
        System.out.println("Travel result: "+t);
    }
    
    @Test
    public void testGetUserArticles() throws Exception {
        System.out.println("getUserArticles ");
        
        String res = "";
        List<Article> l = dbf.getUserArticles(1);
        for(Article a: l) res+=a+" ";
        System.out.println("Result "+res );
        
    }
}
