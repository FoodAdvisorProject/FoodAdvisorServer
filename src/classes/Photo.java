/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Blob;
import java.util.Base64;

/**
 *
 * @author bp
 */
public class Photo {
    public final String base64;
    
    public final byte[] data;
    
    public Photo(String img) {
        this.base64= img;
        data=null;
    }
    public Photo(byte[] d){
        data=d;
        base64="";
    }
    
    public String toBase64() {
        return base64;
    }
    
    @Override
    public String toString() {
        return "Photo{" + "img=" + base64 + '}';
    }

    
}
