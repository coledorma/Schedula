/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	Schedule CLASS
 *
**/
package SchedulaAlgo;

import java.util.LinkedList;
import java.util.HashSet;

public class Schedule {	
	private HashSet<Section> sections;
	private int size;
	
	/** CONSTRUCTOR
	 *	Function:	creates new linked list of sections
	 *	@params	n/a
	 **/
	public Schedule() { sections = new HashSet<Section>(); }
	
	// Getters
	public int getSize(){ return size;}
	public LinkedList<Section> getSections(){ return new LinkedList<Section>(sections); }
	
	// Overrides
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Schedule))return false;
		Schedule otherSchedg = (Schedule) other;
		int elementsInCommon = 0;
		for (Section s: this.sections)
		elementsInCommon += (otherSchedg.sections.contains(s))? 1:0;
		return otherSchedg.size == elementsInCommon;
	}

	/** ADD
	 *	Function:	adds Section to LinkedList of Sections
	 *	@params	s = Section to be added
	 *	@return boolean indication if add successful
	 **/
	public boolean add(Section s){
		for (Section inSchedg : sections)
		if (inSchedg.conflicts(s)) return false;
		boolean matches = sections.add(s);
		size += (matches)?1:0;
		return matches;
	}
	
	/** CONTAINS
	 *	Function:	Check in O(1) if a given Section s is in this Schedule or not
	 *	@params	s = Section to be checked
	 *	@return boolean indication if s is in sections
	 **/
	public boolean contains(Section s) { return sections.contains(s); }
 
	/** TO STRING
	 *	Function:	string formatted representation of sections data member
	 *	@params	n/a
	 *	@overwritten
	 **/
	public String toString() { return sections.toString(); }
}