package SchedulaAlgo;
import java.util.*;

public final class run{
	public static void main(String[] args) {
		
		long start = System.nanoTime();
	/**
	 *	START TEST
	 **/
		ArrayList<Commitment> commitments = new ArrayList<Commitment>();
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<SubSection> subsections1 = new ArrayList<SubSection>();
		ArrayList<SubSection> subsections2 = new ArrayList<SubSection>();
		ArrayList<SubSection> subsections3 = new ArrayList<SubSection>();
		ArrayList<SubSection> subsections4 = new ArrayList<SubSection>();
		ArrayList<SubSection> subsections5 = new ArrayList<SubSection>();
		ArrayList<Section> sections1 = new ArrayList<Section>();
		ArrayList<Section> sections2 = new ArrayList<Section>();
		ArrayList<Section> sections3 = new ArrayList<Section>();
		
		commitments.add(new Commitment("LAX",201630,"TR 11:35-12:35"));
		subsections1.add(new SubSection("A1","TUT",223443,201630,"W 04:05-15:50"));
		subsections1.add(new SubSection("A2","TUT",223444,201630,"W 16:05-17:25"));
		subsections2.add(new SubSection("B1","TUT",223443,201630,"W 04:05-15:50"));
		subsections2.add(new SubSection("B2","TUT",223444,201630,"W 16:05-17:25"));
		sections1.add(new Section("A", "Bobby", 231454,201630,"TR 11:35-12:35",subsections1));
		sections1.add(new Section("B", "Timmy", 231454,201630,"TR 11:35-12:35",subsections2));
		courses.add(new Course("COMP300", "Summer", sections1));

		subsections3.add(new SubSection("A1","TUT",223443,201630,"W 04:05-15:50"));
		subsections3.add(new SubSection("A2","TUT",223444,201630,"W 16:05-17:25"));
		sections2.add(new Section("A", "John", 231454,201630,"TR 11:35-12:35",subsections3));
		courses.add(new Course("COMP100", "Summer", sections2));

		subsections4.add(new SubSection("A1","TUT",223443,201630,"W 04:05-15:50"));
		subsections4.add(new SubSection("A2","TUT",223444,201630,"W 16:05-17:25"));
		subsections5.add(new SubSection("B1","TUT",223443,201630,"W 04:05-15:50"));
		subsections5.add(new SubSection("B2","TUT",223444,201630,"W 16:05-17:25"));
		sections3.add(new Section("A", "Hannah", 231454,201630,"TR 11:35-12:35",subsections4));
		sections3.add(new Section("B", "Samuel", 231454,201630,"TR 11:35-12:35",subsections5));
		courses.add(new Course("COMP500", "Summer", sections1));

		Schedule sched = new Schedule(commitments, courses);


	/**
	 *	RESULTS
	 **/
		long stop = System.nanoTime();
		System.out.println("Execution time: "+ 1e-6 * (stop-start) +"ms");
		return;
	}
}