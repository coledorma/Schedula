/**
 * By Jacob Perks
 *
 *	Schedule CLASS
 *
 */
package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Schedule {
	
	protected ArrayList<Section> sections;
	
/**
 * Constructor for Sschedule objects
 *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
 **/
	public Schedule(ArrayList<Section> s){
		sections = s;
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return "" + sections;
	}
}