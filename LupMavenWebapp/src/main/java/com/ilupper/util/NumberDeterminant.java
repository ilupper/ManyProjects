package com.ilupper.util;


public class TypeDeterminant {
    
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
