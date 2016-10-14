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
	protected int startTimeMin;
	protected int startTimeHour;
	protected int endTimeMin; 
	protected int endTimeHour;
	private LinkedList<Tutorial> tuts;
	
/**
 * Constructor for Section objects
 *	@params n (A,B,C,...etc.), p ("name"), c (123456,...etc.), st ("14:35"),
 *			et ("15:55"), t (Linked List = [TutorialA1,TutorialA2,...etc.])
 **/
	public Section(char n, String p, int c, String st, String et, LinkedList<Tutorial> t){
		name = n;
		prof = p;
		crn = c;
		startTimeMin = Integer.parseInt(st.substring(3,5));
		startTimeHour = Integer.parseInt(st.substring(0,2));
		endTimeMin = Integer.parseInt(et.substring(3,5));
		endTimeHour = Integer.parseInt(et.substring(0,2));
		tuts = t;
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return	"Section: "+name+"\nProf:"+prof+"\nTimes:"+
				String.format("%02d", startTimeHour)+":" + String.format("%02d", startTimeMin)+" - "+
				String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMin)+"\n"+
				"Tutorial Sections: "+tuts;
	}
}