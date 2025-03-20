package com.loginapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Secure Login System");
        System.out.print("1. Register\n2. Login\nChoose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            userService.registerUser(username, password);
        } else if (choice == 2) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (userService.loginUser(username, password)) {
                System.out.print("Enter 2FA code sent to email: ");
                String code = scanner.nextLine();
                if (Authenticator.verifyCode(code)) {
                    System.out.println("Login Successful.");
                } else {
                    System.out.println("Invalid 2FA code.");
                }
            } else {
                System.out.println("Login Failed.");
            }
        }
        scanner.close();
    }
}
