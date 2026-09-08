package com.uade.logistica;



public class Node<T> {
    T info;
    Node<T> next;
 

    public Node(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public Node<T> getNext() {
        return next;
    }


    public void setNext(Node<T> next) {
        this.next = next;
    }
    
    @Override
    public String toString() {    
      return info.toString();
    }
    
}