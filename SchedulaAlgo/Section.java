/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	Section CLASS
 *
**/

package SchedulaAlgo;

import java.util.ArrayList;

public class Section {
	protected String ID;
	protected String prof;
	protected int crn;
	public TimeSlot[] times;
	public ArrayList<SubSection> subSec;

	/** CONSTRUCTOR
	 *	Function:	parses time and creates a an array of TimeSlot objects
	 *	@params	n = section number (ie A, B, V, ...)
	 *			p = professor's name
	 *			c = course registration number 
	 *			term = term section is in
	 *			time = time of section, including days of the week
	 *			s = array list of SubSections
	 **/
	public Section(String n, String p, int c, int term, String time, ArrayList<SubSection> s){
		int startHour, startMinute, endHour, endMinute;
		int year = (int)(term/100);
		int semester = term - year*100;
		ID = n;
		prof = p;
		crn = c;
		subSec = s;
		times = new TimeSlot[3];
		if (time.length() == 11){
		// If once a week (W 1405-1555) 
			startHour = Integer.parseInt(time.substring(2,4));
			startMinute = Integer.parseInt(time.substring(4,6));
			endHour = Integer.parseInt(time.substring(7,9));
			endMinute = Integer.parseInt(time.substring(9));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = times[2] = null;
		} else if (time.length() == 12){
		// If twice a week (TR 1405-1555)
			startHour = Integer.parseInt(time.substring(3,5));
			startMinute = Integer.parseInt(time.substring(5,7));
			endHour = Integer.parseInt(time.substring(8,10));
			endMinute = Integer.parseInt(time.substring(10));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
			times[2] = null;
		} else if (time.length() == 13){
		// If three times a week (MWF 1405-1555)
			startHour = Integer.parseInt(time.substring(4,6));
			startMinute = Integer.parseInt(time.substring(6,8));
			endHour = Integer.parseInt(time.substring(9,11));
			endMinute = Integer.parseInt(time.substring(11));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
			times[2] = new TimeSlot(time.charAt(2),startHour,startMinute,endHour,endMinute,semester,year);
		} else times[0] = times[1] = times[2] = null;
	}

	/** TO STRING
	 *	Function:	string formatted representation of this Section obj
	 *	@params	n/a
	 *	
	 **/
	public String toString(){
		/*String s = "Section: "+ID+"\nProf:"+prof+"\n";
		for(TimeSlot t : times) if (t != null) s += t+"\n";
		s += "\nAdditional Requirements: \n"+subSec;
		return s;*/
		return prof+crn;
	}
	
	/** CONFLICTS
	 *	Function:	compares two sections to see if their times conflict
	 *	@params	s = Section that will be compared with this
	 *	
	 **/
	public boolean conflicts(Section s) {
		boolean b = false;
		if (this == null || s == null) return b;
		for(TimeSlot t1 : times){
			if (t1 == null) continue;
			for (TimeSlot t2 : s.times){
				if (t2 == null) continue;
				b = (t1.conflicts(t2) || t2.conflicts(t1));
			}
		}
		return b;
	}

	/** ADD SUB-SECTION
	 *	Function:	adds SubSection to array list of SubSections
	 *	@params	subSection: SubSection to be added
	 *
	 **/
	public void addSubSection(SubSection subSection) { subSec.add(subSection); }

   	 // Getters
	public String getID() { return ID;}
	public String getProf() { return prof;}
	public int getCrn() { return crn;}
	public int numDays() { int count = 0; for(TimeSlot t : times) count += (t!=null) ? 1:0; return count; }
	public TimeSlot[] getTimes() { return times;}
	public ArrayList<SubSection> getSubSecs() { return subSec;}

	// Overrides
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Section))return false;
		Section otherSec = (Section) other;
		return this.crn == otherSec.crn;
	}    
}
