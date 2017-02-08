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
    //public final Blob img;
    /*
    			pre = con
					.prepareStatement("insert into blobtest (pic_name,pic_file) values (?,?)");
			pre.setString(1, picfile.getName());
			pre.setBinaryStream(2, fis, (int) picfile.length());
                        int count = pre.executeUpdate();
    */
    public final String img;
    public final byte[] data;
    public Photo(String img) {
        this.img= img;
        data=null;
    }
    public Photo(byte[] d){
        data=d;
        img="";
    }
    
    public String toBase64() {
        
        Base64.Encoder e = Base64.getEncoder();
        //ISO88
        if(data!=null)
            return e.encodeToString(data);
        else
            return "";
    }
    
    @Override
    public String toString() {
        return "Photo{" + "img=" + img + '}';
    }
    
    
}
