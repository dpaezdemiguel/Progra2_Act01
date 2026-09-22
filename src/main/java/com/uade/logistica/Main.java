package com.uade.logistica;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            Menu menu = new Menu(scanner);

            menu.ejecutar();
        }
    }
}
