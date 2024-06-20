package com.app.controllers;

import java.util.List;
import java.util.Scanner;

import com.app.dao.AccountDao;
import com.app.dao.TransactionDao;
import com.app.models.Account;
import com.app.models.Transaction;
import com.app.models.User;
import com.app.utils.UtilsValidation;
/**
 * ControllerMovements manages operations related to displaying movements (transactions)
 *
 * @author Adrian
 */
public class ControllerMovements {
    Scanner lector = new Scanner(System.in);
    AccountDao accountDao = new AccountDao();
    TransactionDao transactionDao = new TransactionDao();
    /**
     * A method to display all movements for a specific user account.
     *
     * @param  user  the user object for whom movements are to be displayed
     */
    public void allMovements(User user) {
        System.out.println("\n-> ALL MOVEMENTS");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        String accountNumber;
        boolean validate = false;
        int idAccount = 0;
        System.out.println("");
        do {
            System.out.print("Select the account to see the movements: ");
            accountNumber = lector.nextLine();
            if (UtilsValidation.validateAccountNumber(accountNumber)) {
                for (Account account : accounts) {
                    if (account.getAccount_number().equals(accountNumber)) {
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
        List<Transaction> transactions = transactionDao.selectByAccount(account.getAccount_number());
        if (transactions.size() == 0) {
            System.out.println("There are no transactions for this account");
        } else{
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toStringFormatted(accountNumber));
            }
        }
    }

}
