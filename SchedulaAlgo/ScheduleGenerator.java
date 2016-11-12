/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	ScheduleGenerator CLASS
 *
**/
package SchedulaAlgo;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ScheduleGenerator {
	protected LinkedList<Schedule> schedules;
	protected ArrayList<Course> courses;
	protected ArrayList<Commitment> commits;
	protected ArrayList<String> periods;
	protected Random generator;

	/** CONSTRUCTOR
	 *	Function:	creates new ScheduleGenerator and attempts to generate possible schedules "size*10" times
	 *	@params	cr = array list of courses user wants to take
	 *			cm = array list of commitments user has
	 *			p = array list of strings, max size 2, representing preferable time of day to have classes ("Morning", Afternoon", "Evening")
	 *			size = max number of schedules wanted to generate
	 **/
	public ScheduleGenerator(ArrayList<Course> cr, ArrayList<Commitment> cm, ArrayList<String> p,  int size){
		courses = cr;
		commits = cm;
		periods = p;
		schedules = new LinkedList<Schedule>();
		generator = new Random(System.nanoTime());
		generate(size);
	}

	/** GENERATE (MAIN LOGICAL ASPECT)
	 *	Function:	generates Schedule objects and stores them in linked list schedules
	 *	@params	size = max number of schedules wanted to generate
	 **/
	private void generate(int size){
		int searchCount = 0, subCount;
		for(int i = 0; i < size;){
			subCount = courses.size();
			Schedule posSchedg = new Schedule();
			Collections.shuffle(courses, generator);
			for (Course c : courses){
				Collections.shuffle(c.sections, generator);
				for (Section s : c.sections){
					if (periods.size() == 2) {
						if (posSchedg.add(s) && !commitConflicts(s) && s.getTimes()[0].period() == periods.get(0) || s.getTimes()[0].period() == periods.get(1)) {
							if (s.getSubSecs() == null) continue;
							subCount -= 1;
							Collections.shuffle(s.getSubSecs(), generator);
							SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0) || ss.getTimes()[0].period() == periods.get(1)) {  
								if (!posSchedg.add(ss)) {
									posSchedg.getSections().removeLast();
								}
							}
						}
					} else if (periods.size() == 1) {
						if (posSchedg.add(s) && !commitConflicts(s) && s.getTimes()[0].period() == periods.get(0)) {
							if (s.getSubSecs() == null) continue;
							subCount -= 1;
							Collections.shuffle(s.getSubSecs(), generator);
							SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0)) { 
								if (!posSchedg.add(ss)) {
									posSchedg.getSections().removeLast();
								}
							}
						}
					} else {
						if (posSchedg.add(s) && !commitConflicts(s)) {
							if (s.getSubSecs() == null) continue;
							subCount -= 1;
							Collections.shuffle(s.getSubSecs(), generator);
							SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
							if (!commitConflicts(ss)) {
								if (!posSchedg.add(ss)) {
									posSchedg.getSections().removeLast();
								}
							}
						}
					}
				}
			}
			if (posSchedg.getSize() == ((courses.size()*2)- subCount))
			if(!schedules.contains(posSchedg)){
				schedules.add(posSchedg);
				i += 1;
			}
			if (searchCount>=size*10) break;
			else searchCount+=1;
		}
		System.out.println(searchCount + " = # of loops\n" + toString());
    }
 
	/** TO STRING
	 *	Function:	string formatted representation of this ScheduleGenerator
	 *	@params	n/a
	 **/
	public String toString(){
		String s = "INPUT COURSES:\n";
		for (Course c : courses) s += c+"\n";
		s += "INPUT COMMITMENTS:\n";
		for (Commitment c : commits) s += c+"\n";
		s += "POSSIBLE SCHEDULES:\n";
		for (Schedule sc : schedules) s += schedules.indexOf(sc) + ": " + sc+"\n";
		return s;
	}
 
	/** COMMITMENT CONFILCTS
	 *	Function:	checks to see if a given section conflicts with a commitment, returns true if conflict and false if no conflict
	 *	@params	s = Section object that will be compared with commitment
	 *	@return boolean indicating if given Section s conflicts with a commitment in commits 
	 **/
	public boolean commitConflicts(Section s) {
		for (int i = 0; i < commits.size(); i++) {
			if (s.conflicts(commits.get(i).getTimes())) {
				return true;
			}
		}
		return false;
	}
	
	// Getters
	public LinkedList<Schedule> getSchedules() { return schedules; }
}
