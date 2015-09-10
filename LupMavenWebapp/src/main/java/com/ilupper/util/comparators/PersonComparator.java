package com.ilupper.util.comparators;

import java.util.Comparator;

import com.ilupper.domain.Person;


public class PersonComparator
    implements Comparator<Person> {

    public static void main(String[] args) {
    }

    @Override
    public int compare(Person arg0, Person secondPerson) {
        
        if (secondPerson.getLast().compareTo(arg0.getLast()) == 0) {
            if (secondPerson.getFirst().compareTo(arg0.getFirst()) == 0) {
                if (secondPerson.getId() == arg0.getId()) 
                    return 0;
                else if (secondPerson.getId() < arg0.getId()) 
                    return -1;
                else 
                    return 1;
            }
            else 
                return secondPerson.getFirst().compareTo(arg0.getFirst());    
        }
        else 
            return secondPerson.getLast().compareTo(arg0.getLast()); 
    }

}
