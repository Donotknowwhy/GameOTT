/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Admin
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import model.Account;
import model.Message;
import model.User;
import ui.Invite;
import ui.ListFrm;
import ui.LoginFrm;
import ui.Rank;
import ui.RegisterFrm;

/**
 *
 * @author lamit
 */
public class LoginControl {
    private LoginFrm loginFrm;
    private ClientControl clientControl;
    private ListFrm listFrm;
    public LoginControl(LoginFrm loginFrm,ClientControl clientControl){
        this.clientControl = clientControl;
        this.loginFrm = loginFrm;
        this.loginFrm.setVisible(true);
        this.loginFrm.setAction(new ButtonListener(), new ButtonRegister());
    }
    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginFrm.getUsername();
            String password = loginFrm.getPassword();
            System.out.println(password);
            Account account = new Account(username, password);
            Message mesSend = new Message(account, Message.MesType.LOGIN);
            clientControl.sendData(mesSend);
            Message mesRecei = clientControl.receiveData();
            if(mesRecei.getMesType() == Message.MesType.LOGIN_FAIL){
                loginFrm.showMessage("Login Fail");
            }else if(mesRecei.getMesType() == Message.MesType.LOGIN_SUCCESS){
                loginFrm.showMessage("Login Success");
                Message mesReq = new Message(account, Message.MesType.GET_SCOREBOARD);
                clientControl.sendData(mesReq);
                listFrm = new ListFrm();
                InviteControl inviteControl = new InviteControl(clientControl, listFrm);
                inviteControl.setUser((User) mesRecei.getObject());
                listFrm.setVisible(true);
                CheckMess checkMess = new CheckMess(clientControl.getClientSocket(),clientControl );
                checkMess.start();
            }
        }       
    }
    class ButtonRegister implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterFrm registerFrm = new RegisterFrm();
            registerFrm.setVisible(true);
            RegisterControl registerControl = new RegisterControl(registerFrm, clientControl);
        }
        
    }
}
