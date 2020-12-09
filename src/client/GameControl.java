/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Game;
import ui.GameFrm;

/**
 *
 * @author lamit
 */
public class GameControl {
    private ClientControl clientControl;
    private GameFrm gameFrm;
    private Game game;
    public GameControl(GameFrm gameFrm,ClientControl clientControl, Game game) {
        this.clientControl = clientControl;
        this.gameFrm = gameFrm;
        this.gameFrm.setVisible(true);
        this.game = game;
    }
    class ButtonSubmit implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            int choice = gameFrm.getChoice();
            
        }
        
    }
    
}
