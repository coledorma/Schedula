/**
 * 
 *
 *	ScheduleGenerator CLASS
 *
 */
package SchedulaAlgo;

import java.util.*;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class ScheduleGenerator {

	protected LinkedList<Schedule> schedules;
	protected ArrayList<Course> courses;
	protected ArrayList<Commitment> commits;

	public ScheduleGenerator(ArrayList<Course> cr, ArrayList<Commitment> cm, int size){
		courses = cr;
		commits = cm;
		schedules = new LinkedList<Schedule>();
		generate(size);
	}
	
	private void generate(int size){
		for(int i = 0; i < size; i++){
			long seed = System.nanoTime();
			Collections.shuffle(courses, new Random(seed));
			Schedule posSchedg = new Schedule();
			for (Course c : courses){
				seed = System.nanoTime();
				Collections.shuffle(c.sections, new Random(seed));
				for (Section s : c.sections){
					if (posSchedg.add(s)){
						seed = System.nanoTime();
						Random gen = new Random(seed);
						Collections.shuffle(s.getSubSecs(), gen);
						posSchedg.add(s.getSubSecs().get(gen.nextInt(s.getSubSecs().size())));
					}
				}
			}
			if(!schedules.contains(posSchedg)){
				schedules.add(posSchedg);
			}
		}
		System.out.println(toString());
	}
	
	public String toString(){
		String s = "INPUT COURSES:\n";
		for (Course c : courses) s += c+"\n";
		s += "INPUT COMMITMENTS:\n";
		for (Commitment c : commits) s += c+"\n";
		s += "POSSIBLE SCHEDULES:\n";
		for (Schedule sc : schedules) s += sc+"\n";
		return s;
	}
}