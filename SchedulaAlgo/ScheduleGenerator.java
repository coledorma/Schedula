/**
 *  By: Daniel Fitzhenry and Jacob Perks
 *
 *	ScheduleGenerator CLASS
 *
 */

package SchedulaAlgo;

import java.util.*;
import java.lang.*;

public class ScheduleGenerator {

	protected LinkedList<Schedule> schedules;
	protected ArrayList<Course> courses;
	protected ArrayList<Commitment> commits;
	protected ArrayList<String> periods;

	/*
	Ctor: calls generate function
	Params:
	cr = array list of courses user wants to take
	cm = array list of commitments user has
	p = array list of strings, max size 2, representing preferable time of day to have classes ("Morning", Afternoon", "Evening")
	size = max number of schedules wanted to generate
	*/
	public ScheduleGenerator(ArrayList<Course> cr, ArrayList<Commitment> cm, ArrayList<String> p,  int size){
		courses = cr;
		commits = cm;
		schedules = new LinkedList<Schedule>();
		Collections.sort(courses, new CustomComparator());
		periods = p;

		generate(size);
	}
	
	/*
	Function: generates Schedule objects and stores them in linked list schedules
	Params:
	size = max number of schedules wanted to generate
	*/
	private void generate(int size){
		int i =0;
		int j = 0;
		while (j < 100){
			j++;
			if (i ==10) { break;}
			long seed = System.nanoTime();
			Collections.shuffle(courses, new Random(seed));
			Schedule posSchedg = new Schedule();
			for (Course c : courses){
				seed = System.nanoTime();
				Collections.shuffle(c.sections, new Random(seed));
				for (Section s : c.sections){
					if (periods.size() == 2) {
						if (posSchedg.add(s) && !commitConflicts(s) && s.getTimes()[0].period() == periods.get(0) || s.getTimes()[0].period() == periods.get(1)) {
							seed = System.nanoTime();
							Random gen = new Random(seed);
							Collections.shuffle(s.getSubSecs(), gen);
							SubSection ss = s.getSubSecs().get(gen.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0) || ss.getTimes()[0].period() == periods.get(1)) {	
								posSchedg.add(ss);
							}
						}
					} else if (periods.size() == 1) {
						if (posSchedg.add(s) && !commitConflicts(s) && s.getTimes()[0].period() == periods.get(0)) {
							seed = System.nanoTime();
							Random gen = new Random(seed);
							Collections.shuffle(s.getSubSecs(), gen);
							SubSection ss = s.getSubSecs().get(gen.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0)) {	
								posSchedg.add(ss);
							}
						}
					} else {
						if (posSchedg.add(s) && !commitConflicts(s)) {
							seed = System.nanoTime();
							Random gen = new Random(seed);
							Collections.shuffle(s.getSubSecs(), gen);
							SubSection ss = s.getSubSecs().get(gen.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss)) {	
								posSchedg.add(ss);
							}
						}
					}
				}
			}
			if(!schedules.contains(posSchedg) && (posSchedg.getSize() >= courses.size()*2)){
				schedules.add(posSchedg);
				i++;
			}
		}
		System.out.println(toString());
	}
	
	/*
	Function: returns formatted string of array lists courses and commitments, and linked list schedules
	*/
	public String toString(){
		String s = "INPUT COURSES:\n";
		for (Course c : courses) s += c+"\n";
		s += "INPUT COMMITMENTS:\n";
		for (Commitment c : commits) s += c+"\n";
		s += "POSSIBLE SCHEDULES:\n";
		for (Schedule sc : schedules) s += sc+"\n";
		return s;
	}


	/*
	Function: checks to see if a given section conflicts with a commitment, returns true if confliction and false if no confliction
	Params:
	s = Section object that will be compared with commitment 
	*/
	public boolean commitConflicts(Section s) {
		for (int i = 0; i < commits.size(); i++) {
			if (s.conflicts(commits.get(i).getTimes())) {
				return true;
			}
		}
		return false;
	}
}