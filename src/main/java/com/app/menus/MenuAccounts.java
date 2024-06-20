package com.app.menus;


import java.util.Scanner;

import com.app.controllers.ControllerAccounts;
import com.app.models.User;

public class MenuAccounts {
    Scanner lector = new Scanner(System.in);
    
    /**
     * Prints the menu options for managing user accounts.
     *
     * This method displays the available options for managing user accounts.
     * The options include viewing all accounts, opening a new account,
     * closing an existing account, and exiting the application.
     *
     * @return         	void
     */
    private void menuAccounts() {
        System.out.println("\n-> ACCOUNTS");
        System.out.println("1. See accounts");
        System.out.println("2. Open account");
        System.out.println("3. Close account");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }

    /**
     * Handles the menu options for managing user accounts.
     *
     * This method displays the available options for managing user accounts.
     * The options include viewing all accounts, opening a new account,
     * closing an existing account, and exiting the application.
     *
     * @param  user  the user object for whom the accounts are to be managed
     */
    public void casesMenuAccounts(User user) {
        int option;
        ControllerAccounts controllerAccounts = new ControllerAccounts();
        do {
            menuAccounts();
            option = lector.nextInt();
            switch (option) {
                case 1:
                    controllerAccounts.seeAccounts(user);
                    break;
                case 2:
                    controllerAccounts.openAccount(user);
                    break;
                case 3:
                    controllerAccounts.closeAccount(user);
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }
}
