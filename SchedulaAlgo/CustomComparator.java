/**
 * By Jacob Perks
 *
 *	Custom Comparator
 *
 */
package SchedulaAlgo;

import java.util.*;

//TODO: Figure out other useful functions/parameters

public class CustomComparator implements Comparator<Course>{

	public CustomComparator() {
		
	}

	@Override 
	public int compare(Course c1, Course c2) {
		if (c1.sections.size() > c2.sections.size()) {
			return 1;
		} else if (c1.sections.size() < c2.sections.size()) {
			return -1;
		} else {
			int c1Sub = 0;
			int c2Sub = 0;
			for (int i = 0; i <0; i++) {
				c1Sub += c1.sections.get(i).subSec.size();
				c2Sub += c2.sections.get(i).subSec.size();
			}
			if (c1Sub > c2Sub) {
				return 1;
			} else if (c2Sub > c1Sub) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}