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

    public Socket openConnection() {
        serverHost = Usage.serverHost;
        serverPort = Usage.port;
        try {

            clientSocket = new Socket(serverHost, serverPort);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream()); 
            return clientSocket;
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void sendData(Message mesSend) {
        CheckMess checkMess = new CheckMess(clientSocket, ois);
            checkMess.start();
        try {
            oos.writeObject(mesSend);
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Message receiveData() {
        try {
            Object o = ois.readObject();
            if (o instanceof Message) {
                Message mesReceive = (Message) o;
                return mesReceive;
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

class CheckMess extends Thread {

    private Socket socketNhanInvite;
    ObjectInputStream ois;

    public CheckMess(Socket socketNhanInvite, ObjectInputStream ois) {
        this.socketNhanInvite = socketNhanInvite;
        this.ois = ois;
    }

    @Override
    public void run() {
        Message message;
        while (true) {
            try {
                message = (Message) ois.readObject();
                if (message.getMesType() == Message.MesType.INVITE_USER) {
                    InviteRequest inviteRequest = new InviteRequest();
                    inviteRequest.setVisible(true);
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
