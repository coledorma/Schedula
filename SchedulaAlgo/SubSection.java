/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	SubSection CLASS
 *
**/
package SchedulaAlgo;

import java.util.ArrayList;

public final class SubSection extends Section{

	/** CONSTRUCTOR
	 *	Function:	creates SubSection obj
	 *	@params	n = section number (ie A, B, V, ...)
	 *			t = type of section
	 *			c = course registration number 
	 *			tr = term section is in
	 *			time = time of section, including days of the week
	 *
	 **/
	public SubSection(String s, String t, int c, int tr, String time){
		super(s,t,c,tr,time,new ArrayList<SubSection>());
	}
	
	/** TO STRING
	 *	Function:	string formatted representation of this SubSection obj
	 *	@params	n/a
	 *	@overwritten
	 **/
/*	public final String toString(){ 
		String s = "\nSection:\t"+ID+"\nType:\t\t"+prof+"\n"; //<<----TODO: super param Section.prof = type of section (TUT/LAB...etc.)
		for(TimeSlot t : times) if (t != null) s += t;
		return s+"\n";
	}
*/
}
