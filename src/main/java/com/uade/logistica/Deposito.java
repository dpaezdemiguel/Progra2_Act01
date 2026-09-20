package com.uade.logistica;

import java.time.LocalDateTime;

public class Deposito implements Comparable<Deposito> {

    private int id;
    private String nombre;
    private boolean visitado;
    private LocalDateTime fechaUltimaAuditoria;

    public Deposito(int id, String nombre,
                    boolean visitado,
                    LocalDateTime fechaUltimaAuditoria) {
        this.id = id;
        this.nombre = nombre;
        this.visitado = visitado;
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public LocalDateTime getFechaUltimaAuditoria() {
        return fechaUltimaAuditoria;
    }

    public void setFechaUltimaAuditoria(LocalDateTime fechaUltimaAuditoria) {
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
    }

    @Override
    public int compareTo(Deposito otro) {
        return Integer.compare(this.id, otro.id);
    }

    @Override
    public String toString() {
        return nombre + " (" + id + ")";
    }
}