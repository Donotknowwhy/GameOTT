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
public class Message implements Serializable {

    private static final long serialVersionUID = 6529685098267757693L;
    private Object object;
    private MesType mesType;

    public Message() {
    }
    
    public Message(Object object, MesType mesType) {
        this.object = object;
        this.mesType = mesType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public MesType getMesType() {
        return mesType;
    }

    public void setMesType(MesType mesType) {
        this.mesType = mesType;
    }

    public static enum MesType {
        //login,register
        LOGIN,
        LOGIN_SUCCESS,
        LOGIN_FAIL,
        REGISTER,
        REGISTER_FAIL,
        REGISTER_SUCCESS,
        //yeu cau choi game
        REQUEST_PLAY,
        ACCEPT_REQUEST,
        DENY_REQUEST,
        //choi game
        START_GAME,
        SEND_CHOICE,
        REPLY_RESULT,
        //scoreboard
        GET_SCOREBOARD,
        REPLY_SCOREBOARD,
        //list
        LIST_USERS,
        LIST_FULL,
        LIST_NULL,
        //moi
        INVITE_USER
    }
}