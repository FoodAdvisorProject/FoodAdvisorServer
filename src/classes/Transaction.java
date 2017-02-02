/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author bp
 */
public class Transaction {
    public final long id,article_id,buyer_id,seller_id;
    public final float longitude,latitude;

    public Transaction(long id, long article_id, long buyer_id, long seller_id, float longitude, float latitude) {
        this.id = id;
        this.article_id = article_id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", article_id=" + article_id + ", buyer_id=" + buyer_id + ", seller_id=" + seller_id + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    


    
}
