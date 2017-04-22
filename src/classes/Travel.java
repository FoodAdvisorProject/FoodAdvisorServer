/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bp
 */
public class Travel {
    
    private final LinkedList<Transaction> list;
    
    public Travel(LinkedList<Transaction> l) {
        list = l;
    }
    
    public LinkedList<Transaction> getTransactionList(){
        return list;
    }

    @Override
    public String toString() {
        String ret= "Travel{ ";
        for (Transaction t : list) ret+=t.toString()+", ";
        
        return ret +  '}';
    }
    
    
}
