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
	 *	@params 	dayOfWeek(M,T,W,R,F)~{WEEK\WEEKEND},
	 *				startHour/endHour(0-23)~{MOD 24},
	 *				startMinute/endMinute(0-59)~{MOD 60},
	 *				termMonth(10,20,30)~{WNTR(Jan-May),SUMR(Jun-Aug),FALL(Sep-Dec)},
	 *				termYear(0-9999)~{MOD 10000}
	 *	example:	...new TimeSlot('M',11,35,12,55,10,2016)...
	 */
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
		s += "Start-Time:\t"+start.get(Calendar.HOUR_OF_DAY)+":"+start.get(Calendar.MINUTE)+((start.get(Calendar.AM_PM)==0)?"AM":"PM")+"\n";
		s += "End-Time:\t"+end.get(Calendar.HOUR_OF_DAY)+":"+end.get(Calendar.MINUTE)+((end.get(Calendar.AM_PM)==0)?"AM":"PM");
		return s;
	}
	
	public boolean conflicts(TimeSlot t) {
		if ((start.equals(t.start)) || (end.equals(t.end)) || !term.equals(t.term)) return false;
		return	(start.get(Calendar.DAY_OF_WEEK) == t.start.get(Calendar.DAY_OF_WEEK)) &&
				((start.get(Calendar.HOUR_OF_DAY) <= t.start.get(Calendar.HOUR_OF_DAY) && t.start.get(Calendar.HOUR_OF_DAY) <= end.get(Calendar.HOUR_OF_DAY)) ||
				(start.get(Calendar.HOUR_OF_DAY) <= t.end.get(Calendar.HOUR_OF_DAY) && t.end.get(Calendar.HOUR_OF_DAY) <= end.get(Calendar.HOUR_OF_DAY))) &&
				((start.get(Calendar.MINUTE) <= t.start.get(Calendar.MINUTE) && t.start.get(Calendar.MINUTE) <= end.get(Calendar.MINUTE)) ||
				(start.get(Calendar.MINUTE) <= t.end.get(Calendar.MINUTE) && t.end.get(Calendar.MINUTE) <= end.get(Calendar.MINUTE)));
	}
}