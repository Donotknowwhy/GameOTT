
import client.ClientControl;
import client.LoginControl;
import client.RegisterControl;
import ui.LoginFrm;
import ui.RegisterFrm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lamit
 */
public class ClientRun {
    public static void main(String[] args) {
        LoginFrm loginFrm = new LoginFrm();
        loginFrm.setVisible(true);
        ClientControl clientControl = new ClientControl();
        System.out.println(System.currentTimeMillis());
        clientControl.openConnection();
        System.out.println(System.currentTimeMillis());
        clientControl.startThreadRecei();
        LoginControl loginControl
                 = new LoginControl(loginFrm,clientControl);
        
    }
}
