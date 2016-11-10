/**
 * By Daniel Fitzhenry and Jacob Perks
 *
 *	Commitment CLASS
 *
 */

package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

public class Commitment {

	protected String type;
	protected int term;
	protected SubSection times;
	
	/*
	Ctor
	Params:
	ty = name of commitment
	te = term of commitment 
	ti = time of commitment, including days of the week
	*/
	public Commitment(String ty, int te, String ti){
		type = ty;
		term = te;
		times = new SubSection(null, ty, 0,  te, ti);
	}


	/*
	Function: returns formatted string
	*/
	public String toString(){ 
		return times.toString();
	}

	// Getters
	public SubSection getTimes() { return times; }
	public String getType() { return type; }
	public int getTerm() { return term; }
}