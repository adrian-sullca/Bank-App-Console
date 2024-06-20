package com.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.dao.AccountDao;
import com.app.dao.UserDao;
import com.app.menus.MenuWelcome;
import com.app.models.Account;
import com.app.models.User;
import com.app.models.UserSession;
import com.app.utils.UtilsAccounts;
import com.app.utils.UtilsValidation;
/**
 * ControllerPrincipal handles user sign up, sign in, and various account validation processes.
 * 
 * @author Adrian
 */
public class ControllerPrincipal {
    Scanner lector = new Scanner(System.in);
    UserDao userDao = new UserDao();
    AccountDao accountDao = new AccountDao();
    /**
     * Signs up a new user by prompting the user to enter their username, password,
     * and DNI. Creates a new User and Account objects, inserts them into the database,
     * and sets the User's account to the newly created Account.
     *
     * @return void
     */
    public void signUp() {
        System.out.println("\n-> SIGN UP");
        boolean validate = false;
        String username = validateUsername(validate);
        validate = false;
        String password = validatePassword(validate);
        validate = false;
        String dni = validateDni(validate);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setDni(dni);

        Account account = new Account();
        List<Account> accounts = new ArrayList<Account>();
        int idUser = userDao.insertReturnId(user);
        account = UtilsAccounts.createAccount(accountDao);
        account.setIdUser(idUser);
        accounts.add(account);
        user.setAccount(accounts);
        accountDao.insertReturnId(account);
        System.out.println("\nSign up successful. Please sign in");
    }

    /**
     * Sign in the user by validating the provided username and password.
     *
     * @return None
     */
    public void singIn() {
        System.out.println("\n-> SIGN IN");
        System.out.print("Username: ");
        String username = lector.nextLine();
        System.out.print("Password: ");
        String password = lector.nextLine();

        UserSession userSession = new UserSession();
        User user = userSession.validateUser(username, password);
        if (user != null) {
            userSession.setSesion(user);
            MenuWelcome menuWelcome = new MenuWelcome();
            menuWelcome.casesMenuWelcome();
        } else {
            System.out.println("User not found. Try again");
        }
    }

    /**
     * Validates the DNI entered by the user.
     *
     * @param  validate  a boolean indicating whether the DNI is valid or not
     * @return           the validated DNI
     */
    private String validateDni(boolean validate) {
        String dni;
        do {
            System.out.print("DNI: ");
            dni = lector.nextLine();
            if (!UtilsValidation.validateDni(dni)) {
                System.out.println("Invalid dni. Must contain 8 digits and 1 letter. Try again");
            } else {
                if (userDao.checkDni(dni) != 0) {
                    System.out.println("Dni already exists. Try again");
                } else {
                    validate = true;
                }
            }
        } while (!validate);
        return dni;
    }
    /**
     * Validates the password entered by the user.
     *
     * @param  validate  a boolean indicating whether the password is valid or not
     * @return           the validated password
     */
    private String validatePassword(boolean validate) {
        String password;
        do {
            System.out.print("Password: ");
            password = lector.nextLine();
            if (!UtilsValidation.validatePassword(password)) {
                System.out.println("Invalid password. Must contain at least one letter and one symbol. Try again");
            } else {
                validate = true;
            }    
        } while (!validate);
        return password;
    }
    /**
     * Validates the username entered by the user.
     *
     * @param  validate  a boolean indicating whether the username is valid or not
     * @return           the validated username
     */
    private String validateUsername(boolean validate) {
        String username;
        do {
            System.out.print("Username: ");
            username = lector.nextLine();
            if (!UtilsValidation.validateUsername(username)) {
                System.out.println("Invalid username. Username cannot contain special characters or spaces. Try again");
            } else {
                if (userDao.checkUsername(username) != 0) {
                    System.out.println("Username already exists. Try again");
                } else {
                    validate = true;
                }
            }
        } while (!validate);
        return username;
    }
}
