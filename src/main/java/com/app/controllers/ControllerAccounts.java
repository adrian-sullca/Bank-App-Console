package com.app.controllers;


import java.util.List;
import java.util.Scanner;

import com.app.dao.AccountDao;
import com.app.models.Account;
import com.app.models.User;
import com.app.utils.UtilsAccounts;
import com.app.utils.UtilsValidation;

/**
 * ControllerAccounts maneja operaciones relacionadas con cuentas de usuario
 * como ver cuentas, apertura de cuentas nuevas y cierre de cuentas.
 *
 * @author Adrian
 */
public class ControllerAccounts {
    Scanner lector = new Scanner(System.in);
    AccountDao accountDao = new AccountDao();

    /**
     * Prints all the accounts associated with a user.
     *
     * @param  user  the user object for whom the accounts are to be printed
     */
    public void seeAccounts(User user) {
        System.out.println("\n-> SEE ACCOUNTS");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

    /**
     * Opens a new account for a user.
     *
     * @param  user  the user object for whom the account is being opened
     */
    public void openAccount(User user) {
        System.out.println("\n-> OPEN ACCOUNT");
        Account account = UtilsAccounts.createAccount(accountDao);
        account.setIdUser(user.getId());
        accountDao.insertReturnId(account);
        System.out.println("Account created successfully. Summary:");
        System.out.println(account.toString());
    }

    /**
     * Closes the user account based on the provided account number after validation 
     * by checking that the account has no available balance.
     * 
     * @param  user  the user object for whom the account is to be closed
     */
    public void closeAccount(User user) {
        System.out.println("\n-> CLOSE ACCOUNT");
        List<Account> accounts = accountDao.selectByUserId(user.getId());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
        System.out.print("Select the account to close: ");
        String accountNumber = lector.nextLine();
        if (UtilsValidation.validateAccountNumber(accountNumber)) {
            boolean accountClosed = false;
            boolean accountExists = false;
            for (Account account : accounts) {
                if (account.getAccount_number().equals(accountNumber)) {
                    accountExists = true;
                    if (account.getBalance() == 0.0) {
                        accountDao.delete(account);
                        System.out.println("Account closed successfully.");
                        accountClosed = true;
                        break;
                    }
                }
            }
            if (!accountExists) {
                System.out.println("Invalid account number. Please try again.");
            } else if (!accountClosed) {
                System.out.println("You cannot close an account with an available balance. Choose another account.");
            }
        } else {
            System.out.println("Invalid account number format. Please try again.");
        }
    }
}
