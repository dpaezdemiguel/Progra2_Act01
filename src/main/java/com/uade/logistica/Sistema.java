package com.uade.logistica;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sistema {

    private final CentroDistribucion centro;
    private final Camion camion;
    private final Set<String> ids;

    public Sistema() {
        this.centro = new CentroDistribucion();
        this.camion = new Camion();
        this.ids = new HashSet<>();
    }

    public CentroDistribucion getCentro() {
        return centro;
    }

    public Camion getCamion() {
        return camion;
    }

    public int cantidadPendientes() {
        return centro.cantidadPendientes();
    }

    public int cantidadEnCamion() {
        return camion.cantidadPaquetes();
    }

    public void cargarInventario() throws IOException {

        List<Paquete<?>> paquetes = JsonLoader.cargarInventario();

        Set<String> nuevos = new HashSet<>();

        for (Paquete<?> paquete : paquetes) {

            if (ids.contains(paquete.getId())
                    || !nuevos.add(paquete.getId())) {

                throw new IOException(
                        "Inventario sin incorporar: ID duplicado "
                                + paquete.getId());
            }
        }

        for (Paquete<?> paquete : paquetes) {
            centro.agregarPaquete(paquete);
        }

        ids.addAll(nuevos);
    }

    public void registrarPaquete(
            String id,
            String peso,
            String destino,
            String urgente,
            String contenido) {

        if (ids.contains(id)) {
            throw new IllegalArgumentException(
                    "Ya existe un paquete con ese ID.");
        }

        if (!urgente.equalsIgnoreCase("s")
                && !urgente.equalsIgnoreCase("n")) {

            throw new IllegalArgumentException(
                    "Ingrese s o n.");
        }

        if (contenido == null || contenido.isBlank()) {

            throw new IllegalArgumentException(
                    "El contenido es obligatorio.");
        }

        try {

            Paquete<String> paquete =
                    new Paquete<>(
                            id,
                            Double.parseDouble(
                                    peso.replace(',', '.')),
                            destino,
                            urgente.equalsIgnoreCase("s"),
                            contenido);

            centro.agregarPaquete(paquete);

            ids.add(paquete.getId());

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Peso invalido.");
        }
    }

    public boolean existeId(String id) {
        return ids.contains(id);
    }

    public void registrarId(String id) {
        ids.add(id);
    }
}