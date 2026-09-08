package com.uade.logistica;

public class CentroDistribucion {

    private final MyOwnPriorityStack<Paquete<?>> cola;

    public CentroDistribucion() {

        cola = new MyOwnPriorityStack<>();
    }


    public void agregarPaquete(Paquete<?> paquete) {
        cola.push(paquete);
    }

    public Paquete<?> procesarSiguiente() {
        return cola.isEmpty() ? null : cola.getPriorityElement();
    }

    public int cantidadPendientes() {
        return cola.size();
    }
}
