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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import model.Message;
import ui.InviteRequest;
import ui.LoginFrm;
import utils.Usage;

/**
 *
 * @author lamit
 */
public class ClientControl {
    private LoginControl loginControl;
    private RegisterControl registerControl;
    private InviteControl inviteControl;
    private Socket clientSocket;
    private String serverHost;
    private int serverPort;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    private static ClientControl instance = null;

    public static ClientControl getInstance() {
        if (instance == null) {
            instance = new ClientControl();
        }
        return instance;
    }
    
    public ClientControl() {
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
    
    public Socket openConnection() {
        serverHost = Usage.serverHost;
        System.out.println("open connection");
        serverPort = Usage.port;
        try {

            clientSocket = new Socket(serverHost, serverPort);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println(ois);
            return clientSocket;
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
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

//    public Message receiveData() {
//        try {
//            Object o = ois.readObject();
//            if (o instanceof Message) {
//                Message mesReceive = (Message) o;
//                return mesReceive;
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    Thread threadReceive = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Object o = ois.readObject();
                    Message message = (Message) o;
                    switch(message.getMesType()){
                        case  LOGIN_FAIL:{
                            loginControl.showMessageFail();
                            break;
                        }
                        case LOGIN_SUCCESS:{
                            loginControl.showMessageSuccess(message);
                            break;
                        }
                        case REGISTER_FAIL:{
                            registerControl.showMessageFail();
                            break;
                        }
                        case REGISTER_SUCCESS:{
                            registerControl.showMessageSuccess();
                            break;
                        }
                        case LIST_FULL:{
                            inviteControl.showListUser(message);
                            break;
                        }
                        default: break;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
}

class CheckMess extends Thread {

    Message mesRecei;
    private Socket socketNhanInvite;
    ObjectInputStream ois;

    public CheckMess(Socket socketNhanInvite, ObjectInputStream ois) {
        this.socketNhanInvite = socketNhanInvite;
        try {
            this.ois = new ObjectInputStream(socketNhanInvite.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(CheckMess.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.ois);
    }

    public Message getMesRecei() {
        return mesRecei;
    }

    public void setMesRecei(Message mesRecei) {
        this.mesRecei = mesRecei;
    }

    @Override
    public void run() {
        Message message;

        while (true) {
            try {
                Object o = ois.readObject();
                if (o instanceof Message) {
                    message = (Message) o;
                    setMesRecei(message);
                    if (message.getMesType() == Message.MesType.INVITE_USER) {
                        InviteRequest inviteRequest = new InviteRequest();
                        inviteRequest.setVisible(true);
                    } else {

                    }
                }
                Thread.sleep(1000);
            } catch (IOException ex) {
                Logger.getLogger(CheckMess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CheckMess.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(CheckMess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
