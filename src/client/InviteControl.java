/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Game;
import model.Message;
import model.User;
import ui.GameFrm;
import ui.InviteRequest;
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

    public InviteControl(ClientControl clientControl, ListFrm listFrm) {
        this.clientControl = clientControl;
        this.clientControl.setInviteControl(this);
        this.listFrm = listFrm;
        listFrm.setAction(new ButtonInvite());
    }

    public void setUser(User user) {
        this.userRecent = user;
    }

    class ButtonInvite implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User u = listFrm.choseData(user);
            ArrayList<User> users1 = new ArrayList<>();
            users1.add(userRecent);
            users1.add(u);
            System.out.println(userRecent.toString());
            System.out.println(u.toString());
            if (u.isStatus() != false) {
                Message message = new Message(users1, Message.MesType.INVITE_USER);
                clientControl.sendData(message);
            } else {
                listFrm.thongBao("tai khoan nay khong online!");
            }
        }
    }

    public void showListUser(Message mesRei) {
        user = (ArrayList<User>) mesRei.getObject();
        listFrm.showUsers(user);
    }

    public void showInviteRequest() {
        
        InviteRequest inviteRequest = new InviteRequest();
        inviteRequest.setVisible(true);
        InviteRequestControl inviteRequestControl
                  = new InviteRequestControl(inviteRequest, clientControl, user);
    }

    public void showGameConsole(Message mesRecei) {
        GameFrm gameFrm = new GameFrm();
        Game game = (Game) mesRecei.getObject();
        GameControl gameControl = new GameControl(gameFrm, clientControl, game);
    }
}
