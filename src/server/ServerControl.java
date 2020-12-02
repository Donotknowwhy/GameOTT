/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Admin
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Message;
import model.User;

/**
 *
 * @author lamit
 */
public class ServerControl implements Runnable {

    private String serverHost;
    private int serverPort;
    private Socket clientSocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ServerDao serverDao;
    private Map<User,Socket> mapSocket = new HashMap<>();
    public ServerControl(Socket socket) {
        this.serverDao = new ServerDao();
        this.clientSocket = socket;
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("0");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("1");
                Object o = ois.readObject();
                if (o instanceof Message) {
                    Message mesReceive = (Message) o;
                    checkMesType(mesReceive);
                }
            }
            Thread.sleep(100);
            clientSocket.close();
        } catch (Exception ex) {
            try {
                ois.close();
                oos.close();
                clientSocket.close();
            } catch (IOException ex1) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private void checkMesType(Message mesReceive) {
        switch (mesReceive.getMesType()) {
            case LOGIN: {
                Account acc = (Account) mesReceive.getObject();
                System.out.println(acc);
                User user = serverDao.checkLogin((Account) mesReceive.getObject());
                if (user == null) {
                    try {
                        User user1 = new User();
                        oos.writeObject(new Message(user1, Message.MesType.LOGIN_FAIL));
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        oos.writeObject(new Message(user, Message.MesType.LOGIN_SUCCESS));
                        mapSocket.put(user, clientSocket);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            case REGISTER: {
                boolean isRegisterSuccess = serverDao.register((Account) mesReceive.getObject());
                if (isRegisterSuccess == false) {
                    try {
                        oos.writeObject(new Message(isRegisterSuccess, Message.MesType.REGISTER_FAIL));
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        oos.writeObject(new Message(isRegisterSuccess, Message.MesType.REGISTER_SUCCESS));
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            case GET_SCOREBOARD:{
                ArrayList<User> us = serverDao.getUsers();
                if(us != null){
                    try {
                        oos.writeObject(new Message(us, Message.MesType.lIST_FULL));
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        oos.writeObject(new Message(us, Message.MesType.LIST_NULL));
                    } catch (IOException ex) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            }
            
            case INVITE_USER:{
                ArrayList<User> users2 = (ArrayList<User>) mesReceive.getObject();
                User userMoi = users2.get(0);
                User userNhan = users2.get(1);
                ArrayList<User> users = new ArrayList<User>(mapSocket.keySet());
                for(int i= 0;i<users.size();i++){
                    if(userNhan.equalsUser(users.get(i))){
                        Socket cliSocket = mapSocket.get(users.get(i));
                        ObjectOutputStream oos1;
                        try {
                            oos1 = new ObjectOutputStream(cliSocket.getOutputStream());                           
                            oos1.writeObject(new Message(userNhan,Message.MesType.INVITE_USER));
                            
                        } catch (IOException ex) {
                            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
                        }                       
                    }
                }
                break;
            }
            
            default: break;
        }
    }
    
    class inviteThread extends Thread{

        @Override
        public void run() {
            
        }
        
    }
    
    private Message createMessage() {
        Message mes = new Message();
        return mes;
    }

}