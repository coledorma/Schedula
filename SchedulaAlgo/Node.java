package SchedulaAlgo;
import java.util.*;


public class Node {
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private Section data = null;

    public Node(Section d) {
        data = d;
    }

    public Node(Section d, Node p) {
        data = d;
        parent = p;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Section data) {
        Node child = new Node(data);
        children.add(child);
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public Section getData() {
        return data;
    }

    public void print(int level) {
        for (int i = 1; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(data.getProf());
        for (Node child : children) {
            child.print(level + 1);
        }
    }

}