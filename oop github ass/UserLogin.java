/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package user.login;

import java.io.Console;
import java.util.Scanner;

public class UserLogin{
    public static void main(String[] args) {
        final String USERNAME = "rouki";     
        final String PASSWORD = "12345";      

        int attempts = 3; 

        Scanner sc = new Scanner(System.in);
        Console console = System.console();   //code l use to hide the password

        while (attempts > 0) {
            System.out.print("Enter username: ");
            String inputUser = sc.nextLine();

            String inputPass;

           
            if (console != null) {
                char[] passArray = console.readPassword("Enter password: ");
                inputPass = new String(passArray);
            } else {
                System.out.print("Enter password: ");
                inputPass = sc.nextLine();
             
                System.out.println("Password entered: " + "*".repeat(inputPass.length()));
            }

            // this is code for checking my user credentials
            if (inputUser.equals(USERNAME) && inputPass.equals(PASSWORD)) {
                System.out.println("Login Successful! Welcome " + USERNAME);
                break;
            } else {
                attempts--;
                System.out.println("Incorrect username or password. Tries left: " + attempts);
            }

            if (attempts == 0) {
                System.out.println(" Login failed. You have used all attempts.");
            }
        }
    }
}
