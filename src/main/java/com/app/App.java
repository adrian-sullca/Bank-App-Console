package com.app;

import com.app.menus.MenuGeneral;
public class App {
    /**
     * The main function of the Java program. It creates an instance of the MenuGeneral class
     * and calls the casesMenuBankApp method to execute the menu options for the BankApp.
     *
     * @param  args  the command line arguments passed to the program
     */
    public static void main(String[] args) {
        MenuGeneral app = new MenuGeneral();
        app.casesMenuBankApp();
    }
}