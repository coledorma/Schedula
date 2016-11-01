package SchedulaAlgo;
import java.util.*;

public final class run{
	public static void main(String[] args) {
		long start = System.nanoTime();
/**
 *	START TEST
 **/
		LinkedList<SubSection> t = new LinkedList<SubSection>();
		ArrayList<Section> s = new ArrayList<Section>();
		Schedule sched = new Schedule(s);

		t.add(new SubSection("A1",223443,201630,"W 04:05-15:50"));	
		t.add(new SubSection("A2",223444,201630,"W 16:05-17:25"));
		s.add(new Section("A", "John Smith", 231454,201630,"TR 11:35-12:35",t));


		System.out.println(s.get(0));

		System.out.println((t.get(0).getTimes()[0]).conflicts(t.get(1).getTimes()[0]));
		System.out.println((t.get(0).getTimes()[0]).difference(t.get(1).getTimes()[0]));
		
/**
 *	RESULTS
 **/
		long stop = System.nanoTime();
		System.out.println("Execution time: "+ 1e-6 * (stop-start) +"ms");
		return;
	}
}