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
}