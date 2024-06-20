package com.app.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.dao.UserDao;

public class Account {

    private int id;
    private int idUser;
    private double balance;
    private String account_number;
    private Date created;
    private List<Transaction> transaction = new ArrayList<Transaction>();

    public Account() {
    }

    public Account(int id, int idUser, double balance, String account_number, Date created, List<Transaction> transaction) {
        this.id = id;
        this.idUser = idUser; 
        this.balance = balance;
        this.account_number = account_number;
        this.created = created;
        this.transaction = transaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String createdFormatted = (created != null) ? sdf.format(created) : "null";
        UserDao userDao = new UserDao();
        User user = userDao.selectById(idUser);
        return "Account [account_number=" + account_number + ", balance=" + balance + ", created="
                + createdFormatted + ", user=" + user.getUsername() + "]";
    }
}
