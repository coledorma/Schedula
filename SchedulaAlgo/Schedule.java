/**
 * By Jacob Perks
 *
 *	Schedule CLASS
 *
 */
package SchedulaAlgo;

import java.util.Collections;
import java.util.*;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Schedule {
	
	protected HashMap<Integer,HashMap<Section,SubSection>> schedules;
	protected ArrayList<Course> courses;
	protected ArrayList<Commitment> commits;
	
/**
 * Constructor for Schedule objects
 *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
 **/
	public Schedule(ArrayList<Commitment> com, ArrayList<Course> co){
		courses = co;
		commits = com;
		Collections.sort(courses, new CustomComparator());
		// IF THERE IS A COURSE WITH 1 SECTION
		if (courses.get(0).sections.size() == 1) {
			// USED FOR STORING CLASSES IN TREE-LIKE FASION (FAST + SIMPLE)
			HashMap<Course, HashMap<Section, TimeSlot[]>> courseMap = new HashMap<Course, HashMap<Section, TimeSlot[]>>();
			// POPULATING courseMap WITH COURSES FROM courses
			for(Course c : courses){
				HashMap<Section, TimeSlot[]> cMap = new HashMap<Section, TimeSlot[]>();
				for(Section s : c.sections) cMap.put(s, s.getTimes());
				courseMap.put(c, cMap);
			}
			System.out.println("SIZE OF MAP:\t"+ numberOfTimeSlots(courseMap)+"\n");
			generate(courseMap);
		}
	}

/**
 * 	numberOfTimeSlots(HashMap<Course, HashMap<Section, TimeSlot[]>>) helper function   
 *	@params map (HashMap<Course, HashMap<Section, TimeSlot[]>>)
 *  @return number of time slots in courses
 **/
	private int numberOfTimeSlots(HashMap<Course, HashMap<Section, TimeSlot[]>> map){
		int i = 0;
		for(HashMap<Section, TimeSlot[]> secs : map.values()){
			for(TimeSlot[] tArray : secs.values()){
				for (TimeSlot t : tArray) i += (t != null) ? 1:0;
			}
			for(Section s : secs.keySet()){
				for (SubSection ss : s.getSubSecs()){
					for (TimeSlot t : ss.getTimes()) i += (t != null) ? 1:0;
				}
			}
		}
		return i;
	}
	
	private void generate(HashMap<Course, HashMap<Section, TimeSlot[]>> map){
		schedules = new HashMap<Integer, HashMap<Section,SubSection>>();
		int schedCount = 0;
		HashMap<Section,SubSection> posSchedg = new HashMap<Section,SubSection>();
		for(int i = 0; i < numberOfTimeSlots(map); i++){
			for(HashMap<Section, TimeSlot[]> secs : map.values()){
				Section prevSec = null;
				for(Section s : secs.keySet()){
					for (SubSection ss : s.getSubSecs()){
						if (prevSec == null) prevSec = s;
						else if (ss.conflicts(prevSec)) continue;
						if (!s.conflicts(ss)) posSchedg.put(s,ss);
					}
				}
			}
			if(!schedules.containsValue(posSchedg)){
				schedules.put(schedCount, posSchedg);
				schedCount += 1;
				posSchedg = new HashMap<Section,SubSection>();
			}
		}
		System.out.println(toString());
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){
		String crns = "";
		for (Course c : courses){
			for (Section s : c.sections){
				crns += s.getCrn() + "\n";
				if (s.getSubSecs() != null && !s.getSubSecs().isEmpty()){
					crns += "Add. Req.:\t[";
					for (SubSection ss : s.getSubSecs())
						crns += ss.getCrn() + " ";
					crns += "]\n";
				}
			}
		}
		return "INPUT COURSES:\n" + crns + "\nINPUT COMMITMENTs:"+ commits +"\nDIFFERENT OPTIONS:\n"+mapToString(schedules);
	}
	public static String mapToString(Map mp) {
		String s = "\n";
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			s += pair.getKey() + " = " + pair.getValue() + "\n";
			it.remove(); // avoids a ConcurrentModificationException
		}
		return s;
	}
}