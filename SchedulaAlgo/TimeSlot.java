/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	TimeSlot CLASS
 *
**/
package SchedulaAlgo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.String;

public class TimeSlot {
	private static final char MONDAY = 'M', TUESDAY = 'T', WEDNESDAY = 'W', THURSDAY = 'R', FRIDAY = 'F';
	private char day;
	private Calendar start;
	private Calendar end;
	private String term;

	/** CONSTRUCTOR
	 *	Function:	creates Gregorian Calendar TimeSlots
	 *	@params	c = course code
	 *			dayOfWeek = day of the week of the timeslot
	 *			startHour = start hour of the timeslot
	 *			startMinute = start minute of the timeslot
	 *			endHour = end hour of the timeslot
	 *			endMinute = end minute of the timeslot
	 *			termMonth = indicates the semester of the timeslot
	 *			termYear = year timeslot is in		
	 **/
	public TimeSlot(char dayOfWeek, int startHour, int startMinute, int endHour, int endMinute, int termMonth, int termYear) {
		day = dayOfWeek;
		int tm,dw,sd; tm=dw=sd=0;
		switch(dayOfWeek){
			default: break;
			case MONDAY: 	dw=2;	break;
			case TUESDAY:	dw=3;	break;
			case WEDNESDAY:	dw=4;	break;
			case THURSDAY:	dw=5;	break;
			case FRIDAY:	dw=6;	break;
		}
		switch(termMonth){
			default: break;
			case 10: tm=Calendar.JANUARY;	sd=9;	term="WINTER";	break;
			case 20: tm=Calendar.MAY;		sd=1;	term="SUMMER";	break; 
			case 30: tm=Calendar.SEPTEMBER;	sd=12;	term="FALL";	break; 
		}
		// Instantiate start time
		start = new GregorianCalendar(termYear, tm, sd+(dw-2), startHour,startMinute);
		start.add(Calendar.DAY_OF_MONTH, -1);
		start.set(Calendar.DAY_OF_WEEK, dw);
		//	Instantiate end time
		end = new GregorianCalendar(termYear, tm, sd+(dw-2), endHour,endMinute);
		end.add(Calendar.DAY_OF_MONTH, -1);
		end.set(Calendar.DAY_OF_WEEK, dw);
	}
	
	// Getters
	public char getDay()		{ return day; }
	public Calendar getStart()	{ return start; }
	public Calendar getEnd()	{ return end; }
	public int getStartMins()	{ return start.get(Calendar.HOUR_OF_DAY)*60 + start.get(Calendar.MINUTE); }
	public int getEndMins()		{ return end.get(Calendar.HOUR_OF_DAY)*60 + end.get(Calendar.MINUTE); }
	
	/** TO STRING
	 *	Function:	string formatted representation of this TimeSlot obj
	 *	@params	n/a
	 *	
	 **/
	public String toString(){
		String s = "\tTIMESLOT\nTerm:\t\t"+term+start.get(Calendar.YEAR)+"\nDay:\t\t";
		switch(start.get(Calendar.DAY_OF_WEEK)){
			default: break;
			case 2:	s+="MON\n";	break;
			case 3:	s+="TUE\n";	break;
			case 4:	s+="WED\n";	break;
			case 5:	s+="THU\n";	break;
			case 6:	s+="FRI\n";	break;
		}
		int sh = start.get(Calendar.HOUR_OF_DAY),
			sm = start.get(Calendar.MINUTE),
			eh = end.get(Calendar.HOUR_OF_DAY),
			em = end.get(Calendar.MINUTE);
		s += "Start-Time:\t"+((sh/10==0)?"0"+sh:sh)+":"+((sm/10==0)?"0"+sm:sm)+((start.get(Calendar.AM_PM)==0)?"AM":"PM")+"\n";
		s += "End-Time:\t"+((eh/10==0)?"0"+eh:eh)+":"+((em/10==0)?"0"+em:em)+((end.get(Calendar.AM_PM)==0)?"AM":"PM");
		return s+="\n";
	}

	/** CONFLICTS
	 *	Function:	compares two TimeSlots to see if they conflict
	 *	@params	t = TimeSlot to be compared with
	 **/
	public boolean conflicts(TimeSlot t) {
		if (this == null || t == null) return false;
		if (!term.equals(t.term)) return false;
		if (start.compareTo(t.start) == 0 || end.compareTo(t.end) == 0) return true;

		int	startMin = start.get(Calendar.HOUR_OF_DAY)*60 + start.get(Calendar.MINUTE),
			endMin = end.get(Calendar.HOUR_OF_DAY)*60 + end.get(Calendar.MINUTE),
			startMinT = t.start.get(Calendar.HOUR_OF_DAY)*60 + t.start.get(Calendar.MINUTE),
			endMinT = t.end.get(Calendar.HOUR_OF_DAY)*60 + t.end.get(Calendar.MINUTE);

		return	(start.get(Calendar.DAY_OF_WEEK) == t.start.get(Calendar.DAY_OF_WEEK)) &&
				((startMinT >= startMin && startMinT <= endMin) ||
				(endMinT >= startMin && endMinT <= endMin) ||
				(startMinT <= startMin && endMinT >= endMin));
	}

	/** PERIOD
	 *	Function:	returns string indicating period of the day, used for time of day preferences
	 *	@params	n/a
	 *	
	 **/
	public String period() {
		if (start.get(Calendar.HOUR_OF_DAY) < 12) {
			return "Morning";
		} else if (start.get(Calendar.HOUR_OF_DAY) < 17) {
			return "Afternoon";
		} else return "Evening";
	}

	/** DIFFERENCE
	 *	Function:	compares two TimeSlots and returns the difference between end and start time in minutes
	 *	@params	t = TimeSlot that will be compared with this
	 *
	 **/
	public int difference(TimeSlot t) {
		int difference, startMinutes, endMinutes; difference = startMinutes = endMinutes  = 0;
		if (conflicts(t) == true) return difference;
		else {
			if (start.get(Calendar.DAY_OF_WEEK) == t.start.get(Calendar.DAY_OF_WEEK)) {
				if (((start.get(Calendar.HOUR_OF_DAY)*60) + (start.get(Calendar.MINUTE))) >= ((t.end.get(Calendar.HOUR_OF_DAY)*60) + (t.end.get(Calendar.MINUTE)))) {
					startMinutes += start.get(Calendar.HOUR_OF_DAY) * 60;
					startMinutes += start.get(Calendar.MINUTE);
					endMinutes += t.end.get(Calendar.HOUR_OF_DAY) * 60;
					endMinutes += t.end.get(Calendar.MINUTE);
				} else {
					startMinutes += t.start.get(Calendar.HOUR_OF_DAY) * 60;
					startMinutes += t.start.get(Calendar.MINUTE);
					endMinutes += end.get(Calendar.HOUR_OF_DAY) * 60;
					endMinutes += end.get(Calendar.MINUTE);
				}
				difference = startMinutes - endMinutes;
			} else return -1;
		}
		return difference;
	}
}
