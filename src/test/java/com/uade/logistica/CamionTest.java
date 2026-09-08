package com.uade.logistica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CamionTest {

    @Test
    void deshacerCargaDevuelveUltimoPaquete() {

        Camion camion = new Camion();

        Paquete<String> paquete1 =
                new Paquete<>("P001", 10, "Rosario", false, "Teclado");

        Paquete<String> paquete2 =
                new Paquete<>("P002", 20, "Cordoba", false, "Mouse");

        camion.cargarPaquete(paquete1);
        camion.cargarPaquete(paquete2);

        Paquete<?> retirado = camion.deshacerCarga();

        assertEquals("P002", retirado.getId());
    }
}