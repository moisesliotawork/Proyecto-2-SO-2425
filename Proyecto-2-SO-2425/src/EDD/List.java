/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import java.util.Random;

/**
 *
 * @author Moises Liota
 */
public class List {
    private Node pFirst;
    private int size;

    public List() {
        this.pFirst = null;
        this.size = 0;
    }

    public Node getpFirst() {
        return pFirst;
    }

    public void setpFirst(Node pFirst) {
        this.pFirst = pFirst;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isEmpty() {
        return getpFirst() == null;
    }
    
    
    public Node next(Node pNode) {
        if (pNode != null) {
            return pNode.getPnext();
        } else {
            return null;
        }
    }
    
     public void destroy() {
        Node pAux = getpFirst();
        while (pAux != null) {
            pAux = getpFirst();
            setpFirst(next(pAux));
            pAux = null;
        }
        this.setSize(0);
        System.gc();
    }
     
     public void deleteFirst() {
        if (!isEmpty()) {
            this.setpFirst(this.pFirst.getPnext());
            size--;
        }
    }
     
     
    public Node deleteAndReturn(int index) {
        if (!isEmpty()) {
            if (index >= 0 && index < this.size) {
                Node pAux = getpFirst();
                if (index == 0) {
                    setpFirst(next(pAux));
                    this.setSize(this.getSize()- 1);
                    return pAux;
                } else {
                    for (int i = 0; i < index - 1; i++) {
                        pAux = next(pAux);
                    }
                    pAux.setPnext(next(next(pAux)));
                    this.setSize(this.getSize()- 1);
                    return pAux;
                }
            }
            return null;
        }
        return null;
    }
    
    
    public void addEnd(Object tInfo) {
        Node pNode = new Node(tInfo);
        if (isEmpty()) {
            setpFirst(pNode);
        } else {
            Node pAux = this.pFirst;
            while (next(pAux) != null) {
                pAux = next(pAux);
            }
            pAux.setPnext(pNode);
        }
        size++;
    }
    
    public Node getRandomNode() {
        if (isEmpty()) {
            return null;
        }

        int index = new Random().nextInt(this.getSize());
        Node current = pFirst;

        for (int i = 0; i < index; i++) {
            current = current.getPnext();
        }

        return current;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "La lista está vacía.";
        }

        StringBuilder builder = new StringBuilder();
        Node current = pFirst;
        while (current != null) {
            builder.append(current.toString());
            builder.append(" -> ");
            current = current.getPnext();
        }
        return builder.toString();
    }
}
