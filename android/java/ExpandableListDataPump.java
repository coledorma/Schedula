package com.example.coop.schedula;

/**
 * Created by ColeDorma on 2016-10-24.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> preferredTime = new ArrayList<String>();
        preferredTime.add("Morning (8:00 AM to 11:59 AM)");
        preferredTime.add("Afternoon (12:00 PM to 3:59 PM)");
        preferredTime.add("Evening (4:00 PM to 9:00 PM)");

        expandableListDetail.put("Preferred Time Of Day", preferredTime);

        return expandableListDetail;
    }
}