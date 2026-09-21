package com.uade.logistica;

import java.time.LocalDateTime;

public class ArbolNode<T> {

    T element;

    ArbolNode<T> right;
    ArbolNode<T> left;

    boolean visited;
    LocalDateTime lastAudit;

    public ArbolNode(T element) {

        this.right = null;
        this.left = null;

        this.element = element;

        this.visited = false;
        this.lastAudit = LocalDateTime.now().minusDays(40);
    }
}
