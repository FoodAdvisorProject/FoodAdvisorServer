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
public class Article {
    public final long article_id;
    public final String name;
    public final Photo photo;
    public final String description;
    public final long creator_id;

    public Article(long article_id, String name, long creator_id,String description,Photo photo) {
        this.article_id = article_id;
        this.name = name;
        this.creator_id = creator_id;
        this.description = description;
        this.photo = photo;
        
    }

    @Override
    public String toString() {
        return "Article{" + "article_id=" + article_id + ", name=" + name + ", photo=" + photo + ", description=" + description + ", creator_id=" + creator_id + '}';
    }
    
    
    
}
