package com.uade.logistica;

import java.util.Stack;

public class Camion {

    private Stack<Paquete<?>> carga;

    public Camion() {
        carga = new Stack<>();
    }

    public void cargarPaquete(Paquete<?> paquete) {
        carga.push(paquete);
    }

    public Paquete<?> deshacerCarga() {

        if (carga.isEmpty()) {
            return null;
        }

        return carga.pop();
    }

    public int cantidadPaquetes() {
        return carga.size();
    }
}