package com.easywork.mystery.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Relation implements Serializable {
    @Id
    private Integer id;

    private String account;

    @Column(name = "friend_account")
    private String friendAccount;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return friend_account
     */
    public String getFriendAccount() {
        return friendAccount;
    }

    /**
     * @param friendAccount
     */
    public void setFriendAccount(String friendAccount) {
        this.friendAccount = friendAccount;
    }
}