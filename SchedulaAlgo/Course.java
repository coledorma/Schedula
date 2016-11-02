/**
 * By Jacob Perks
 *
 *	Course CLASS
 *
 */
package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Course {

	protected String code;
	protected ArrayList<Section> sections;
	
/**
 * Constructor for Sschedule objects
 *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
 **/
	public Course(String c, ArrayList<Section> s){
		code = c;
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return "Course Code:" + code + "\n" + sections;
	}
}