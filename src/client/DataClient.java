/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import model.User;

/**
 *
 * @author lamit
 */
public class DataClient {
    static User userCurrent = new User();

    public static void setUserCurrent(User userCurrent) {
        DataClient.userCurrent = userCurrent;
    }

    public static User getUserCurrent() {
        return userCurrent;
    }
    
}
