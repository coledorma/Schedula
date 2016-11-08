package SchedulaAlgo;
import java.util.*;


public class Node {
    private List<Node> children = new ArrayList<Node>();
    private Node parent = null;
    private Section data = null;

    public Node(Section d) {
        data = d;
		parent = null;
    }

    public Node(Section d, Node p) {
        data = d;
        parent = p;
    }

    public List<Node> getChildren() {
        return children;
    }
	
    public void addChild(Section data) {
        Node child = new Node(data,this);
        children.add(child);
    }

    public void addChild(Node child) {
		child.parent = this;
        children.add(child);
    }

    public Section getData() {
        return data;
    }
	
	public Node getParent() {
		return parent;
	}
	
	public void traverse(Node n){
		System.out.println(n.data);
		for(Node each : n.getChildren()){
			traverse(each);
		}
	}
	
	public Set<Node> getAllLeafNodes() {
    Set<Node> leafNodes = new HashSet<Node>();
    if (this.children.isEmpty()) {
        leafNodes.add(this);
    } else {
        for (Node child : this.children) {
            leafNodes.addAll(child.getAllLeafNodes());
        }
    }
    return leafNodes;
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