package hazel.poc;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * User: greg
 */
public class User implements Serializable {

    private long id;
    private String name;
    private int balance;
    private Role role;
    private Date ttl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getTtl() {
        return ttl;
    }

    public void setTtl(Date ttl) {
        this.ttl = ttl;
    }
}
