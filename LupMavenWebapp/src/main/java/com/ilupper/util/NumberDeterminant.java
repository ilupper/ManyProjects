package com.ilupper.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TypeDeterminant {
    
    /**
     * Has to check for java.time.DateTimeParseException
     * 
     * @param dateInString
     * @return
     */
    public static LocalDate convertToLocalDate(String dateInString) {
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        try {
            return LocalDate.parse(dateInString, formatter);
        } 
        catch (DateTimeException e) {return null;}
        
    }
    
    public static boolean isInteger(String possibleNum) {
        try {
            new Integer(possibleNum);
            return true;
        }
        catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    public static Integer convertToInteger(String numInString) {
        try {
            return new Integer(numInString);
        }
        catch (NumberFormatException nfe) {
            return 0;
        }
    }
}
