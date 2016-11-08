/**
 * By Daniel Fitzhenry + Jacob Perks
 *
 *	SUBSECTION CLASS
 *
 **/
package SchedulaAlgo;

//TODO: Figure out other useful functions/parameters

public final class SubSection extends Section{

    /**
     * CONSTRUCTOR
     *	@params s ("A","B1","C03",...),
     *			t ("TUT","LAB",...),
     *			c (123456,345675,...),
     *			tr (201710,201620,...),
     *			time ("W 1405-1555","MW 1405-1555","MWF 1405-1555",...) [MAX 3] else null
     **/
	public SubSection(String s, String t, int c, int tr, String time){
		super(s,t,c,tr,time,null);
	}
	
/**
 * toString() function returns String formatted print of Tutorial 
 *	@params n/a
 *  @overwritten
 **
	public final String toString(){ 
		String s = "\nSection:\t"+ID+"\nType:\t\t"+prof+"\n"; //<<----TODO: super param Section.prof = type of section (TUT/LAB...etc.)
		for(TimeSlot t : times) if (t != null) s += t;
		return s+"\n";
	}
*/
}