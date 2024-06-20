package com.app.menus;

import java.util.Scanner;

import com.app.controllers.ControllerMovements;
import com.app.models.User;

public class MenuMovements {
    Scanner lector = new Scanner(System.in);

    /**
     * A method to display the menu options related to movements.
     *
     */
    private void menuMovements() {
        System.out.println("\n-> MOVEMENTS");
        System.out.println("1. See all movements");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }
    
    ControllerMovements controllerMovements = new ControllerMovements();

    /**
     * This function handles the menu options related to movements. It prompts the user to choose an option
     * and based on the choice, it calls the appropriate method from the ControllerMovements class. The function
     * continues to prompt the user until they choose the option to exit (0).
     *
     * @param  user  the user object for whom movements are to be handled
     */
    public void casesMenuMovements(User user) {
        int option;
        do {
            menuMovements();
            option = lector.nextInt();
            switch (option) {
                case 1:
                    controllerMovements.allMovements(user);
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }
}
