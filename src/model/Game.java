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
import java.util.Date;

/**
 *
 * @author lamit
 */
public class Game implements Serializable{
    private static final long serialVersionUID = 6529685098267757692L;
    private int id;
    private Date timeCreated;

    public Game() {
    }

    
    
    public Game(int id, Date timeCreated) {
        this.id = id;
        this.timeCreated = timeCreated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
    
}