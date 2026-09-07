package com.uade.logistica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaqueteTest {

    @Test
    void crearPaqueteCorrectamente() {

        Paquete<String> paquete =
                new Paquete<>(
                        "P001",
                        25,
                        "Rosario",
                        true,
                        "Notebook"
                );

        assertEquals("P001", paquete.getId());
        assertEquals(25, paquete.getPeso());
        assertEquals("Rosario", paquete.getDestino());
        assertTrue(paquete.isUrgente());
    }
}