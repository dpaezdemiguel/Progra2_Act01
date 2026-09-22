package com.uade.logistica;

import java.time.LocalDateTime;

public class ArbolBinario<T extends Comparable<T>> {

    private ArbolNode<T> root;

    public ArbolBinario() {
        this.root = null;
    }

    public void insert(T element) {
        if (this.root == null) {
            this.root = new ArbolNode<>(element);
            return;
        }

        insertWithNode(this.root, element);
    }

    private void insertWithNode(ArbolNode<T> node, T element) {

        if (node.element.compareTo(element) > 0) {

            if (node.left == null) {
                node.left = new ArbolNode<>(element);
                return;
            }

            insertWithNode(node.left, element);
        }

        if (node.element.compareTo(element) < 0) {

            if (node.right == null) {
                node.right = new ArbolNode<>(element);
                return;
            }

            insertWithNode(node.right, element);
        }
    }

    public void printDepositByLevel(int level) {

        if (level < 0) {
            throw new IllegalArgumentException("El nivel no puede ser negativo");
        }

        System.out.println("Nivel " + level + " (raiz = 0):");
        if (!printDepositByLevelByNode(this.root, level, 0)) {
            System.out.println("No hay depositos en ese nivel.");
        }
    }

    private boolean printDepositByLevelByNode(
            ArbolNode<T> node,
            int level,
            int currentLevel) {

        if (node == null) return false;
        if (level == currentLevel) {
            System.out.println("Deposito: " + node.element);
            return true;
        }
        boolean izquierda = printDepositByLevelByNode(node.left, level, currentLevel + 1);
        boolean derecha = printDepositByLevelByNode(node.right, level, currentLevel + 1);
        return izquierda || derecha;
    }

    public void runAudit() {
        if (root == null) {
            System.out.println("No hay depositos cargados.");
            return;
        }
        LocalDateTime limite = LocalDateTime.now().minusDays(30);

        runAudit(this.root, limite);
    }

    private void runAudit(
            ArbolNode<T> node,
            LocalDateTime limit) {

        if (node == null) {
            return;
        }

        runAudit(node.left, limit);
        runAudit(node.right, limit);

        node.visitado =
                node.fechaUltimaAuditoria == null
                        || node.fechaUltimaAuditoria.isBefore(limit);

        if (node.element instanceof Deposito deposito) {
            deposito.setVisitado(node.visitado);
        }
        System.out.println(node.element + (node.visitado
                ? " - Visitado: requiere auditoria" : " - Auditoria reciente"));
    }
}