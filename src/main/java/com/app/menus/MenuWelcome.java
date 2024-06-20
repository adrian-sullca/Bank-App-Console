package com.app.menus;

import java.util.Scanner;

import com.app.models.User;
import com.app.models.UserSession;

public class MenuWelcome {
    //poner nombre de usuario despues de welcome
    Scanner lector = new Scanner(System.in);
    User user = UserSession.getSesion();
    
    /**
     * Displays the welcome menu for the Bank App.
     *
     * @param  user  the user object for whom the menu is displayed
     * @return         	void
     */
    private void menuWelcome(User user) {
        System.out.println("\n-> WELCOME " + user.getUsername() + " TO BANK APP");
        System.out.println("1. Transactions");
        System.out.println("2. Accounts");
        System.out.println("3. Movements");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }

    MenuTransactions menuTransactions = new MenuTransactions();
    MenuAccounts menuAccounts = new MenuAccounts();
    MenuMovements menuMovements = new MenuMovements();

    /**
     * Executes the casesMenuWelcome function, which displays the welcome menu for the Bank App
     * and allows the user to choose an option. The function prompts the user to choose an option
     * and based on the choice, it calls the appropriate method from the MenuTransactions,
     * MenuAccounts, or MenuMovements class. The function continues to prompt the user until they
     * choose the option to exit (0).
     *
     * @return         	void
     */
    public void casesMenuWelcome() {
        int option;
        do {
            menuWelcome(user);
            option = lector.nextInt();
            switch (option) {
                case 1:
                    menuTransactions.casesMenuTransactions(user);
                    break;
                case 2:
                    menuAccounts.casesMenuAccounts(user);
                    break;
                case 3:
                    menuMovements.casesMenuMovements(user);
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }
}
