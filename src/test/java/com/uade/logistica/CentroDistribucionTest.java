package com.uade.logistica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CentroDistribucionTest {

    @Test
    void procesaPrimeroLosPaquetesUrgentes() {

        CentroDistribucion centro = new CentroDistribucion();

        Paquete<String> normal =
                new Paquete<>(
                        "P001",
                        20,
                        "Rosario",
                        false,
                        "Teclado"
                );

        Paquete<String> urgente =
                new Paquete<>(
                        "P002",
                        10,
                        "Cordoba",
                        true,
                        "Notebook"
                );

        centro.agregarPaquete(normal);
        centro.agregarPaquete(urgente);

        Paquete<?> siguiente = centro.procesarSiguiente();

        assertEquals("P002", siguiente.getId());
    }
}