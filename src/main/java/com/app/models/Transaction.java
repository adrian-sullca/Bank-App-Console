package com.app.models;

import java.text.DecimalFormat;

import com.app.enums.Type;

public class Transaction {
    private int id;
    private String origin_account;
    private String target_account;
    private double amount;
    private Type type;
    
    public Transaction() {
    }

    public Transaction(int id, String origin_account, String target_account, double amount, Type type) {
        this.id = id;
        this.origin_account = origin_account;
        this.target_account = target_account;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin_account() {
        return origin_account;
    }

    public void setOrigin_account(String origin_account) {
        this.origin_account = origin_account;
    }

    public String getTarget_account() {
        return target_account;
    }

    public void setTarget_account(String target_account) {
        this.target_account = target_account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", origin_account=" + origin_account + ", target_account=" + target_account
                + ", amount=" + amount + ", type=" + type + "]";
    }

    public String toStringFormatted(String numberOriginAccount) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedAmount = "";
        if (type == Type.deposit) {
            formattedAmount = "+" + df.format(amount);
        } else if (type == Type.withdrawal) {
            formattedAmount = "-" + df.format(amount);
        } else if (type == Type.transfer) {
            if (numberOriginAccount.equals(origin_account)) {
                formattedAmount = "-" + df.format(amount);
            } else {
                formattedAmount = "+" + df.format(amount);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Transaction [id=").append(id)
          .append(", type=").append(type)
          .append(", amount=").append(formattedAmount)
          .append(", origin_account=").append(origin_account);

          if (type == Type.transfer) {
            sb.append(", target_account=").append(target_account);
        }

        sb.append("]");
        return sb.toString();
    }
}