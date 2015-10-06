package com.ilupper.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.ilupper.domain.Person;


public class PersonTest {

    @Test
    public void testPersonInEquality() {
        Person p1 = new Person();
        p1.setId(12345);
        Person p2 = new Person();
        p2.setId(98765);
        assertFalse(p1.equals(p2));
    }
    
    //@Test
    public void testPersonEquality() {
        Person p1 = new Person();
        p1.setId(12345);
        Person p2 = new Person();
        p2.setId(12345);
        assertTrue(p1.equals(p2));
    }
    
    @Test
    public void testPersonHash() {
        Person p1 = new Person();
        p1.setId(12345);
        int returnedHash = p1.hashCode();
        assertEquals(12345, returnedHash);
    }
    
    @Test
    public void testPersonToString() {
        Person p1 = new Person();
        p1.setId(12345);
        p1.setFirst("Lup");
        p1.setLast("Ma");
        //assertEquals("12345: Ma, Lup", p1.toString());
        assertEquals("12345: Lup, Ma", p1.toString());
    }
    
}
