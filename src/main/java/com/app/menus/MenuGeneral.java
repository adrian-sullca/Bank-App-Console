package com.app.menus;

import java.util.Scanner;

import com.app.controllers.ControllerPrincipal;

public class MenuGeneral {
    Scanner lector = new Scanner(System.in);
    ControllerPrincipal controllerPrincipal = new ControllerPrincipal();

    /**
     * Prints the menu options for the BankApp.
     *
     * This method displays the available options for the BankApp to the user.
     * It includes options for signing up, signing in, and exiting the application.
     *
     * @return         	void
     */
    private void menuBankApp() {
        System.out.println("\n-> BANK APP");
        System.out.println("1. Sign up");
        System.out.println("2. Sign in");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }

    /**
     * Executes the menu options for the BankApp.
     *
     * This method displays the available options for the BankApp to the user.
     * It includes options for signing up, signing in, and exiting the application.
     * The function continues to prompt the user for input until they choose the option to exit.
     *
     * @return         	void
     */
    public void casesMenuBankApp() {
        int option;
        do {
            menuBankApp();
            option = lector.nextInt();
            switch (option) {
                case 1:
                    controllerPrincipal.signUp();
                    break;
                case 2:
                    controllerPrincipal.singIn();
                    break;
                default:
                    break;
            }
        } while (option != 0);
    }
}
