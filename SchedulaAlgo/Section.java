package SchedulaAlgo;

import java.util.LinkedList;
import java.lang.*;

public class Section {
	
	private char name;
	private String prof;
	private int crn;
	private int startTimeMin;
	private int startTimeHour;
	private int endTimeMin; 
	private int endTimeHour;
	private LinkedList<Tutorial> tuts;
	
	/**
	 * Constructor for Section objects
	 *	@params n (A,B,C,...etc.), p ("name"), c (123456,...etc.), st ("14:35"),
	 *			et ("15:55"), t (Linked List = [TutorialA1,TutorialA2,...etc.])
	 **/	 
	public Section(char n, String p, int c, String st, String et, LinkedList<Tutorial> t){
		name = n;
		prof = p;
		crn = c;
		startTimeMin = Integer.parseInt(st.substring(3,5));
		startTimeHour = Integer.parseInt(st.substring(0,2));
		endTimeMin = Integer.parseInt(et.substring(3,5));
		endTimeHour = Integer.parseInt(et.substring(0,2));
		tuts = t;
	}
	
	public String toString(){ 
		return	"Section: "+name+"\nProf:"+prof+"\nTimes:"+
				startTimeHour+":" + startTimeMin+" - "+
				endTimeHour + ":" + endTimeMin+"\n"+
				tuts;
	}
								
	
	/**
	 * Calculate 
	 *
	 *
	 */
	
	public static void main(String[] args) {
		LinkedList<Section> s = new LinkedList<Section>();
		s.add(new Section('A', "John Smith", 231454, "11:35","12:55",new LinkedList<Tutorial>()));
		System.out.println(s.peek().toString());
		return;
	}
}