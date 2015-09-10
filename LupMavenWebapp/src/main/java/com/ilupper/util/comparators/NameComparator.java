package com.ilupper.util.comparators;

import java.util.Comparator;

import com.ilupper.domain.Person;


public class NameComparator
    implements Comparator<Person> {
    @Override
    public int compare(Person arg0, Person secondPerson) {
        if (secondPerson.getLast().compareTo(arg0.getLast()) == 0) {
            if (secondPerson.getFirst().compareTo(arg0.getFirst()) == 0) {
                return 0;
            }
            else 
                return secondPerson.getFirst().compareTo(arg0.getFirst());    
        }
        else 
            return secondPerson.getLast().compareTo(arg0.getLast()); 
    }

}
