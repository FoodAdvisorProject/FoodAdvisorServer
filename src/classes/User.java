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
public class User {
    public final long user_id;
    public final String login;
    public final String passw;
    public final String email;
    public final String name;
    public final String second_name;
    public final boolean    bool;
    public final String enterprise_description;
    public final Photo  photo;

    public User(long id, String login, String passw, String email, String name, String second_name, boolean bool, String enterprise_description, Photo photo) {
        this.user_id = id;
        this.login = login;
        this.passw = passw;
        this.email = email;
        this.name = name;
        this.second_name = second_name;
        this.bool = bool;
        this.enterprise_description = enterprise_description;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + user_id + ", login=" + login + ", passw=" + passw + ", email=" + email + ", name=" + name + ", second_name=" + second_name + ", bool=" + bool + ", enterprise_description=" + enterprise_description + ", photo=" + photo + '}';
    }
    
}
