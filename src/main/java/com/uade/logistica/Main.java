package com.uade.logistica;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private final CentroDistribucion centro = new CentroDistribucion();
    private final Camion camion = new Camion();
    private final Set<String> ids = new HashSet<>();
    private final Scanner scanner;

    public Main(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(scanner).ejecutar();
        }
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
                    Paquete<?> paquete = centro.procesarSiguiente();
                    if (paquete == null) {
                        System.out.println("No hay paquetes pendientes.");
                    } else {
                        camion.cargarPaquete(paquete);
                        System.out.println("Cargado: " + paquete);
                    }
                }
                case "4" -> {
                    Paquete<?> paquete = camion.deshacerCarga();
                    if (paquete == null) {
                        System.out.println("El camion esta vacio.");
                    } else {
                        centro.agregarPaquete(paquete);
                        System.out.println("Carga deshecha; devuelto al centro: " + paquete);
                    }
                }
                case "5" -> {
                    Paquete<?> paquete = camion.descargarPaquete();
                    System.out.println(paquete == null ? "El camion esta vacio." : "Descargado: " + paquete);
                }
                case "6" -> System.out.println("Pendientes: " + centro.cantidadPendientes()
                        + " | En camion: " + camion.cantidadPaquetes());
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void cargarInventario() {
        try {
            List<Paquete<?>> paquetes = JsonLoader.cargarInventario();
            Set<String> nuevos = new HashSet<>();
            for (Paquete<?> paquete : paquetes) {
                if (ids.contains(paquete.getId()) || !nuevos.add(paquete.getId())) {
                    System.out.println("Inventario sin incorporar: ID duplicado " + paquete.getId());
                    return;
                }
            }
            for (Paquete<?> paquete : paquetes) {
                centro.agregarPaquete(paquete);
            }
            ids.addAll(nuevos);
            System.out.println("Paquetes incorporados al centro: " + paquetes.size());
        } catch (IOException e) {
            System.out.println("Error al cargar inventario: " + e.getMessage());
        }
    }

    private void registrarManual() {
        String id = leer("ID unico: ");
        if (id == null) return;
        if (ids.contains(id)) {
            System.out.println("Ya existe un paquete con ese ID.");
            return;
        }
        String peso = leer("Peso en kg: ");
        if (peso == null) return;
        String destino = leer("Destino: ");
        if (destino == null) return;
        String urgente = leer("Urgente (s/n): ");
        if (urgente == null) return;
        if (!urgente.equalsIgnoreCase("s") && !urgente.equalsIgnoreCase("n")) {
            System.out.println("Ingrese s o n. Paquete sin registrar.");
            return;
        }
        String contenido = leer("Contenido (ej. Electronica: Notebook): ");
        if (contenido == null) return;
        if (contenido.isBlank()) {
            System.out.println("El contenido es obligatorio. Paquete sin registrar.");
            return;
        }
        try {
            Paquete<String> paquete = new Paquete<>(id, Double.parseDouble(peso.replace(',', '.')),
                    destino, urgente.equalsIgnoreCase("s"), contenido);
            centro.agregarPaquete(paquete);
            ids.add(paquete.getId());
            System.out.println("Registrado: " + paquete);
        } catch (NumberFormatException e) {
            System.out.println("Peso invalido. Paquete sin registrar.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + " Paquete sin registrar.");
        }
    }

    private String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }
}
