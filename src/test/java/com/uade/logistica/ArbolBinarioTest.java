package com.uade.logistica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ArbolBinarioTest {

    @Test
    void insertaDepositosSinErrores() {

        ArbolBinario<Deposito> arbol = new ArbolBinario<>();

        assertDoesNotThrow(() -> {

            arbol.insert(new Deposito(50, "Central", false, null));
            arbol.insert(new Deposito(20, "Cordoba", false, null));
            arbol.insert(new Deposito(80, "Mendoza", false, null));
            arbol.insert(new Deposito(10, "Rosario", false, null));
            arbol.insert(new Deposito(30, "Santa Fe", false, null));

        });
    }

    @Test
    void ejecutaAuditoriaSinErrores() {

        ArbolBinario<Deposito> arbol = new ArbolBinario<>();

        arbol.insert(new Deposito(50, "Central", false, null));
        arbol.insert(new Deposito(20, "Cordoba", false, null));
        arbol.insert(new Deposito(80, "Mendoza", false, null));

        assertDoesNotThrow(arbol::runAudit);
    }

    @Test
    void imprimeNivelSinErrores() {

        ArbolBinario<Deposito> arbol = new ArbolBinario<>();

        arbol.insert(new Deposito(50, "Central", false, null));
        arbol.insert(new Deposito(20, "Cordoba", false, null));
        arbol.insert(new Deposito(80, "Mendoza", false, null));

        assertDoesNotThrow(() -> arbol.printDepositByLevel(1));
    }

    @Test
    void auditaEnPostOrdenYMarcaSoloFechasVencidas() {
        java.time.LocalDateTime ahora = java.time.LocalDateTime.now();
        Deposito central = new Deposito(50, "Central", true, ahora.minusDays(10));
        Deposito izquierdo = new Deposito(20, "Cordoba", false, ahora.minusDays(31));
        Deposito derecho = new Deposito(80, "Mendoza", false, null);
        ArbolBinario<Deposito> arbol = new ArbolBinario<>();
        arbol.insert(central);
        arbol.insert(izquierdo);
        arbol.insert(derecho);
        java.io.PrintStream anterior = System.out;
        java.io.ByteArrayOutputStream salida = new java.io.ByteArrayOutputStream();
        try (java.io.PrintStream captura = new java.io.PrintStream(salida)) {
            System.setOut(captura);
            arbol.runAudit();
        } finally {
            System.setOut(anterior);
        }
        org.junit.jupiter.api.Assertions.assertFalse(central.isVisitado());
        org.junit.jupiter.api.Assertions.assertTrue(izquierdo.isVisitado());
        org.junit.jupiter.api.Assertions.assertTrue(derecho.isVisitado());
        String texto = salida.toString();
        org.junit.jupiter.api.Assertions.assertTrue(texto.indexOf("Cordoba") < texto.indexOf("Mendoza"));
        org.junit.jupiter.api.Assertions.assertTrue(texto.indexOf("Mendoza") < texto.indexOf("Central"));
        org.junit.jupiter.api.Assertions.assertEquals(ahora.minusDays(31), izquierdo.getFechaUltimaAuditoria());
    }
}
