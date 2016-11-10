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
	//	ArrayList<SubSection> subsections6 = new ArrayList<SubSection>();
		ArrayList<Section> sections1 = new ArrayList<Section>();
		ArrayList<Section> sections2 = new ArrayList<Section>();
		ArrayList<Section> sections3 = new ArrayList<Section>();
	//	ArrayList<Section> sections4 = new ArrayList<Section>();
		
		commitments.add(new Commitment("LAX",201630,"TR 1135-1235"));
		
		subsections1.add(new SubSection("A1","TUT",111111,201630,"M 1405-1555"));
		subsections1.add(new SubSection("A2","TUT",111112,201630,"W 1605-1755"));
		subsections2.add(new SubSection("B1","TUT",111113,201630,"R 1405-1555"));
		subsections2.add(new SubSection("B2","TUT",111114,201630,"W 1605-1755"));
		subsections3.add(new SubSection("A1","TUT",222221,201630,"R 1305-1455"));
		subsections3.add(new SubSection("A2","TUT",222222,201630,"M 1605-1755"));
		subsections4.add(new SubSection("A1","TUT",333331,201630,"F 1405-1555"));
		subsections4.add(new SubSection("A2","TUT",333332,201630,"W 1605-1755"));
		subsections5.add(new SubSection("B1","TUT",333333,201630,"F 1405-1555"));
		subsections5.add(new SubSection("B2","TUT",333334,201630,"W 1605-1755"));
	/*	subsections6.add(new SubSection("A1","TUT",100001,201630,"F 1405-1555"));
		subsections6.add(new SubSection("A2","TUT",100002,201630,"W 1605-1755"));
	*/	
		sections1.add(new Section("A", "Bobby", 111110,201630,"TR 1135-1255",subsections1));
		sections1.add(new Section("B", "Timmy", 111100,201630,"TR 1135-1255",subsections2));
		sections2.add(new Section("A", "John", 222220,201630,"W 1135-1255",subsections3));
		sections3.add(new Section("A", "Hannah", 333330,201630,"TR 1135-1255",subsections4));
		sections3.add(new Section("B", "Samuel", 333300,201630,"TR 1135-1255",subsections5));
		
		courses.add(new Course("COMP300", sections1));
		courses.add(new Course("COMP100", sections2));
		courses.add(new Course("COMP500", sections3));
		
	/*	sections4.add(new Section("C", "Mike", 111000,201630,"W 1135-1355",subsections6));
		courses.add(new Course("COMP666", sections4));
	*/	
		ScheduleGenerator schedgs = new ScheduleGenerator(courses, commitments, 10);

	/**
	 *	RESULTS
	 **/
		long stop = System.nanoTime();
		System.out.println("Execution time: "+ 1e-6 * (stop-start) +"ms");
		return;
	}
}