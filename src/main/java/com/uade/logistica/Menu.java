package com.uade.logistica;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    private final Sistema sistema;
    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
        this.sistema = new Sistema();
    }

    public void ejecutar() {

        cargarInventario();

        while (true) {

            System.out.println("\n=== SISTEMA LOGISTICO ===");
            System.out.println("1. Cargar inventario.json");
            System.out.println("2. Registrar paquete manualmente en el centro");
            System.out.println("0. Salir");

            String opcion = leer("Seleccione una opcion: ");

            if (opcion == null || opcion.equals("0")) {
                System.out.println("Fin del programa");
                return;
            }

            switch (opcion) {

                case "1" -> cargarInventario();

                case "2" -> registrarManual();

                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void cargarInventario() {

        try {

            sistema.cargarInventario();

            System.out.println("Inventario cargado correctamente.");

        } catch (IOException e) {

            System.out.println(
                    "Error al cargar inventario: "
                            + e.getMessage());
        }
    }

    private void registrarManual() {

        String id = leer("ID unico: ");
        if (id == null) return;

        String peso = leer("Peso en kg: ");
        if (peso == null) return;

        String destino = leer("Destino: ");
        if (destino == null) return;

        String urgente = leer("Urgente (s/n): ");
        if (urgente == null) return;

        String contenido =
                leer("Contenido (ej. Electronica: Notebook): ");

        if (contenido == null) return;

        try {

            sistema.registrarPaquete(
                    id,
                    peso,
                    destino,
                    urgente,
                    contenido);

            System.out.println("Registrado correctamente.");

        } catch (IllegalArgumentException e) {

            System.out.println(
                    e.getMessage() + " Paquete sin registrar.");
        }
    }

    private String leer(String mensaje) {

        System.out.print(mensaje);

        return scanner.hasNextLine()
                ? scanner.nextLine().trim()
                : null;
    }
}