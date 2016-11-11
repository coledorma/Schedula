/**
 * By Daniel Fitzhenry and Jacob Perks
 *
 *	SubSection CLASS
 *
 */

package SchedulaAlgo;

public final class SubSection extends Section{

	/**
	Ctor
	Params:
	cr = array list of courses user wants to take
	cm = array list of commitments user has
	p = array list of strings, max size 2, representing preferable time of day to have classes ("Morning", Afternoon", "Evening")
	size = max number of schedules wanted to generate
	*/
	public SubSection(String s, String t, int c, int tr, String time){
		super(s,t,c,tr,time,null);
	}
	
    /**
    Function: returns formatted string of SubSection info
    */
    /**
	public final String toString(){ 
		String s = "\nSection:\t"+ID+"\nType:\t\t"+prof+"\n"; //<<----TODO: super param Section.prof = type of section (TUT/LAB...etc.)
		for(TimeSlot t : times) if (t != null) s += t;
		return s+"\n";
	}*/
}