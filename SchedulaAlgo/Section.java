/**
 *	SECTION CLASS
 *	By Daniel Fitzhenry + Jacob Perks (2016-10-27)
 **/
package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.String;

public class Section {

    protected String ID;
    protected String prof;
    protected int crn;
    protected TimeSlot[] times;
    public ArrayList<SubSection> subSec;

    /**
     * CONSTRUCTOR
     *	@params n (A,B,C,...),
     *			p ("name jonhson"),
     *			c (123456,345675,...),
     *			term (201710,201620,...),
     *			time ("W 1405-1555","MW 1405-1555","MWF 1405-1555",...) [MAX 3] else null,
     *			s (LinkedList = [SubSection1,SubSection2,...])
     **/
    public Section(String n, String p, int c, int term, String time, ArrayList<SubSection> s){
        int startHour, startMinute, endHour, endMinute;
        int year = (int)(term/100);
        int semester = term - year*100;
        ID = n;
        prof = p;
        crn = c;
        subSec = s;
        times = new TimeSlot[3];
        if (time.length() == 11){
			/* W 1405-1555 */
            startHour = Integer.parseInt(time.substring(2,4));
            startMinute = Integer.parseInt(time.substring(4,6));
            endHour = Integer.parseInt(time.substring(7,9));
            endMinute = Integer.parseInt(time.substring(9));
            times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
            times[1] = times[2] = null;
        } else if (time.length() == 12){
			/* TR 1405-1555 */
            startHour = Integer.parseInt(time.substring(3,5));
            startMinute = Integer.parseInt(time.substring(5,7));
            endHour = Integer.parseInt(time.substring(8,10));
            endMinute = Integer.parseInt(time.substring(10));
            times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
            times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
            times[2] = null;
        } else if (time.length() == 13){
			/* MWF 1405-1555 */
            startHour = Integer.parseInt(time.substring(4,6));
            startMinute = Integer.parseInt(time.substring(6,8));
            endHour = Integer.parseInt(time.substring(9,11));
            endMinute = Integer.parseInt(time.substring(11));
            times[0] = new TimeSlot(time.charAt(0),startHour,startMinute,endHour,endMinute,semester,year);
            times[1] = new TimeSlot(time.charAt(1),startHour,startMinute,endHour,endMinute,semester,year);
            times[2] = new TimeSlot(time.charAt(2),startHour,startMinute,endHour,endMinute,semester,year);
        } else times[0] = times[1] = times[2] = null;
    }

    /**
     * toString() function returns String formatted print of Section
     *	@params n/a
     *  @overwritable
     **/
    public String toString(){
        String s = "Section: "+ID+"\nProf:"+prof+"\n";
        for(TimeSlot t : times) if (t != null) s += t+"\n";
        s += "\nAdditional Requirements: \n"+subSec;
        return s;
    }
	
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Section))return false;
        Section otherSec = (Section) other;
        return this.crn == otherSec.crn;
    }
	
	public boolean conflicts(Section s) {
		boolean b = false;
		if (this == null || s == null) return b;
		for(TimeSlot t1 : times){
			if (t1 == null) continue;
			for (TimeSlot t2 : s.times){
				if (t2 == null) continue;
				if (t1.conflicts(t2) || t2.conflicts(t1)) b = true;
			}
		}
		return b;
	}
    /**
     * GETTERS
     * @params n/a
     **/
    public String getID() { return ID;}
    public String getProf() { return prof;}
    public int getCrn() { return crn;}
    public TimeSlot[] getTimes() { return times;}
    public ArrayList<SubSection> getSubSecs() { return subSec;}
    
    public void addSubSection(SubSection subSection) {
        subSec.add(subSection);
    }
}
