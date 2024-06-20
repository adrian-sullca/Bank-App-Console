package com.app.menus;

import java.util.Scanner;

import com.app.controllers.ControllerTransactions;
import com.app.models.User;

public class MenuTransactions {
    Scanner lector = new Scanner(System.in);
    
    /**
     * Displays the menu options for transactions including Deposit, Withdrawal, Transfer, and Exit.
     */
    private void menuTransactions() {
        System.out.println("\n-> TRANSACTIONS");
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Transfer");
        System.out.println("0. Exit");

        System.out.print("\nChoose an option: ");
    }

    ControllerTransactions controllerTransactions = new ControllerTransactions();

    /**
     * A method that handles different transactions based on user input.
     *
     * @param  user  the user for whom the transactions are being performed
     */
    public void casesMenuTransactions(User user) {
        int option;
        do {
            menuTransactions();
            option = lector.nextInt();
            switch (option) {
                case 1:
                    controllerTransactions.deposit(user);
                    break;
                case 2:
                    controllerTransactions.withdrawal(user);
                    break;
                case 3:
                    controllerTransactions.transfer(user);
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }
}
