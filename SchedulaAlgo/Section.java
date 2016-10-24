/**
 * By Daniel Fitzhenry (14-10-2016)
 *
 *	SECTION CLASS
 *
 */
package SchedulaAlgo;

import java.util.LinkedList;
import java.lang.String;

//TODO: Figure out other useful functions/parameters

public class Section {
	
	protected char name;
	protected String prof;
	protected int crn;
	protected String time;
	private LinkedList<SubSection> tuts;
	
/**
 * Constructor for Section objects
 *	@params n (A,B,C,...etc.), p ("name"), c (123456,...etc.), st ("14:35"),
 *			et ("15:55"), t (Linked List = [TutorialA1,TutorialA2,...etc.])
 **/
	public Section(char n, String p, int c, String ti, LinkedList<SubSection> t){
		name = n;
		prof = p;
		crn = c;
		time = ti;
		tuts = t;
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **//*
	public String toString(){ 
		return	"Section: "+name+"\nProf:"+prof+"\nTimes:"+
				String.format("%02d", startTimeHour)+":" + String.format("%02d", startTimeMin)+" - "+
				String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMin)+"\n"+
				"Tutorial Sections: "+tuts;
	}

	public String timeOfDay(Section A){ 
		if (A.time) // If class starts/ends between 8:00 am and 11:59am
			return "Morning";
		if (A.time) // If class starts/ends between 12:00pm and 3:59pm
			return "Afternoon";
		if (A.time) // If class starts/ends between 4:00pm and 10:00pm
			return "Evening";
	}*/	
}