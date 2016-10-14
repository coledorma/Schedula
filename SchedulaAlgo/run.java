/**
 * By Daniel Fitzhenry (14-10-2016)
 *
 *	TEST RUN CLASS
 *
 */
package SchedulaAlgo;

import java.util.*;

public final class run{
	public static void main(String[] args) {
		LinkedList<Section> s = new LinkedList<Section>();
		LinkedList<Tutorial> t = new LinkedList<Tutorial>();
		
		t.add(new Tutorial('A',223443,"14:05","15:55"));	
		s.add(new Section('A', "John Smith", 231454, "11:35","12:55",t));
		
		System.out.println(s.peek());
		return;
	}
}