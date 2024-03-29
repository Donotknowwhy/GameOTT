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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Choice;
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
            while (!Thread.currentThread().isInterrupted()) {
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
                        ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                        ArrayList<User> us = serverDao.getUsers();
                        for (int i = 0; i < users.size(); i++) {
                            
                            DataServer.sendMessage(users.get(i), new Message(us, Message.MesType.LIST_FULL));
                        }
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
                        ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                        ArrayList<User> us = serverDao.getUsers();
                        for (int i = 0; i < users.size(); i++) {
                            
                            DataServer.sendMessage(users.get(i), new Message(us, Message.MesType.LIST_FULL));
                        }
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
                ArrayList<User> users2 = (ArrayList<User>) mesReceive.getObject();

                User userMoi = users2.get(0);
                User userNhan = users2.get(1);
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                for (int i = 0; i < users.size(); i++) {
                    if (userNhan.getAccount().getUsername().equals(users.get(i).getAccount().getUsername())) {
                        Message mesSent = new Message(users2, Message.MesType.INVITE_USER);
                        DataServer.sendMessage(users.get(i), mesSent);

                        break;
                    }
                }
                break;
            }
            case ACCEPT_REQUEST: {
                ArrayList<User> usersPlayGame = (ArrayList<User>) mesReceive.getObject();
                User userMoi = usersPlayGame.get(0);
                User userNhan = usersPlayGame.get(1);
                userMoi.setStatus(2);
                userNhan.setStatus(2);
                serverDao.updateUserStatus(userMoi);
                serverDao.updateUserStatus(userNhan);
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                ArrayList<User> us = serverDao.getUsers();
                for (int i = 0; i < users.size(); i++) {
                    
                    DataServer.sendMessage(users.get(i), new Message(us, Message.MesType.LIST_FULL));
                }
                System.out.println("users" + users.size());
                Game game = serverDao.insertGame();
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(users.get(i));
                    if (userNhan.getId() == users.get(i).getId()) {
                        DataServer.sendMessage(users.get(i), new Message(game, Message.MesType.START_GAME));

                    } else if (userMoi.getId() == users.get(i).getId()) {
                        DataServer.sendMessage(users.get(i), new Message(game, Message.MesType.START_GAME));
                    }
                }
                break;
            }
            case DENY_REQUEST: {
                ArrayList<User> usersPlayGame = (ArrayList<User>) mesReceive.getObject();
                User userMoi = usersPlayGame.get(0);
                User userNhan = usersPlayGame.get(1);
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                for (int i = 0; i < users.size(); i++) {
                    System.out.println(users.get(i));
                    if (userMoi.getId() == users.get(i).getId()) {
                        DataServer.sendMessage(users.get(i), new Message(users, Message.MesType.DO_NOT_PLAY));
                    }
                }
                break;
            }
            case SEND_CHOICE: {
                Choice choice = (Choice) mesReceive.getObject();
                System.out.println("choice" + choice.getUser().getId());
                Game game = choice.getGame();
                serverDao.insertChoice(choice);
                ArrayList<Choice> listChoice = serverDao.getChoiceByIdGame(game.getId());
                Choice choice1 = new Choice();
                Choice choice2 = new Choice();
                if (listChoice.size() == 2) {
                    choice1 = listChoice.get(0);
                    choice2 = listChoice.get(1);
                    if (choice1.compareTo(choice2) > 0) {
                        choice1.setResult(2);
                        choice2.setResult(0);
                    } else if (choice1.compareTo(choice2) == 0) {
                        choice1.setResult(1);
                        choice2.setResult(1);
                    } else {
                        choice1.setResult(0);
                        choice2.setResult(2);
                    }
                    choice1.getUser().setStatus(1);
                    choice2.getUser().setStatus(1);
                    serverDao.updateUserStatus(choice1.getUser());
                    serverDao.updateUserPoint(choice1);
                    serverDao.updateUserPoint(choice2);
                    ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                    ArrayList<User> us = serverDao.getUsers();
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getId() == choice1.getUser().getId()) {
                            DataServer.sendMessage(users.get(i), new Message(choice1, us, Message.MesType.REPLY_RESULT));
                        }
                        if (users.get(i).getId() == choice2.getUser().getId()) {
                            DataServer.sendMessage(users.get(i), new Message(choice2, us, Message.MesType.REPLY_RESULT));

                        }
                    }
                }
                break;
            }
            case CHANGE_USER_STATUS: {
                User u = (User) mesReceive.getObject();
                serverDao.updateUserStatus(u);
                System.out.println(u.toString());
                ArrayList<User> users = new ArrayList<User>(DataServer.mapSocket.keySet());
                ArrayList<User> us = serverDao.getUsers();
                for (int i = 0; i < users.size(); i++) {
                    DataServer.sendMessage(users.get(i), new Message(us, Message.MesType.LIST_FULL));
                }
                break;
            }
            default:
                break;
        }
    }

}