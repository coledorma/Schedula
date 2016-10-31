/**
 * By Daniel Fitzhenry + Jacob Perks
 *
 *	SECTION CLASS
 *
 */
package SchedulaAlgo;

import java.util.LinkedList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Section {
	
	protected String ID;
	protected String prof;
	protected int crn;
	protected TimeSlot times;
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
		if (time.length() > 11){
			startHour = Integer.parseInt(time.substring(2,4));
			startMinute = Integer.parseInt(time.substring(5,7));
			endHour = Integer.parseInt(time.substring(7,9));
			endMinute = Integer.parseInt(time.substring(11));
			times = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
		} else {
			times = null;
		}
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return	"Section: "+ID+"\nProf:"+prof+"\n"+times+"\nAdditional Sections: \n"+subSec;
	}

	public String getID() { return ID;}

	public String getProf() { return prof;}

	public int getCrn() { return crn;}

	public TimeSlot getTimes() { return times;}

	public LinkedList<SubSection> getSubSecs() { return subSec;}
}
