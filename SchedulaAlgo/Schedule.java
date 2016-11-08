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
	
	protected HashMap<Integer,HashMap<Section,SubSection>> schedules;
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
           
            b1.root.traverse(b1.root);
			generate(b1);
		}
	}
	
	private void generate(BinaryTree b){
		schedules = new HashMap<Integer, HashMap<Section,SubSection>>();
		int count = 0;
		if (b.root == null) return;
		for (Node n : b.root.getAllLeafNodes()){
			for(SubSection ss1 : n.getData().getSubSecs()){
				HashMap<Section,SubSection> posSchedg = new HashMap<Section,SubSection>();
				for(Node temp = n; temp != null; temp = temp.getParent()){
					for(SubSection ss2 : temp.getData().getSubSecs()){
						if (!ss1.conflicts(ss2)){
							posSchedg.put(n.getData(),ss1);
							posSchedg.put(temp.getData(),ss2);
						}
					}
				}
				if (!schedules.containsValue(posSchedg)){
					schedules.put(count, posSchedg);
					count += 1;
				}
			}
		}
		System.out.println(mapToString(schedules));
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
		return "INPUT COURSES:\n" + crns + "\nINPUT COMMITMENTs:"+ commits +"\nDIFFERENT OPTIONS:\n"+mapToString(schedules);
	}
	public static String mapToString(Map mp) {
		String s = "\n";
		Iterator it = mp.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			s += pair.getKey() + " = " + pair.getValue() + "\n";
			it.remove(); // avoids a ConcurrentModificationException
		}
		return s;
	}
}