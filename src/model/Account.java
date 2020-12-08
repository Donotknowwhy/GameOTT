/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
import java.io.Serializable;

/**
 *
 * @author lamit
 */
public class Account implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    private int id;
    private String username;
    private String password;

    public Account() {
    }
    
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Account(String username) {
        this.username = username;
    }
    
    public Account(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{username=" + username + ", password=" + password + '}';
    }
    
}