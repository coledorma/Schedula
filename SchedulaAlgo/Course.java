/**
 * By Daniel Fitzhenry and Jacob Perks
 *
 *  Course CLASS
 *
 */

package SchedulaAlgo;

import java.util.ArrayList;
import java.lang.*;

public class Course {

    public String code;
    public ArrayList<Section> sections;

    /*
    Ctor
    Params:
    c = course code
    s = array list of sections of particular course
    */
    public Course(String c, ArrayList<Section> s){
        code = c;
        sections = s;
    }

    /*
    Function: returns formatted string
    */
    public String toString(){
        return "Course Code:" + code + "\n" + sections;
    }

    @Override
    /*
    Function: compares two courses to see if they are the same object
    Params:
    other = Object to be comapred with this
    */
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Course))return false;
        Course otherCourse = (Course) other;
        return this.code.equals(otherCourse.code);
    }

}
