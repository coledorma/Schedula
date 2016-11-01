/**
 *	TimeSlot Class
 *	Made to  calculate optimal time al
 *	By Daniel Fitzhenry (14-10-2016)
 */
package SchedulaAlgo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.lang.String;

public class TimeSlot {
	private static final char MONDAY = 'M', TUESDAY = 'T', WEDNESDAY = 'W', THURSDAY = 'R', FRIDAY = 'F';
	private Calendar start;
	private Calendar end;
	private String term;

/**
 *	CONSTRUCTOR
 *	@params	dayOfWeek(M,T,W,R,F)~{WEEK\WEEKEND},
 *			startHour/endHour(0-23)~{MOD 24},
 *			startMinute/endMinute(0-59)~{MOD 60},
 *			termMonth(10,20,30)~{WNTR(Jan-May),SUMR(Jun-Aug),FALL(Sep-Dec)},
 *			termYear(0-9999)~{MOD 10000}
 *	@xmpl	new TimeSlot('M',11,35,12,55,10,2016)
 **/
    public TimeSlot(char dayOfWeek, int startHour, int startMinute, int endHour, int endMinute, int termMonth, int termYear) {
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
			case 10: tm=Calendar.JANUARY;	sd=9;	term="WINTER";	break; //<<<<<< sd: Will need to be updated in future!
			case 20: tm=Calendar.MAY;		sd=1;	term="SUMMER";	break; //<<<<<< sd: Will need to be updated in future! (START DATE)
			case 30: tm=Calendar.SEPTEMBER;	sd=12;	term="FALL";	break; //<<<<<< sd: Will need to be updated in future!
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
/**
 * toString() function returns String formatted print of TimeSlot
 *	@params n/a
 *  @overwritable
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
		return s;
	}
/**
 * CONFLICTS FUNCTION
 * Returns false if this timeslot is compatible with the given timeslot
 * @params t (TimeSlotID)
 **/
	public boolean conflicts(TimeSlot t) {
		if (this == null || t == null) return false;
		if (start.equals(t.start) || end.equals(t.end) || !term.equals(t.term)) return false;
		return	(start.get(Calendar.DAY_OF_WEEK) == t.start.get(Calendar.DAY_OF_WEEK)) &&
				((start.get(Calendar.HOUR_OF_DAY) <= t.start.get(Calendar.HOUR_OF_DAY) && 
				t.start.get(Calendar.HOUR_OF_DAY) <= end.get(Calendar.HOUR_OF_DAY)) ||
				(start.get(Calendar.HOUR_OF_DAY) <= t.end.get(Calendar.HOUR_OF_DAY) &&
				t.end.get(Calendar.HOUR_OF_DAY) <= end.get(Calendar.HOUR_OF_DAY))) &&
				((start.get(Calendar.MINUTE) <= t.start.get(Calendar.MINUTE) && 
				t.start.get(Calendar.MINUTE) <= end.get(Calendar.MINUTE)) ||
				(start.get(Calendar.MINUTE) <= t.end.get(Calendar.MINUTE) &&
				t.end.get(Calendar.MINUTE) <= end.get(Calendar.MINUTE)));
	}

/**
 * PERIOD FUNCTION
 * Function that returns a string to determine whether it is a morning, afternoon, or evening class
 * @params n/a
 **/
	public String period() {
		if (start.get(Calendar.HOUR_OF_DAY) < 12) {
			return "Morning";
		} else if (start.get(Calendar.HOUR_OF_DAY) < 16) {
			return "Afternoon";
		} else return "Evening";
	}

/**
 * DIFFERENCE FUNCTION
 * Function that returns an integer (3.45 == 3 hours and 45 minutes) corresponding to the difference in end and start time between two timeslots, 
 * This will allow us to determine whether a timeslot is "better" (or closer to another timeslot),
 * or "worse" (or further away from another timeslot)
 **/
	public int difference(TimeSlot t) {
		int difference = 0;
		if (conflicts(t) == true) {
			return difference;
		} else {
			if (start.get(Calendar.DAY_OF_WEEK) == t.start.get(Calendar.DAY_OF_WEEK)) {
				if ((start.get(Calendar.HOUR_OF_DAY) >= t.end.get(Calendar.HOUR_OF_DAY)) && (start.get(Calendar.MINUTE) > t.end.get(Calendar.MINUTE))) {
					difference += start.get(Calendar.HOUR_OF_DAY) - t.end.get(Calendar.HOUR_OF_DAY);
					difference += (start.get(Calendar.MINUTE) - t.end.get(Calendar.MINUTE)) / 100;
				} else {
					difference += t.start.get(Calendar.HOUR_OF_DAY) - end.get(Calendar.HOUR_OF_DAY);
					difference += (t.start.get(Calendar.MINUTE) - end.get(Calendar.MINUTE)) / 100;
				}
			} else {
				return difference;
			}
		}
		return difference;
	}
}