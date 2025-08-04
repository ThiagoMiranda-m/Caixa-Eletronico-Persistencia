//O usuário deverá escrever o nome e 2 números, você deverá criar um programa que lê os dois numéros e calcule a média deles.
//Retorne a frase "Olá {nome], a média dos números X e X foi X."



package Banco.App;

import Banco.Service.UserService;
import java.util.Scanner;

public class BancoApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        UserService service = new UserService();

        System.out.println("=======MENU=======");
        System.out.println("1 - Registro");
        System.out.println("2 - Login");
        int option = scan.nextInt();

        switch (option) {
            case 1:
                service.register();
            case 2:
                service.login();
        }

    }
}