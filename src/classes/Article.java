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
    public final String nome;
    public final Photo photo;
    public final String descrizione;
    public final long creator_id;

    public Article(long article_id, String nome, Photo photo, String descrizione, long creator_id) {
        this.article_id = article_id;
        this.nome = nome;
        this.photo = photo;
        this.descrizione = descrizione;
        this.creator_id = creator_id;
    }
    
}
