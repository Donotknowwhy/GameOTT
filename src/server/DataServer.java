/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Message;
import model.User;
import utils.Usage;

/**
 *
 * @author Admin
 */
public class DataServer {
    static Map<User,ObjectOutputStream> mapSocket = new HashMap<>();
    static ArrayList<User> usersMapSocket = new ArrayList<User>(mapSocket.keySet());
    static public void sendMessage(User user, Message m){
        try {
           mapSocket.get(user).writeObject(m);
        } catch (IOException ex) {
            Logger.getLogger(DataServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
