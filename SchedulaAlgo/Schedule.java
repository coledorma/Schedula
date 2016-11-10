/**
 * By Daniel Fitzhenry and Jacob Perks
 *
 *	Schedule CLASS
 *
 */

package SchedulaAlgo;

import java.util.Collections;
import java.util.*;
import java.lang.*;

public class Schedule {
	
	private LinkedList<Section> sections;
	private int size;
	
	/*
	Ctor: creates new linked list of sections
	*/
	public Schedule() { 
		sections = new LinkedList<Section>(); 
	}

    /*
    Function: adds Section to sections and returns true if add was successful and false if Section conflicts and cannot be added
    Params:
    s = Section to be added
    */
	public boolean add(Section s){
		for (Section inSchedg : sections)
			if (inSchedg.conflicts(s)){
				return false;
			}
		if (!sections.contains(s)){
			sections.add(s);
			size++;
			return true;
		}
		return false;
	}
 
    /*
    Function: returns formatted string
    */
	public String toString() {
		return sections.toString();
	}
	
    @Override
    /*
    Function: compares two schedules to see if they are the same
    Params:
    other = Object to be comapred with this
    */
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Schedule))return false;
        Schedule otherSchedg = (Schedule) other;
        return this.sections.equals(otherSchedg.sections);
    }

    public int getSize() { return size;}
}