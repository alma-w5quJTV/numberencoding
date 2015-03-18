package com.almasoft.numberencoding;

import java.util.Comparator;

import com.almasoft.numberencoding.model.Phone;

public class PhoneComparator implements Comparator<Phone> {

    public int compare(Phone o1, Phone o2) {
        //TODO create without String
        //this 2.5 sec!
        return o1.asString().replaceAll("[-/]", "").compareTo(o2.asString().replaceAll("[-/]", ""));
    }

}
