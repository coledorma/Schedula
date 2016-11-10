/**
 * 
 *
 *	ScheduleGenerator CLASS
 *
 */
package SchedulaAlgo;

import java.util.Collections;
import java.util.*;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Schedule {
	
	private LinkedList<Section> sections;
	private int size;
	
/**
 * Constructors for Schedule objects
 *	@params  n/a
 **/
	public Schedule(){ sections = new LinkedList<Section>(); }

/**
 * Add function 
 *	@params	s (Section, SubSection)
 *	@return	boolean indicating if Section fits inside this Schedule
 **/

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
 
/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString() {
		return sections.toString();
	}
	
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Schedule))return false;
        Schedule otherSchedg = (Schedule) other;
        return this.sections.equals(otherSchedg.sections);
    }

    public int getSize() { return size;}
}