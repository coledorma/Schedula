/**
 * By Jacob Perks
 *
 *	Schedule CLASS
 *
 */
package SchedulaAlgo;

import java.util.Collections;
import java.util.ArrayList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Schedule {
	
	protected ArrayList<ArrayList<String>> schedules;
	protected ArrayList<Course> courses;
	
/**
 * Constructor for Schedule objects
 *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
 **/
	public Schedule(ArrayList<Commitment> com, ArrayList<Course> co){
		courses = co;
		Collections.sort(courses, new CustomComparator());
		if (courses.get(0).sections.size() == 1) {
			BinaryTree b1 = new BinaryTree(courses.get(0).sections.get(0));
			Node x;
			for (int i = 0; i < courses.size()-1; i++) {
				for (int k = 0; k < courses.get(i).sections.size(); k++) {
					for (int j = 0; j < courses.get(i+1).sections.size(); j++) {
						x = b1.find(courses.get(i).sections.get(k), b1.root);
						x.addChild(courses.get(i+1).sections.get(j));
					}
				}
			}
		}
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return "" + courses;
	}

}