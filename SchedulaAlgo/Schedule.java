/**
 * By Jacob Perks
 *
 *	Schedule CLASS
 *
 */
package SchedulaAlgo;

import java.util.Collections;
import java.util.*;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Schedule {
	
	protected ArrayList<ArrayList<Integer>> schedules;
	protected ArrayList<Course> courses;
	protected ArrayList<Commitment> commits;
	
/**
 * Constructor for Schedule objects
 *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
 **/
	public Schedule(ArrayList<Commitment> com, ArrayList<Course> co){
		courses = co;
		commits = com;
		Collections.sort(courses, new CustomComparator());
		if (courses.get(0).sections.size() == 1) {
			BinaryTree b1 = new BinaryTree(courses.get(0).sections.get(0));
			Node x;
			for (int i = 0; i < courses.size()-1; i++) {
				for (int k = 0; k < courses.get(i).sections.size(); k++) {
					for (int j = 0; j < courses.get(i+1).sections.size(); j++) {
						x = b1.find(courses.get(i).sections.get(k), b1.root);
						x.addChild(courses.get(i+1).sections.get(j));
					}
				}
			}
			b1.root.print(1);
			System.out.println("SIZE OF BINARY TREE:\t"+ b1.size());
			schedules = new ArrayList<ArrayList<Integer>>();
			generate(b1.root);
			System.out.println(toString());
		}
	}
	
	private void generate(Node n){
		for (Course cc : courses){
		for (Course c : courses){
			if (c.equals(cc)) continue;
			for (Section s : c.sections){
			for (Section s1 : cc.sections){
				System.out.println(s.equals(s1));
				System.out.println(s.conflicts(s1));
				System.out.println(s.getCrn());
				System.out.println(s1.getCrn()+"\n___________________________________");
				if (s.equals(s1) || s.conflicts(s1)) continue;
				ArrayList<Integer> temp = new ArrayList<Integer>();
				System.out.println(s.getCrn());
				for (TimeSlot t : s.getTimes()) System.out.println(((t==null)? "":t));
				System.out.println(s1.getCrn());
				for (TimeSlot t1 : s1.getTimes())System.out.println(((t1==null)? "":t1));
				temp.add(s.getCrn());
				temp.add(s1.getCrn());
				if (schedules.isEmpty()) schedules.add(temp);
				else {
					boolean unique = false;
					for (ArrayList<Integer> ai : schedules){
						if (ai.contains(temp.get(0)) || ai.contains(temp.get(1))) {
							unique = false;
							break;
						} else 	unique = true;
					}
					if (unique) schedules.add(temp);
					else continue;
				}
			}
			}
		}
		}
	}

/**
 * toString() function returns String formatted print of Section 
 *	@params n/a
 *  @overwritable
 **/
	public String toString(){
		String crns = "";
		for (Course c : courses){
			for (Section s : c.sections){
				crns += s.getCrn() + "\n";
				if (s.getSubSecs() != null && !s.getSubSecs().isEmpty()){
					crns += "Add. Req.:\t[";
					for (SubSection ss : s.getSubSecs())
						crns += ss.getCrn() + " ";
					crns += "]\n";
				}
			}
		}
		return "INPUT COURSES:\n" + crns + "\nINPUT COMMITMENTs:"+ commits +"\nDIFFERENT OPTIONS:\n"+schedules;
	}

}