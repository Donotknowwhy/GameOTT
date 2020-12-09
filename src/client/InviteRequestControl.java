/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Message;
import model.User;
import ui.InviteRequest;

/**
 *
 * @author ASUS 
 */
public class InviteRequestControl {
    private ClientControl clientControl;
    private InviteRequest inviteRequest;
    private ArrayList<User> users;
    public InviteRequestControl(InviteRequest inviteRequest, ClientControl clientControl, ArrayList<User> users) {
        this.inviteRequest = inviteRequest;
        this.clientControl = clientControl;
        this.clientControl.setInviteRequestControl(this);
        this.inviteRequest.setAction(new ButtonAccept(), new ButtonReject());
        this.users = users;
    }
    class ButtonAccept implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Message messages = new Message(users, Message.MesType.ACCEPT_REQUEST);
            clientControl.sendData(messages);
        }
        
    }
    class ButtonReject implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Message messages = new Message(users, Message.MesType.DENY_REQUEST);
            clientControl.sendData(messages);
        }
        
    }
    
}
