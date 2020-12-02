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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Choice;
import model.Game;
import model.User;
import utils.Usage;

/**
 *
 * @author lamit
 */
public class ServerDao{

    private Connection conn;
    public ServerDao() {
        conn = ConnectDatabase.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User checkLogin(Account acc) {
        User user = new User();
        try {
            PreparedStatement pre = conn.prepareStatement(Usage.findAccount);
            pre.setString(1, acc.getUsername());
            pre.setString(2, acc.getPassword());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                PreparedStatement pre1 = conn.prepareStatement(Usage.updateStatus);
                pre1.setBoolean(1, true);
                pre1.setInt(2, id);
                pre1.executeUpdate();
                user.setAccount(acc);
                user.setStatus(true);
                return user;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return null;
    }

    public boolean register(Account acc) {
        boolean isSuccess = false;
        try {
            PreparedStatement preProcess = conn.prepareStatement(Usage.findAccount);
            preProcess.setString(1, acc.getUsername());
            preProcess.setString(2, acc.getPassword());
            ResultSet rs = preProcess.executeQuery();
            if (!rs.next()) {
                PreparedStatement pre = conn.prepareStatement(Usage.insertAccount, Statement.RETURN_GENERATED_KEYS);
                pre.setString(1, acc.getUsername());
                pre.setString(2, acc.getPassword());
                pre.executeUpdate();
                //get id insert last
                ResultSet rs1 = pre.getGeneratedKeys();
                int lastRowId = 0;
                if(rs1.next()){
                    lastRowId = rs1.getInt(1);
                }
                PreparedStatement pre1 = conn.prepareStatement(Usage.insertUser);
                User user = new User(0, false);
                pre1.setInt(1, lastRowId);
                pre1.setInt(2, 0);
                pre1.setBoolean(3, true);
                pre1.executeUpdate();
                isSuccess = true;
                conn.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return isSuccess;
    }

    public Game insertGame() {
        Game game = new Game();
        try {
            PreparedStatement pre = conn.prepareStatement(Usage.insertGame, Statement.RETURN_GENERATED_KEYS);
            Date date = new Date();
            pre.setObject(1, date.toInstant().atZone(ZoneId.of("Vietnam")).toLocalDate());
            //get id has just inserted 
            pre.executeUpdate();
            ResultSet resultGetGenerateKey = pre.getGeneratedKeys();
            int idGame = 0;
            if(resultGetGenerateKey.next()){
                idGame = resultGetGenerateKey.getInt(1);
            }
            game.setTimeCreated(date);
            game.setId(idGame);
            conn.commit();
            return game;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return game;
    }

    public void insertChoice(Choice choice) {
        try {
            PreparedStatement pre = conn.prepareStatement(Usage.insertChoice);
            pre.setInt(1, choice.getUser().getId());
            pre.setInt(2, choice.getGame().getId());
            int choiceInt = 0;
            /**
             * BUA = 1
             * KEO = 2
             * BAO = 3
             */
            switch(choice.getChoice()){
                case BUA:{
                    choiceInt = 1;
                    break;
                }
                case KEO:{
                    choiceInt = 2;
                    break;
                }
                case  BAO:{
                    choiceInt = 3;
                    break;
                }
            }
            pre.setInt(3, choiceInt);
            pre.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> listUser = new ArrayList<>();
        try {
            PreparedStatement pre = conn.prepareStatement(Usage.getAllUser);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                //u._point,u._status,a._username
                int point = rs.getInt("u._point");
                boolean status = rs.getBoolean("u._status");
                String username = rs.getString("a._username");
                User user = new User(new Account(username), point, status);
                listUser.add(user);
            }
            Collections.sort(listUser,new CompareUser());
            int rank = 1;
            for(User user : listUser){
                user.setRank(rank++);
            }
            conn.commit();
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return listUser;
    }
    
    public ArrayList<User> getUsers(){
        ArrayList<User> listUser = new ArrayList<>();
        try {
            PreparedStatement pre = conn.prepareStatement(Usage.getAllUser);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                //u._point,u._status,a._username
                int point = rs.getInt("u._point");
                boolean status = rs.getBoolean("u._status");
                String username = rs.getString("a._username");
                User user = new User(new Account(username), point, status);
                listUser.add(user);
            }
            Collections.sort(listUser,new CompareUser());
            int rank = 1;
            for(User user : listUser){
                user.setRank(rank++);
            }
            conn.commit();
            return listUser;
        } catch (SQLException ex) {
            Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(ServerDao.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return listUser;
    }
    
    public void invite(){
        
    }
    
//    public User getUser(){
//        User u 
//        return user;
//    }
//    
    class CompareUser implements Comparator<User> {

        @Override
        public int compare(User t, User t1) {
            return t.getPoint() > t1.getPoint() ? 1 : -1;
        }

    }
}