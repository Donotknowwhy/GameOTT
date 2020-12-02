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
import model.Account;
import model.Message;
import ui.RegisterFrm;

/**
 *
 * @author BENH VIEN CONG NGHE
 */
public class RegisterControl {
    private RegisterFrm registerFrm;
    private ClientControl clientControl;
    public RegisterControl(RegisterFrm registerFrm, ClientControl clientControl){
        this.clientControl = clientControl;
        this.registerFrm = registerFrm;
        this.registerFrm.setVisible(true);
        this.registerFrm.setAction(new ButtonListener());
    }
    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = registerFrm.getUsername();
            String password = registerFrm.getPassword();
            Account account = new Account(username, password);
            Message mesSend = new Message(account, Message.MesType.REGISTER);
            clientControl.sendData(mesSend);
            Message mesRecei = clientControl.receiveData();
            if(mesRecei.getMesType() == Message.MesType.REGISTER_FAIL){
                registerFrm.showMessage("Register Fail");
            }else if(mesRecei.getMesType() == Message.MesType.REGISTER_SUCCESS){
                registerFrm.showMessage("Register Success");
            }
        }
        
    }
}