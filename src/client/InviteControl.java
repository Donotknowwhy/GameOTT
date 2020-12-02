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
import ui.ListFrm;

/**
 *
 * @author Admin
 */
public class InviteControl {
    private ArrayList<User> user = new ArrayList();
    private ListFrm listFrm;
    private ClientControl clientControl;
    private User userRecent;
    public InviteControl(ClientControl clientControl,ListFrm listFrm) {
        this.clientControl = clientControl;
        this.listFrm = listFrm;
        Message mesRei = clientControl.receiveData();
        user = (ArrayList<User>) mesRei.getObject();
        listFrm.showUsers(user);
        listFrm.setAction(new ButtonInvite());
    }
    public void setUser(User user){
        this.userRecent = user;
    }
    
    class ButtonInvite implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            User u = listFrm.choseData();
            ArrayList<User> users1 = new ArrayList<>();
            users1.add(userRecent);
            users1.add(u);
            if(u.isStatus() != false){
                Message message = new Message(users1, Message.MesType.INVITE_USER);
                clientControl.sendData(message);
            }else{
                listFrm.thongBao("tai khoan nay khong online!");
            }
        }
        
    }
}
