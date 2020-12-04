/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.InviteRequest;

/**
 *
 * @author lamit
 */
public class InviteRequestControl {
    private InviteRequest inviteRequest;
    private ClientControl clientControl;

    public InviteRequestControl(InviteRequest inviteRequest, ClientControl clientControl) {
        this.inviteRequest = inviteRequest;
        this.clientControl = clientControl;
        this.inviteRequest.setAction(new ButtonAccept(), new ButtonReject());
    }
    class ButtonAccept implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
        }
        
    }
    class ButtonReject implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
        }
        
    }
}
