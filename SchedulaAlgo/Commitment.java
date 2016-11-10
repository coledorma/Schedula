/**
 * By Jacob Perks & Daniel Fitzhenry
 *
 *	Commitment CLASS
 *
 */
package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Commitment {

	protected String type;
	protected int term;
	protected SubSection times;
	
/**
 * Constructor for Commitment objects
 *	@params ty ("Work","Sports",etc...)
 *			te (201610,201720,etc...)
 *			ti (("W 1405-1555","MW 1405-1555","MWF 1405-1555",...) [MAX 3] else null
 **/
	public Commitment(String ty, int te, String ti){
		type = ty;
		term = te;
		times = new SubSection(null, ty, 0,  te, ti);
	}


/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){ 
		return times.toString();
	}

	public SubSection getTimes() { return times; }
}