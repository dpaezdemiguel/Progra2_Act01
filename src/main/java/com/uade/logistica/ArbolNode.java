package com.uade.logistica;

import java.time.LocalDateTime;

public class ArbolNode<T> {

    T element;

    ArbolNode<T> right;
    ArbolNode<T> left;

    boolean visitado;
    LocalDateTime fechaUltimaAuditoria;

    public ArbolNode(T element) {

        this.right = null;
        this.left = null;

        this.element = element;

        this.visitado = false;
        this.fechaUltimaAuditoria = null;
        if (element instanceof Deposito deposito) {
            this.visitado = deposito.isVisitado();
            this.fechaUltimaAuditoria = deposito.getFechaUltimaAuditoria();
        }
    }
}
