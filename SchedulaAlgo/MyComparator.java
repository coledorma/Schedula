/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *  Custom Comparator CLASS (MAIN)
 *
**/

package SchedulaAlgo;
import java.util.*;

public class MyComparator implements Comparator<Schedule>{

	public MyComparator() {
		
	}

	@Override 
	public int compare(Schedule s1, Schedule s2) {
		int dif1 = 0;
		int dif2 = 0;
		int min1Counter = 0;
		int max1Counter = 0;
		int min2Counter = 0;
		int max2Counter = 0;
		TimeSlot min1 = null;
		TimeSlot max1 = null;
		TimeSlot min2 = null;
		TimeSlot max2 = null;

		////////////////////////
		// MONDAY COMPARISONS //
		////////////////////////

		for (Section s : s1.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'M' && min1Counter == 0) {
						min1 = t;
						min1Counter++;
					} else if (t.getDay() == 'M' && max1Counter == 0) {
						max1 = t;
						max1Counter++;
					} else if (min1Counter > 0 && t.getDay() == 'M' && t.getStartMins() < min1.getStartMins()) {
						min1 = t;
						min1Counter++;
					} else if (max1Counter > 0 && t.getDay() == 'M' && max1.getEndMins() < t.getEndMins()) {
						max1 = t;
						max1Counter++;
					}
				}	
			}
		}

		if (min1 != null && max1 != null) {
			dif1 += min1.difference(max1);
		}

		for (Section s : s2.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'M' && min2Counter == 0) {
						min2 = t;
						min2Counter++;
					} else if (t.getDay() == 'M' && max2Counter == 0) {
						max2 = t;
						max2Counter++;
					} else if (min2Counter > 0 && t.getDay() == 'M' && t.getStartMins() < min2.getStartMins()) {
						min2 = t;
						min2Counter++;
					} else if (max2Counter > 0 && t.getDay() == 'M' && max2.getEndMins() < t.getEndMins()) {
						max2 = t;
						max2Counter++;
					}
				}
			}
		}

		if (min2 != null && max2 != null) {
			dif2 += min2.difference(max2);
		}

		min1 = null;
		max1 = null;
		min2 = null;
		max2 = null;
		min1Counter = 0;
		max1Counter = 0;
		min2Counter = 0;
		max2Counter = 0;

		/////////////////////////
		// TUESDAY COMPARISONS //
		/////////////////////////

		for (Section s : s1.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'T' && min1Counter == 0) {
						min1 = t;
						min1Counter++;
					} else if (t.getDay() == 'T' && max1Counter == 0) {
						max1 = t;
						max1Counter++;
					} else if (min1Counter > 0 && t.getDay() == 'T' && t.getStartMins() < min1.getStartMins()) {
						min1 = t;
						min1Counter++;
					} else if (max1Counter > 0 && t.getDay() == 'T' && max1.getEndMins() < t.getEndMins()) {
						max1 = t;
						max1Counter++;
					}
				}	
			}
		}

		if (min1 != null && max1 != null) {
			dif1 += min1.difference(max1);
		}

		for (Section s : s2.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'T' && min2Counter == 0) {
						min2 = t;
						min2Counter++;
					} else if (t.getDay() == 'T' && max2Counter == 0) {
						max2 = t;
						max2Counter++;
					} else if (min2Counter > 0 && t.getDay() == 'T' && t.getStartMins() < min2.getStartMins()) {
						min2 = t;
						min2Counter++;
					} else if (max2Counter > 0 && t.getDay() == 'T' && max2.getEndMins() < t.getEndMins()) {
						max2 = t;
						max2Counter++;
					}
				}
			}
		}

		if (min2 != null && max2 != null) {
			dif2 += min2.difference(max2);
		}

		min1 = null;
		max1 = null;
		min2 = null;
		max2 = null;
		min1Counter = 0;
		max1Counter = 0;
		min2Counter = 0;
		max2Counter = 0;

		/////////////////////////
		// WEDNESDAY COMPARISONS //
		/////////////////////////

		for (Section s : s1.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'W' && min1Counter == 0) {
						min1 = t;
						min1Counter++;
					} else if (t.getDay() == 'W' && max1Counter == 0) {
						max1 = t;
						max1Counter++;
					} else if (min1Counter > 0 && t.getDay() == 'W' && t.getStartMins() < min1.getStartMins()) {
						min1 = t;
						min1Counter++;
					} else if (max1Counter > 0 && t.getDay() == 'W' && max1.getEndMins() < t.getEndMins()) {
						max1 = t;
						max1Counter++;
					}
				}	
			}
		}

		if (min1 != null && max1 != null) {
			dif1 += min1.difference(max1);
		}

		for (Section s : s2.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'W' && min2Counter == 0) {
						min2 = t;
						min2Counter++;
					} else if (t.getDay() == 'W' && max2Counter == 0) {
						max2 = t;
						max2Counter++;
					} else if (min2Counter > 0 && t.getDay() == 'W' && t.getStartMins() < min2.getStartMins()) {
						min2 = t;
						min2Counter++;
					} else if (max2Counter > 0 && t.getDay() == 'W' && max2.getEndMins() < t.getEndMins()) {
						max2 = t;
						max2Counter++;
					}
				}
			}
		}

		if (min2 != null && max2 != null) {
			dif2 += min2.difference(max2);
		}

		min1 = null;
		max1 = null;
		min2 = null;
		max2 = null;
		min1Counter = 0;
		max1Counter = 0;
		min2Counter = 0;
		max2Counter = 0;

		/////////////////////////
		// THURSDAY COMPARISONS //
		/////////////////////////

		for (Section s : s1.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'R' && min1Counter == 0) {
						min1 = t;
						min1Counter++;
					} else if (t.getDay() == 'R' && max1Counter == 0) {
						max1 = t;
						max1Counter++;
					} else if (min1Counter > 0 && t.getDay() == 'R' && t.getStartMins() < min1.getStartMins()) {
						min1 = t;
						min1Counter++;
					} else if (max1Counter > 0 && t.getDay() == 'R' && max1.getEndMins() < t.getEndMins()) {
						max1 = t;
						max1Counter++;
					}
				}	
			}
		}

		if (min1 != null && max1 != null) {
			dif1 += min1.difference(max1);
		}

		for (Section s : s2.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'R' && min2Counter == 0) {
						min2 = t;
						min2Counter++;
					} else if (t.getDay() == 'R' && max2Counter == 0) {
						max2 = t;
						max2Counter++;
					} else if (min2Counter > 0 && t.getDay() == 'R' && t.getStartMins() < min2.getStartMins()) {
						min2 = t;
						min2Counter++;
					} else if (max2Counter > 0 && t.getDay() == 'R' && max2.getEndMins() < t.getEndMins()) {
						max2 = t;
						max2Counter++;
					}
				}
			}
		}

		if (min2 != null && max2 != null) {
			dif2 += min2.difference(max2);
		}

		min1 = null;
		max1 = null;
		min2 = null;
		max2 = null;
		min1Counter = 0;
		max1Counter = 0;
		min2Counter = 0;
		max2Counter = 0;

		/////////////////////////
		// FRIDAY COMPARISONS //
		/////////////////////////

		for (Section s : s1.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'F' && min1Counter == 0) {
						min1 = t;
						min1Counter++;
					} else if (t.getDay() == 'F' && max1Counter == 0) {
						max1 = t;
						max1Counter++;
					} else if (min1Counter > 0 && t.getDay() == 'F' && t.getStartMins() < min1.getStartMins()) {
						min1 = t;
						min1Counter++;
					} else if (max1Counter > 0 && t.getDay() == 'F' && max1.getEndMins() < t.getEndMins()) {
						max1 = t;
						max1Counter++;
					}
				}	
			}
		}

		if (min1 != null && max1 != null) {
			dif1 += min1.difference(max1);
		}

		for (Section s : s2.getSections()) {
			for (TimeSlot t : s.getTimes()) {
				if (t != null) {
					if (t.getDay() == 'F' && min2Counter == 0) {
						min2 = t;
						min2Counter++;
					} else if (t.getDay() == 'F' && max2Counter == 0) {
						max2 = t;
						max2Counter++;
					} else if (min2Counter > 0 && t.getDay() == 'F' && t.getStartMins() < min2.getStartMins()) {
						min2 = t;
						min2Counter++;
					} else if (max2Counter > 0 && t.getDay() == 'F' && max2.getEndMins() < t.getEndMins()) {
						max2 = t;
						max2Counter++;
					}
				}
			}
		}

		if (min2 != null && max2 != null) {
			dif2 += min2.difference(max2);
		}

		if (dif1 > dif2) {
			return 1;
		} else if (dif1 < dif2) {
			return -1;
		} else {
			return 0;
		}
	}
}