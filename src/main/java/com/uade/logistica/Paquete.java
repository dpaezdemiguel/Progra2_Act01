package com.uade.logistica;

public class Paquete<T> implements Comparable<Paquete<?>> {

    private String id;
    private double peso;
    private String destino;
    private boolean urgente;
    private T contenido;

    public Paquete(String id,
                   double peso,
                   String destino,
                   boolean urgente,
                   T contenido) {

        if (id == null || id.isBlank() || destino == null || destino.isBlank()) {
            throw new IllegalArgumentException("El ID y el destino son obligatorios.");
        }
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un numero positivo y finito.");
        }
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido es obligatorio.");
        }
        this.id = id.trim();
        this.peso = peso;
        this.destino = destino.trim();
        this.urgente = urgente;
        this.contenido = contenido;
    }

    public String getId() {
        return id;
    }

    public double getPeso() {
        return peso;
    }

    public String getDestino() {
        return destino;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public T getContenido() {
        return contenido;
    }

    @Override
    public int compareTo(Paquete<?> otro) {
        boolean esteEsPrioritario = this.isUrgente() || this.getPeso() > 50.0;
        boolean otroEsPrioritario = otro.isUrgente() || otro.getPeso() > 50.0;

        if (esteEsPrioritario && !otroEsPrioritario) { // El primero es prio y el segundo no
            return -1;
        } else if (!esteEsPrioritario && otroEsPrioritario) { // El segundo prio y el primero no
            return 1;
        }
        return 0; // iguales
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "id='" + id + '\'' +
                ", peso=" + peso +
                ", destino='" + destino + '\'' +
                ", urgente=" + urgente +
                ", contenido=" + contenido +
                '}';
    }
}
