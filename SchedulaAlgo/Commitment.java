/**
 *	Authors: Daniel Fitzhenry and Jacob Perks
 *	ALACRITYDEVELOPMENT©
 *	Commitment CLASS
 *
**/
package SchedulaAlgo;

public class Commitment {
	protected String type;
	protected int term;
	protected SubSection times;
	
	/** CONSTRUCTOR
	 *	Function:	Construct a Commitment obj
	 *	@params	ty = name of commitment
	 *			s = array list of sections of particular course
	 *			te = term of commitment 
	 *			ti = time of commitment, including days of the week
	 **/
	public Commitment(String ty, int te, String ti){
		type = ty;
		term = te;
		times = new SubSection(null, ty, 0,  te, ti);
	}
	
	// Getters
	public SubSection getTimes()	{ return times; }
	public String getType()			{ return type; }
	public int getTerm()			{ return term; }

	/** TO STRING
	 *	Function:	string formatted representation of this Commitment obj
	 *	@params	n/a
	 *	
	 **/
	public String toString(){ return times.toString(); }
}
