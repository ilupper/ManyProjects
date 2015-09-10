package com.ilupper.util.comparators;

import java.util.Comparator;

import com.ilupper.domain.Person;


public class IdComparator
    implements Comparator<Person> {

    public static void main(String[] args) {
    }

    @Override
    public int compare(Person arg0, Person secondPerson) {
        if (secondPerson.getId() == arg0.getId()) 
            return 0;
        else if (secondPerson.getId() < arg0.getId()) 
            return -1;
        else 
            return 1;
    }

}
