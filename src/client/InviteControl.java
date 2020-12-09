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
        listFrm.setActionM(new ButtonLogout(),new ButtonInvite());
    }

    public void setUser(User user) {
        this.userRecent = user;
    }
    class ButtonLogout implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            System.out.println("click");
            User user = DataClient.getUserCurrent();
            user.setStatus(0);
            DataClient.setUserCurrent(user);
            System.out.println(user.toString());
            Message message = new Message(user, Message.MesType.CHANGE_USER_STATUS);
            clientControl.sendData(message);
            listFrm._dispose();
            DataClient.userCurrent = new User();
            System.out.println("aaa");
        }
        
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
            if (u.isStatus() != -1) {
                Message message = new Message(users1, Message.MesType.INVITE_USER);
                clientControl.sendData(message);
            } else {
                listFrm.thongBao("tai khoan nay khong online!");
            }
        }
    }

    public void showListUser(ArrayList<User> listUsers) {
        user = listUsers;
        listFrm.showUsers(user);
    }

    public void showInviteRequest(Message message) {
        user = (ArrayList<User>) message.getObject();
        InviteRequest inviteRequest = new InviteRequest();
        inviteRequest.setVisible(true);
        InviteRequestControl inviteRequestControl
                  = new InviteRequestControl(inviteRequest, clientControl, user);
    }

    public void showGameConsole(Message mesRecei) {
        GameFrm gameFrm = new GameFrm();
        gameFrm.setVisible(true);
        Game game = (Game) mesRecei.getObject();
        GameControl gameControl = new GameControl(gameFrm, clientControl, game);
    }
}
