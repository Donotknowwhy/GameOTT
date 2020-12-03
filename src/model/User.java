/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author lamit
 */
public class User implements Serializable, Comparable<User>{
    private static final long serialVersionUID = 6529685098267757694L;
    private int id;
    private Account account;
    private int point;
    private boolean status;
    //another property
    private int rank;
    public User() {
    }
    
    public User(Account account, int point, boolean status) {
        this.account = account;
        this.point = point;
        this.status = status;
    }
    
    public User(int id, Account account, int point, boolean status) {
        this.id = id;
        this.account = account;
        this.point = point;
        this.status = status;
    }
    
    public User(int id, int point, boolean status) {
        this.id = id;
        this.point = point;
        this.status = status;
    }
    

    public User(int point, boolean status) {
        this.point = point;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public Object[] toObjects(){
        return new Object[]{rank,account.getUsername(),point,status};
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", account=" + account + ", point=" + point + ", status=" + status + ", rank=" + rank + '}';
    }
    
    
    
    @Override
    public int compareTo(User o) {
        if (point == o.point)
            return 0;
        else if (point > o.point)
            return 1;
	else
            return -1;
    }


    public boolean equalsUser(User obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.point != other.point) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.rank != other.rank) {
            return false;
        }
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        return true;
    }
    
}
