/**
 * By Daniel Fitzhenry + Jacob Perks
 *
 *	SECTION CLASS
 *
 */
package SchedulaAlgo;

import java.util.LinkedList;
import java.lang.*;

public class Section {
	
	protected String ID;
	protected String prof;
	protected int crn;
	protected TimeSlot[] times;
	private LinkedList<SubSection> subSec;
	
/**
 * Constructor for Section objects
 *	@params n (A,B,C,...etc.), p ("name"), c (123456,...etc.), st ("14:35"),
 *			et ("15:55"), t (Linked List = [TutorialA1,TutorialA2,...etc.])
 **/
	public Section(String n, String p, int c, int term, String time, LinkedList<SubSection> s){
		int startHour, startMinute, endHour, endMinute;
		int year = (int)(term/100);
		int semester = term - year*100;
		ID = n;
		prof = p;
		crn = c;
		subSec = s;
		times = new TimeSlot[3];
		if (time.length() == 13){
			/* W 14:05-15:55 */
			startHour = Integer.parseInt(time.substring(2,4));
			startMinute = Integer.parseInt(time.substring(5,7));
			endHour = Integer.parseInt(time.substring(8,10));
			endMinute = Integer.parseInt(time.substring(11));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = null;
			times[2] = null;
		} else if (time.length() == 14){
			/* TR 14:05-15:55 */
			startHour = Integer.parseInt(time.substring(3,5));
			startMinute = Integer.parseInt(time.substring(6,8));
			endHour = Integer.parseInt(time.substring(9,11));
			endMinute = Integer.parseInt(time.substring(12));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
			times[2] = null;
		} else if (time.length() > 14){
			/* MWF 14:05-15:55 */
			startHour = Integer.parseInt(time.substring(4,6));
			startMinute = Integer.parseInt(time.substring(7,9));
			endHour = Integer.parseInt(time.substring(10,12));
			endMinute = Integer.parseInt(time.substring(13));
			times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
			times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
			times[2] = new TimeSlot(time.charAt(2),startHour,startMinute,endHour,endMinute,semester,year);
		} else { times[0] = null; times[1] = null; times[2] = null; }
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		String s = "Section: "+ID+"\nProf:"+prof+"\n";
		for(TimeSlot t : times) if (t != null) s += t;
		s += "\nAdditional Sections: \n"+subSec;
		return s;
	}

	public String getID() { return ID;}

	public String getProf() { return prof;}

	public int getCrn() { return crn;}

	public TimeSlot[] getTimes() { return times;}

	public LinkedList<SubSection> getSubSecs() { return subSec;}
}
