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
            System.out.println("3. Procesar siguiente y cargar en camion");
            System.out.println("4. Deshacer ultima carga (devolver al centro)");
            System.out.println("5. Descargar siguiente paquete del camion");
            System.out.println("6. Ver cantidades");
            System.out.println("7. Cargar deposito (depositos.json)");
            System.out.println("8. Correr auditoria");
            System.out.println("9. Reporte de niveles");
            System.out.println("0. Salir");

            String opcion = leer("Seleccione una opcion: ");

            if (opcion == null || opcion.equals("0")) {
                System.out.println("Fin del programa");
                return;
            }

            switch (opcion) {

                case "1" -> cargarInventario();

                case "2" -> registrarManual();

                case "3" -> {
                    Paquete<?> paquete = sistema.procesarYCargar();
                    System.out.println(paquete == null ? "No hay paquetes pendientes." : "Cargado: " + paquete);
                }
                case "4" -> {
                    Paquete<?> paquete = sistema.deshacerCarga();
                    System.out.println(paquete == null ? "El camion esta vacio."
                            : "Carga deshecha; devuelto al centro: " + paquete);
                }
                case "5" -> {
                    Paquete<?> paquete = sistema.getCamion().descargarPaquete();
                    System.out.println(paquete == null ? "El camion esta vacio." : "Descargado: " + paquete);
                }
                case "6" -> System.out.println("Pendientes: " + sistema.cantidadPendientes()
                        + " | En camion: " + sistema.cantidadEnCamion());
                case "7" -> cargarDepositos();
                case "8" -> sistema.correrAuditoria();
                case "9" -> reporteNiveles();
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void cargarDepositos() {
        try {
            int cantidad = sistema.cargarDepositos();
            System.out.println("Depositos cargados: " + cantidad);
        } catch (IOException e) {
            System.out.println("Error al cargar depositos: " + e.getMessage());
        }
    }

    private void reporteNiveles() {
        String nivel = leer("Nivel a consultar (0 = raiz): ");
        if (nivel == null) return;
        try {
            sistema.reporteNivel(Integer.parseInt(nivel));
        } catch (IllegalArgumentException e) {
            System.out.println("Ingrese un nivel entero no negativo.");
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