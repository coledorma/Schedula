/**
 * By Daniel Fitzhenry (14-10-2016)
 *
 *	TUTORIAL CLASS (SECTION SUBCLASS)
 *
 */
package SchedulaAlgo;

//TODO: Figure out other useful functions/parameters

public final class Tutorial extends Section{

/**
 * Constructor for Tutorial objects
 *	@params s (A,B,C,...etc.), c (123456,...etc.), st ("14:35"), et ("15:55")
 **/
	public Tutorial(char s, int c, String st, String et){
		super(s,"T.A",c,st,et,null);
	}
	
/**
 * toString() function returns String formatted print of Tutorial 
 *	@params n/a
 *  @overwritten
 **/
	public final String toString(){ 
		return	name+": "+"Times:"+
				String.format("%02d", startTimeHour)+":"+String.format("%02d", startTimeMin)+" - "+
				String.format("%02d", endTimeHour)+ ":"+String.format("%02d", endTimeMin);
	}

}
