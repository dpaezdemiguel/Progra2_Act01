package com.uade.logistica;

public class Main2 {
    public static void main(String[] args) {
        ArbolBinario<Integer> arbol = new ArbolBinario<>();

        arbol.insert(50);

        arbol.insert(30);
        arbol.insert(70);
        arbol.insert(20);
        arbol.insert(40);
        arbol.runAudit();
        arbol.printDepositByLevel(0);

        System.out.println("Inserciones terminadas.");
    }
}
