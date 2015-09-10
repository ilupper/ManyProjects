package com.ilupper.domain;

public class Person implements Comparable<Person> {

	public String first, last, id;

	@Override
	public boolean equals(Object o) {
		Person p = (Person)o;
		if (p.getId().equals(this.id))
			return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		return new Integer(this.id);
	}
	
	@Override
	public String toString() {
		return this.id + ": " + this.first + ", " + this.last;
	}   
	    
    @Override
    public int compareTo(Person secondPerson) {
        
        if (secondPerson.getLast().compareTo(this.getLast()) == 0) {
            if (secondPerson.getFirst().compareTo(this.getFirst()) == 0) {
                if (secondPerson.getId() == this.getId()) 
                    return 0;
                else if (secondPerson.getId() < this.getId()) 
                    return -1;
                else 
                    return 1;
            }
            else 
                return secondPerson.getFirst().compareTo(this.getFirst());    
        }
        else 
            return secondPerson.getLast().compareTo(this.getLast()); 
            
    }

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public Integer getId() {
		return new Integer(id);
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setId(Integer i) {
		this.id = i.toString();
	}
	
}
