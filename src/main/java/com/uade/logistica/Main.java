package com.uade.logistica;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {

            System.out.println("\n=== SISTEMA LOGISTICO ===");
            System.out.println("1. Cargar inventario");
            System.out.println("2. Mostrar mensaje de camion");
            System.out.println("3. Mostrar mensaje de distribucion");
            System.out.println("0. Salir");

            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    JsonLoader.cargarInventario();
                    break;

                case 2:
                    System.out.println("Modulo Camion");
                    break;

                case 3:
                    System.out.println("Modulo Centro de Distribucion");
                    break;

                case 0:
                    System.out.println("Fin del programa");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);

        scanner.close();
    }
}