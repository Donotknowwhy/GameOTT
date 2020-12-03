
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
//        RegisterFrm registerFrm = new RegisterFrm();
        ClientControl clientControl = new ClientControl();
        clientControl.openConnection();
        LoginControl loginControl
                 = new LoginControl(loginFrm,clientControl);
//        RegisterControl registerControl = new RegisterControl(registerFrm, clientControl);
        
    }
}
