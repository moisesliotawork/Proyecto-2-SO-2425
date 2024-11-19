/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author Moises Liota
 */
public class Node {
    private Object data;
    private Node pnext;
    private int idNode;

    public Node(Object data) {
        this.data = data;
        this.pnext = null;
    }
    
    public Node() {
        this.data = null;
        this.pnext = null;
    }

    public Node getPnext() {
        return pnext;
    }

    public void setPnext(Node pnext) {
        this.pnext = pnext;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }
}
