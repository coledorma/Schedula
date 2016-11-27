/**
 *	Authors: Jacob Perks and Daniel Fitzhenry
 *	ALACRITYDEVELOPMENT©
 *  Custom Comparator CLASS
 *
**/
package SchedulaAlgo;

import java.util.Comparator;
import java.util.LinkedList;

public class MyComparator implements Comparator<Schedule>{
	private static final char MONDAY = 'M', TUESDAY = 'T', WEDNESDAY = 'W', THURSDAY = 'R', FRIDAY = 'F';
	private TimeSlot min1, max1, min2, max2;

	/** CONSTRUCTOR
	 *	Function:	creates new Comparator operable on List<Schedule> objs
	 *	@params	n/a
	 **/
	public MyComparator() {}

	// Overrides
	@Override 
	public int compare(Schedule s1, Schedule s2) {
		// Initialize useful values
		int dif1, dif2, min1Counter, max1Counter, min2Counter, max2Counter;
		/**
		 *	MONDAY
		 **/
		// Reset values
		min1 = max1 = min2 = max2 = null;
		dif1 = dif2 = min1Counter = max1Counter = min2Counter = max2Counter = 0;
		//Compute and add differences, respectively
		compute(s1.getSections(), min1Counter, max1Counter,MONDAY);
		dif1 += (min1 != null && max1 != null) ? min1.difference(max1):0;
		compute(s2.getSections(), min1Counter, max2Counter,MONDAY);
		dif2 += (min2 != null && max2 != null) ? min2.difference(max2):0;
		/**
		 *	TUESDAY
		 **/
		// Reset values
		min1 = max1 = min2 = max2 = null;
		min1Counter = max1Counter = min2Counter = max2Counter = 0;
		// Compute and add differences, respectively
		compute(s1.getSections(), min1Counter, max1Counter,TUESDAY);
		dif1 += (min1 != null && max1 != null) ? min1.difference(max1):0;
		compute(s2.getSections(), min1Counter, max2Counter,TUESDAY);
		dif2 += (min2 != null && max2 != null) ? min2.difference(max2):0;
		/**
		 *	Wednesday
		 **/
		// Reset values
		min1 = max1 = min2 = max2 = null;
		min1Counter = max1Counter = min2Counter = max2Counter = 0;
		// Compute and add differences, respectively
		compute(s1.getSections(), min1Counter, max1Counter,WEDNESDAY);
		dif1 += (min1 != null && max1 != null) ? min1.difference(max1):0;
		compute(s2.getSections(), min1Counter, max2Counter,WEDNESDAY);
		dif2 += (min2 != null && max2 != null) ? min2.difference(max2):0;
		/**
		 *	THURSDAY
		 **/
		// Reset values
		min1 = max1 = min2 = max2 = null;
		min1Counter = max1Counter = min2Counter = max2Counter = 0;
		// Compute and add differences, respectively
		compute(s1.getSections(), min1Counter, max1Counter,THURSDAY);
		dif1 += (min1 != null && max1 != null) ? min1.difference(max1):0;
		compute(s2.getSections(), min2Counter, max2Counter,THURSDAY);
		dif2 += (min2 != null && max2 != null) ? min2.difference(max2):0;
		/**
		 *	FRIDAY
		 **/
		// Reset values
		min1 = max1 = min2 = max2 = null;
		min1Counter = max1Counter = min2Counter = max2Counter = 0;
		// Compute and add differences, respectively
		compute(s1.getSections(), min1Counter, max1Counter,FRIDAY);
		dif1 += (min1 != null && max1 != null) ? min1.difference(max1):0;
		compute(s2.getSections(), min2Counter, max2Counter,FRIDAY);
		dif2 += (min2 != null && max2 != null) ? min2.difference(max2):0;

		// FINAL COMPARISON
		if (dif1 > dif2) return 1;
		else if (dif1 < dif2) return -1;
		else return 0;
	}
	
	/** COMPUTE (HELPER FUNCTION)
	 *	function:	Compares time spread between individual TimeSlots for two 
	 *				given times for a specific schedule
	 *	@params:	LinkedList<Sections>, 
	 *
	 **/
	private void compute(LinkedList<Section> sec, int minCounter, int maxCounter, char dayOfWeek){
		for (Section s : sec)
			for (TimeSlot t : s.getTimes()){
				if (t == null) continue;
				if (t.getDay() == dayOfWeek && minCounter == 0) {
					min1 = t;
					++minCounter;
				} else if (t.getDay() == dayOfWeek && maxCounter == 0) {
					max1 = t;
					++maxCounter;
				} else if (minCounter > 0 && t.getDay() == dayOfWeek && t.getStartMins() < min1.getStartMins()) {
					min1 = t;
					++minCounter;
				} else if (maxCounter > 0 && t.getDay() == dayOfWeek && max1.getEndMins() < t.getEndMins()) {
					max1 = t;
					++maxCounter;
				}
			}
		return;
	}
}