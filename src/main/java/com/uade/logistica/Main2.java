package com.uade.logistica;

import java.util.List;

public class Main2 {

    public static void main(String[] args) {

        try {

            List<Deposito> depositos =
                    DepositoJsonLoader.cargarDepositos();

            ArbolBinario<Deposito> arbol =
                    new ArbolBinario<>();

            for (Deposito deposito : depositos) {
                arbol.insert(deposito);
            }

            System.out.println("Ejecutando auditoria...");

            arbol.runAudit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}