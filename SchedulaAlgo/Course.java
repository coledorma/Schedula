/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	Course CLASS
 *
**/
package SchedulaAlgo;

import java.util.ArrayList;

public class Course {
    public String code;
    public ArrayList<Section> sections;

	/** CONSTRUCTOR
	 *	Function:	Construct a course obj
	 *	@params	c = course code
	 *			s = array list of sections of particular course
	 **/
	public Course(String c, ArrayList<Section> s){
		code = c;
		sections = s;
	}

	/** TO STRING
	 *	Function:	string formatted representation of this Course obj
	 *	@params	n/a
	 *	
	 **/
	public String toString(){
		return code;
	}
	
	// Getters
	//TODO:
	
	// Overrides
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Course))return false;
		Course otherCourse = (Course) other;
		return this.code.equals(otherCourse.code);
	}
}
