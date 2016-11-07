package SchedulaAlgo;
import java.util.*;

public class BinaryTree {
    
    public static Node root;
	private int size;

    public BinaryTree(Section data) {
        root = new Node(data);
		size = 1;
    }
	
	public BinaryTree(Node n) {
        root = n;
		int i = 0;
		if(n == null){ size = i; return; }else i+=1;
		if(n.getChildren() != null || !n.getChildren().isEmpty())
			for(Node m : n.getChildren())
				if (m != null) i += (new BinaryTree(m)).size();
		size = i;
    }
	
	public int size(){ 
		Node n = root;
		int i = 0;
		if(n == null){ return i; } else i+=1;
		if(n.getChildren() != null || !n.getChildren().isEmpty()){
			for(Node m : n.getChildren()){
				i += (new BinaryTree(m)).size();
				i += n.getChildren().size();
			}
		}
		return i;
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
