package com.app.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.app.dao.AccountDao;
import com.app.models.Account;
import com.app.models.Transaction;

public class UtilsAccounts {
    public static Account createAccount(AccountDao accountDao) {
        Account account = new Account();
        account.setAccount_number(generateUniqueAccountNumber(accountDao));
        account.setBalance(0.0);
        account.setCreated(new Date());
        List<Transaction> transactions = new ArrayList<Transaction>();
        account.setTransaction(transactions);
        return account;
    }

    private static String generateUniqueAccountNumber(AccountDao accountDao) {
        String accountNumber;
        do {
            accountNumber = generateRandomAccountNumber();
        } while (accountDao.checkAccount(accountNumber) != 0);
        return accountNumber;
    }
    private static String generateRandomAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}
