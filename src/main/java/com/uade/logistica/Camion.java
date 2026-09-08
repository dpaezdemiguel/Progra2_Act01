package com.uade.logistica;

public class Camion {

    private MyOwnStack<Paquete<?>> carga;

    public Camion() {
        carga = new MyOwnStack<>();
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

    public Paquete<?> descargarPaquete() {
        return carga.isEmpty() ? null : carga.pop();
    }
}
