package com.uade.logistica;


public class MyOwnStack<T> {
    
    Node<T> beginning;
    int length;

    public MyOwnStack() {
        this.beginning = null;
        this.length = 0;
    }

    public void push(T element){
        Node<T> newElement = new Node<>(element);
        newElement.setNext(beginning);
        beginning = newElement;
        length++;
    }

    public int size() {
        return length;
    }

    public T pop(){
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T popData = beginning.info;
        beginning = beginning.next;
        length--;
        return popData;
    }


    public boolean isEmpty(){
        return this.length == 0;
    }


    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return beginning.info;
    }



    @Override
    public String toString() {    
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        Node<T> actual = beginning;
        while (actual != null) {
            sb.append(actual.info);
            if (actual.next != null) {
                sb.append(", "); // Separa los elementos
            }
            actual = actual.next;
        }
        
        sb.append("] <- Tope");
        return sb.toString();
    }


}
