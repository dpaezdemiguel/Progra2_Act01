package com.uade.logistica;

import java.util.ArrayList;

public class MyOwnPriorityStack<Paquete extends Comparable<? super Paquete>> {
    private final ArrayList<Paquete> stack;

    public MyOwnPriorityStack() {
        this.stack = new ArrayList<>();
    }

    public void push(Paquete item) {
        stack.add(item);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public Paquete getPriorityElement() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Priority stack is empty");
        }

        int firstPriority = 0;
        for (int i = 1; i < stack.size(); i++) {
            if (stack.get(i).compareTo(stack.get(firstPriority)) < 0) {
                firstPriority = i;
            }
        }
        return stack.remove(firstPriority);
    }
}