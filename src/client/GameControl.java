/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Choice;
import model.Game;
import model.Message;
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
        this.clientControl.setGameControl(this);
        this.gameFrm = gameFrm;
        this.gameFrm.setActionM(new ButtonSubmit());
        this.game = game;
        
    }
    class ButtonSubmit implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Choice.ChoiceType choiceType =  Choice.ChoiceType.KEO;
            
            int cho = gameFrm.getChoice();
            switch(cho){
                case 1:{
                    choiceType = Choice.ChoiceType.KEO;
                    break;
                }
                case 2:{
                    choiceType = Choice.ChoiceType.BUA;
                    break;
                }
                case 3:{
                    choiceType = Choice.ChoiceType.BAO;
                    break;
                }
            }
            Choice choice = new Choice(DataClient.userCurrent, game, choiceType, 0);
            Message mesSend = new Message(choice, Message.MesType.SEND_CHOICE);
            clientControl.sendData(mesSend);
        }
       
    }
     public void checkResult(Choice choice){
        switch (choice.getResult()) {
            case 1:
                gameFrm.showMessage("Draw");
                break;
            case 2:
                gameFrm.showMessage("Win");
                break;
            default:
                gameFrm.showMessage("Lose");
                break;
        }
        }
}
