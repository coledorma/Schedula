package SchedulaAlgo;
import java.util.*;

public class BinaryTree {
    
    public static Node root;

    public BinaryTree(Section data) {
        root = new Node(data);
    }
	
	public String toString(){
		if (root == null) return "EMPTY";
		Node n = root;
		String s = "Root:\t" + n.toString();
		while (n.getChildren().size() > 0){
			for (Node m : n.getChildren()){
				s += m.toString();
				return s;
			}
		}
		return s;
	}

    public Node find(Section data, Node node) {
        if (node.getData()==data) {
            return node;
        }
        List<Node> children = node.getChildren(); 
        Node temp;
        if (children.size()>0)
        for (int i = 0; i < children.size(); i++) {         
            temp = find(data, children.get(i));
            if(temp!=null)
                return temp;
        }
        return null;
    }
	
}
