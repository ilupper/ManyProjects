package com.ilupper.experiment;

import java.util.function.Function;

import com.ilupper.domain.Person;


public class PersonFunction<T, R>
    implements Function<T, R> {

    public static void main(String[] args) {
    }

    @Override
    public R apply(T t) {

        Person person = (Person)t;
        //String lastName = person.getLastName();
        String firstName = person.getFirst();
        
        //@SuppressWarnings("unchecked")
        //R r = (R)lastName;
        @SuppressWarnings("unchecked")
        R r = (R)firstName;
        
        return r;
    }

}
