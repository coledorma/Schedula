package SchedulaAlgo;
import java.util.*;

public final class run{
	public static void main(String[] args) {
		LinkedList<Section> s = new LinkedList<Section>();
		LinkedList<SubSection> t = new LinkedList<SubSection>();
		
		t.add(new SubSection("A1",223443,201620,"W 14:05-15:55"));	
		t.add(new SubSection("A2",223444,201620,"W 16:05-17:25"));
		s.add(new Section("A", "John Smith", 231454,201630,"T 11:35-12:35",t));

		System.out.println(s.peek());

		System.out.println();
		System.out.println((t.get(0).times).difference(t.get(1).times));
		return;
	}
}