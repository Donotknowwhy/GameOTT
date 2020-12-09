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
import model.Game;
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
                        DataServer.mapSocket.put(user, oos);
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
            case GET_SCOREBOARD: {
                ArrayList<User> us = serverDao.getUsers();
                if (us != null) {
                    try {
                        oos.writeObject(new Message(us, Message.MesType.LIST_FULL));
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

            case INVITE_USER: {
                System.out.println("moi:");
                ArrayList<User> users2 = (ArrayList<User>) mesReceive.getObject();

                User userMoi = users2.get(0);
                User userNhan = users2.get(1);
                System.out.println("u1: " + userMoi.toString());
                System.out.println("u nhan: " + userNhan.toString());
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
//                System.out.println("size"+users.size());
//                System.out.println(usersMapSocket.size());
                for (int i = 0; i < users.size(); i++) {
                    if (userNhan.getAccount().getUsername().equals(users.get(i).getAccount().getUsername())) {
//                        System.out.println(users.get(i).toString());
                        System.out.println("equal");
                        Message mesSent =new  Message(users2, Message.MesType.INVITE_USER);
                        DataServer.sendMessage(users.get(i), mesSent);
                       
                        break;
                    }
                }
                break;
            }
            case ACCEPT_REQUEST: {
                ArrayList<User> usersPlayGame = (ArrayList<User>) mesReceive.getObject();
                User userMoi = usersPlayGame.get(0);
                System.out.println(userMoi.toString());
                User userNhan = usersPlayGame.get(1);
                System.out.println(userNhan.toString());
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
//                System.out.println(users);
                System.out.println("users" + users.size());
//                Game game = serverDao.insertGame();
                Game game = new Game();
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(users.get(i));
                    if (userNhan.getAccount().getUsername().equals(users.get(i).getAccount().getUsername())) {
                            System.out.println("aaa");
                            DataServer.sendMessage(users.get(i), new Message(game, Message.MesType.START_GAME));
//                            oos.writeObject(new Message(game, Message.MesType.START_GAME));
                      
                    } else if (userMoi.getAccount().getUsername().equals(users.get(i).getAccount().getUsername())) {
                        System.out.println("bbb");    
                        DataServer.sendMessage(users.get(i), new Message(game, Message.MesType.START_GAME));
                    }
                }
                break;
            }
            case DENY_REQUEST: {
                break;
            }

            default:
                break;
        }
    }
}

class ThreadSent implements Runnable{
    private Socket cliSocket;
    private ObjectOutputStream oos1;
    Message message;
    public ThreadSent(Socket cliSocket, Message m) {       
        this.cliSocket = cliSocket;
        this.message = m;
        System.out.println("---");
        try {
            System.out.println(cliSocket);
            
            oos1 = new ObjectOutputStream(this.cliSocket.getOutputStream());
            System.out.println("///");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            oos1.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ThreadSent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}