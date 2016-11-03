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
 * Constructor for SubSection objects
 *	@params s (A,B,C,...etc.), c (123456,...etc.), st ("14:35"), et ("15:55")
 **/
	public SubSection(String s, String t, int c, int tr, String time){
		super(s,t,c,tr,time,null);
	}
	
/**
 * toString() function returns String formatted print of Tutorial 
 *	@params n/a
 *  @overwritten
 **/
	public final String toString(){ 
		String s = "\nSection:\t"+ID+"\nType:\t\t"+prof+"\n"; //<<----TODO: super param Section.prof = type of section (TUT/LAB...etc.)
		for(TimeSlot t : times) if (t != null) s += t;
		return s+"\n";
	}
}