package com.uade.logistica;

import java.util.PriorityQueue;

public class CentroDistribucion {

    private PriorityQueue<Paquete<?>> cola;

    public CentroDistribucion() {

        cola = new PriorityQueue<>((p1, p2) -> {

            int prioridad1 = calcularPrioridad(p1);
            int prioridad2 = calcularPrioridad(p2);

            return Integer.compare(prioridad2, prioridad1);
        });
    }

    private int calcularPrioridad(Paquete<?> paquete) {

        if (paquete.isUrgente()) {
            return 2;
        }

        if (paquete.getPeso() > 50) {
            return 1;
        }

        return 0;
    }

    public void agregarPaquete(Paquete<?> paquete) {
        cola.add(paquete);
    }

    public Paquete<?> procesarSiguiente() {
        return cola.poll();
    }

    public int cantidadPendientes() {
        return cola.size();
    }
}