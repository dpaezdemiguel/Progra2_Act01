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
            throw new RuntimeException("El nivel no puede ser negativo");
        }

        printDepositByLevelByNode(this.root, level, 0);
    }

    private void printDepositByLevelByNode(
            ArbolNode<T> node,
            int level,
            int currentLevel) {

        if (node == null) {
            return;
        }

        if (level == currentLevel) {
            System.out.println("Deposito: " + node.element);
            return;
        }

        printDepositByLevelByNode(
                node.left,
                level,
                currentLevel + 1);

        printDepositByLevelByNode(
                node.right,
                level,
                currentLevel + 1);
    }

    public void runAudit() {
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

        node.visited =
                node.lastAudit == null
                        || node.lastAudit.isBefore(limit);

        System.out.println(node.element);
    }
}