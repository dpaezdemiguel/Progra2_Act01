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

    public boolean existeId(String id) {
        return ids.contains(id);
    }

    public void registrarId(String id) {
        ids.add(id);
    }
}
