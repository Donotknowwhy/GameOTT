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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Choice;
import model.Message;
import model.User;
import utils.Usage;

/**
 *
 * @author lamit
 */
public class ClientControl {

    private LoginControl loginControl;
    private RegisterControl registerControl;
    private InviteControl inviteControl;
    private InviteRequestControl inviteRequestControl;
    private GameControl gameControl;
    private Socket clientSocket = new Socket();
    private String serverHost;
    private int serverPort;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ObjectInputStream ois1;
    private static ClientControl instance = null;

    public static ClientControl getInstance() {
        if (instance == null) {
            instance = new ClientControl();
        }
        return instance;
    }

    public ClientControl() {
        this.threadReceive = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Object o = ois.readObject();
                        Message message = (Message) o;
                        switch (message.getMesType()) {
                            case LOGIN_FAIL: {
                                loginControl.showMessageFail();
                                break;
                            }
                            case LOGIN_SUCCESS: {
                                loginControl.showMessageSuccess(message);
                                User userCurrent = (User) message.getObject();
                                DataClient.setUserCurrent(userCurrent);
                                break;
                            }
                            case REGISTER_FAIL: {
                                registerControl.showMessageFail();
                                break;
                            }
                            case REGISTER_SUCCESS: {
                                registerControl.showMessageSuccess();
                                User userCurrent = (User) message.getObject();
                                DataClient.setUserCurrent(userCurrent);
                                break;
                            }
                            case LIST_FULL: {
                                inviteControl.showListUser((ArrayList<User>) message.getObject());
                                break;
                            }
                            case INVITE_USER: {
                                inviteControl.showInviteRequest(message);
                                break;
                            }
                            case DO_NOT_PLAY:{
                                inviteControl.showMessageRejectGame();
                                break;
                            }
                            case START_GAME:{
                                inviteControl.showGameConsole(message);
                                break;
                            }
                            case REPLY_RESULT:{
                                Choice choice = (Choice) message.getObject();
                                ArrayList<User> listUs = (ArrayList<User>) message.getObject2();
                                inviteControl.showListUser(listUs);
                                gameControl.checkResult(choice);
                                break;
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                    Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public LoginControl getLoginControl() {
        return loginControl;
    }

    public void setLoginControl(LoginControl loginControl) {
        this.loginControl = loginControl;
    }

    public RegisterControl getRegisterControl() {
        return registerControl;
    }

    public void setRegisterControl(RegisterControl registerControl) {
        this.registerControl = registerControl;
    }

    public InviteControl getInviteControl() {
        return inviteControl;
    }

    public void setInviteControl(InviteControl inviteControl) {
        this.inviteControl = inviteControl;
    }

    public InviteRequestControl getInviteRequestControl() {
        return inviteRequestControl;
    }

    public void setInviteRequestControl(InviteRequestControl inviteRequestControl) {
        this.inviteRequestControl = inviteRequestControl;
    }

    public GameControl getGameControl() {
        return gameControl;
    }

    public void setGameControl(GameControl gameControl) {
        this.gameControl = gameControl;
    }
    

    public Socket openConnection() {
        serverHost = Usage.serverHost;
        System.out.println("open connection");
        serverPort = Usage.port;
        try {
            clientSocket = new Socket(serverHost, serverPort);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void sendData(Message mesSend) {
        try {
            
            oos.writeObject(mesSend);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    Thread threadReceive;

    public void startThreadRecei() {
        threadReceive.start();
    }
}