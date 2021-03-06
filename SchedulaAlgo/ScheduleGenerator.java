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
	public ScheduleGenerator(ArrayList<Course> cr, ArrayList<Commitment> cm, ArrayList<String> p,  int size, boolean prefOnline){
		if (prefOnline){
			courses = new ArrayList<Course>();
			for (Course c: cr){
				Course sects = new Course(c.code,new ArrayList<Section>());
				for (Section s : c.sections)
				if (s.numDays() == 0) sects.sections.add(s);
				if (!sects.sections.isEmpty()) courses.add(sects);
				else courses.add(c);
			}
		} else courses = cr;
		commits = cm;
		periods = p;
		schedules = new LinkedList<Schedule>();
		generator = new Random(System.nanoTime());
		long startTime = System.nanoTime();
		generate(size);
		long endTime = System.nanoTime();
		System.out.println("Execution time : " + 1e-6 * (endTime - startTime));
	}

	/** GENERATE (MAIN LOGICAL ASPECT)
	 *	Function:	generates Schedule objects and stores them in linked list schedules
	 *	@params	size = max number of schedules wanted to generate, and size*10 upper bound O(10n)
	 **/
	private void generate(int size){
		int searchCount = 0, subCount;
		while(schedules.size() != size){
			Schedule posSchedg = new Schedule();
			generator.setSeed(System.nanoTime());
			subCount = courses.size();
			Collections.shuffle(courses, generator);
			for (Course c : courses){
				Collections.shuffle(c.sections, generator);
				for (Section s : c.sections){
					
					/* IF ONLINE COURSE */
					if (s.numDays() == 0){
						posSchedg.add(s);
						if (s.getSubSecs().size() == 0) continue;
						subCount -= 1;
						Collections.shuffle(s.getSubSecs(), generator);
						SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
						switch (periods.size()) {
						case 2:
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0) || ss.getTimes()[0].period() == periods.get(1))
							if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							break;
						case 1:
							if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0))
							if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							break;
						default:
							if (!commitConflicts(ss))
							if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							break;
						}
						if (posSchedg.contains(s)) break;
					}
					
					/* ELSE */
					switch (periods.size()) {
					case 2:
						if (!commitConflicts(s) && s.getTimes()[0].period() == periods.get(0) || s.getTimes()[0].period() == periods.get(1)) {
							if (posSchedg.add(s)) {
								if (s.getSubSecs().size() == 0) continue;
								subCount -= 1;
								Collections.shuffle(s.getSubSecs(), generator);
								SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
								if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0) || ss.getTimes()[0].period() == periods.get(1))
								if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							}
						} break;
					case 1:
						if (!commitConflicts(s) && s.getTimes()[0].period() == periods.get(0)) {
							if (posSchedg.add(s)) {
								if (s.getSubSecs().size() == 0) continue;
								subCount -= 1;
								Collections.shuffle(s.getSubSecs(), generator);
								SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
								if (!commitConflicts(ss) && ss.getTimes()[0].period() == periods.get(0))
								if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							}
						} break;
					default:
						if (!commitConflicts(s)) {
							if (posSchedg.add(s)) {
								if (s.getSubSecs().size() == 0) continue;
								subCount -= 1;
								Collections.shuffle(s.getSubSecs(), generator);
								SubSection ss = s.getSubSecs().get(generator.nextInt(s.getSubSecs().size()));
								if (!commitConflicts(ss))
								if (!posSchedg.add(ss)) posSchedg.getSections().removeLast();
							}
						} break;
					}
					if (posSchedg.contains(s)) break;
				}
			}
			if (posSchedg.getSize() == ((courses.size()*2)- subCount))
			if(!schedules.contains(posSchedg)) schedules.add(posSchedg);
			if (searchCount>=size*10) break;
			else ++searchCount;
		}
		Collections.sort(schedules, new MyComparator());
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
		for (Schedule sc : schedules) s += schedules.indexOf(sc) + " size: " + sc.getSize() + ": " + sc+"\n";
		return s;
	}
 
	/** COMMITMENT CONFILCTS
	 *	Function:	checks to see if a given section conflicts with a commitment, returns true if conflict and false if no conflict
	 *	@params	s = Section object that will be compared with commitment
	 *	@return boolean indicating if given Section s conflicts with a commitment in commits 
	 **/
	public boolean commitConflicts(Section s) {
		for (Commitment commitment : commits)
		if ((s.conflicts(commitment.getTimes())) || commitment.getTimes().conflicts(s))
		return true;
		return false;
	}
	
	// Getters
	public LinkedList<Schedule> getSchedules() { return schedules; }
}
