/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import model.Message;
import model.User;
import ui.ListFrm;

/**
 *
 * @author Admin
 */
public class InviteControl {
    private ArrayList<User> user = new ArrayList();
    private ListFrm listFrm;
    private ClientControl clientControl;

    public InviteControl(ClientControl clientControl,ListFrm listFrm) {
        this.clientControl = clientControl;
        this.listFrm = listFrm;
        Message mesRei = clientControl.receiveData();
        user = (ArrayList<User>) mesRei.getObject();
        listFrm.showUsers(user);
    }
    
}
