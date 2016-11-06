/**
 * By Jacob Perks
 *
 *	Course CLASS
 *
 */
package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

//TODO: Figure out other useful functions/parameters

public class Course {

    public String code;
    public ArrayList<Section> sections;

    /**
     * Constructor for Schedule objects
     *	@params  s (ArrayList = [SectionA,SectionB,...etc.])
     **/
    public Course(String c, ArrayList<Section> s){
        code = c;
        sections = s;
    }

    /**
     * toString() function returns String formatted print of Section
     *	@params n/a
     *  @overwritable
     **/
    public String toString(){
        return "Course Code:" + code + "\n" + "\n" + sections;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Course))return false;
        Course otherCourse = (Course) other;
        return this.code.equals(otherCourse.code);
    }

}
