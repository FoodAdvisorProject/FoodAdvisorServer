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
public class RichTransaction extends Transaction{
    public final User buyer,seller;
    public RichTransaction(long id, long article_id, long buyer_id, long seller_id, float longitude, float latitude, User buy, User sell) {
        super(id, article_id, buyer_id, seller_id, longitude, latitude);
        buyer=buy;
        seller=sell;
    }
    
    @Override
    public String toString(){
        return "Rich"+super.toString()+" "+buyer+" "+seller;
    }
}
