package com.app.controllers;

import java.util.List;
import java.util.Scanner;

import com.app.dao.AccountDao;
import com.app.dao.TransactionDao;
import com.app.enums.Type;
import com.app.models.Account;
import com.app.models.Transaction;
import com.app.models.User;
import com.app.utils.UtilsValidation;

/**
 * ControllerTransactions handles operations related to depositing, withdrawing, and transferring funds between accounts.
 * 
 * @author Adrian
 */
public class ControllerTransactions {
    
    Scanner lector = new Scanner(System.in);
    AccountDao accountDao = new AccountDao();
    TransactionDao transactionDao = new TransactionDao();
    /**
     * Deposits an amount into the specified user account after validating the account number
     *
     * @param  user  the user for whom the deposit is being made
     */
    public void deposit(User user) {
        System.out.println("\n-> DEPOSIT");
        System.out.println("Select the account number to make the deposit: ");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        String number;
        boolean validate = false;
        int idAccount = 0;
        System.out.println("");
        do {
            System.out.print("Account number: ");
            number = lector.nextLine();
            if (UtilsValidation.validateAccountNumber(number)) {
                for (Account account : accounts) {
                    if (account.getAccount_number().equals(number)) {
                        idAccount = account.getId();
                        validate = true;
                        break;
                    }
                } 
                if (validate == false) {
                    System.out.println("Invalid account number. Try again");
                }
            } else {
                System.out.println("Invalid account number. Try again");
            }
        } while (!validate);
        Account account = accountDao.selectById(idAccount);
        System.out.print("Amount: "); 
        double amount = lector.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount. Try again");
            return;
        }
        lector.nextLine();
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction();
        transaction.setType(Type.deposit);
        transaction.setAmount(amount);
        transaction.setOrigin_account(account.getAccount_number());
        accountDao.update(account);
        transactionDao.insertReturnId(transaction);
        System.out.println("Deposit successful");
    }
    
    /**
     * Transfers an amount from the origin account to the target account after validation.
     *
     * @param  user  the user initiating the transfer
     */
    public void transfer(User user) {
        System.out.println("\n-> TRANSFER");
        System.out.println("Select the origin account to make the transfer: ");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        String number;
        boolean validate = false;
        int idAccount = 0;
        System.out.println("");
        do {
            System.out.print("Origin account number: ");
            number = lector.nextLine();
            if (UtilsValidation.validateAccountNumber(number)) {
                for (Account account : accounts) {
                    if (account.getAccount_number().equals(number)) {
                        idAccount = account.getId();
                        validate = true;
                        break;
                    }
                }
                if (validate == false) {
                    System.out.println("Invalid account number. Try again");
                }
            } else {
                System.out.println("Invalid account number. Try again");
            }
        } while (!validate);
        Account account = accountDao.selectById(idAccount);
        double amount;
        do {
            System.out.print("Amount: ");
            amount = lector.nextDouble();
            lector.nextLine();
            if (amount > account.getBalance()) {
                System.out.println("Insufficient balance. Try again");
            }
        } while (amount > account.getBalance());
        Transaction transaction = new Transaction();
        transaction.setType(Type.transfer);
        transaction.setAmount(amount);
        transaction.setOrigin_account(account.getAccount_number());
        account.setBalance(account.getBalance() - amount);
        String targetAccountNumber;
        do {
            System.out.print("Select the target account number: ");
            targetAccountNumber = lector.nextLine();
            if (accountDao.checkAccount(targetAccountNumber) == 0) {
                System.out.println("Invalid account number. Try again");
            }
            if (account.getAccount_number().equals(targetAccountNumber)) {
                System.out.println("You cannot make a transfer to the same account");
            }
        } while (accountDao.checkAccount(targetAccountNumber) == 0 || account.getAccount_number().equals(targetAccountNumber));
        int idTargetAccount = accountDao.checkAccount(targetAccountNumber);
        Account targetAccount = accountDao.selectById(idTargetAccount);
        transaction.setTarget_account(targetAccount.getAccount_number());
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        accountDao.update(account);
        transactionDao.insertReturnId(transaction);
        accountDao.update(targetAccount);
        System.out.println("\nTransfer successful");
    }
    
    /**
     * Performs a withdrawal from the user's account.
     *
     * @param  user  the user initiating the withdrawal
     */
    public void withdrawal(User user) {
        System.out.println("\n-> WITHDRAWAL");
        System.out.println("Select the account number to make the withdrawal: ");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        String number;
        boolean validate = false;
        int idAccount = 0;
        System.out.println("");
        do {
            System.out.print("Account number: ");
            number = lector.nextLine();
            if (UtilsValidation.validateAccountNumber(number)) {
                for (Account account : accounts) {
                    if (account.getAccount_number().equals(number)) {
                        if (account.getBalance() == 0) {
                            System.out.println("Account empty. Try again");
                            break;
                        } else{
                            idAccount = account.getId();
                            validate = true;
                            break;
                        }
                    }
                } 
            } else {
                System.out.println("Invalid account number. Try again");
            }
        } while (!validate);
        Account account = accountDao.selectById(idAccount);
        double withdrawal;
        do {
            System.out.print("Withdrawal: ");
            withdrawal = lector.nextDouble();
            if (withdrawal > account.getBalance()) {
                System.out.println("Insufficient balance. Try again");
            }
        } while (withdrawal > account.getBalance());
        lector.nextLine();
        account.setBalance(account.getBalance() - withdrawal);
        Transaction transaction = new Transaction();
        transaction.setType(Type.withdrawal);
        transaction.setAmount(withdrawal);
        transaction.setOrigin_account(account.getAccount_number());
        accountDao.update(account);
        transactionDao.insertReturnId(transaction);
        System.out.println("\nWithdrawal successful");
    }
}
