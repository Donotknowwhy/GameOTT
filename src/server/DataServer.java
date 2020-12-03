/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 *
 * @author Admin
 */
public class DataServer {
    static Map<User,Socket> mapSocket = new HashMap<>();
    static ArrayList<User> usersMapSocket = new ArrayList<User>(mapSocket.keySet());
}
