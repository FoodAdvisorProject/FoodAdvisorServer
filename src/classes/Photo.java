/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Blob;

/**
 *
 * @author bp
 */
public class Photo {
    public final Blob img;

    public Photo(Blob img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Photo{" + "img=" + img + '}';
    }
    
    
}
