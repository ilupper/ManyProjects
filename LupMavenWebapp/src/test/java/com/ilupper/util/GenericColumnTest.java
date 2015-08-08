package com.ilupper.util;

public class GenericColumnTest {
        
    //both: content failures
    
        //empty line
    
        //empty space test (look below)
    
        //csv specific
            //bad # of tokens in 1 line
            
            //incorrect designation of delimiter
            
    
    //specific: ready by line content failure 
        //carriage return
    
        //new line
    
        //empty last line
    
        //empty beginning line
    
    //TODO: generalized content test for trailing spaces

    //TODO: generalized break from grid structure (from empty line)
    //TODO: generalized break from grid structure (by missing delimiter)
        //a) if over token
    
        //b) if under token
    
        //c) both under & over token: break from pattern of data
        //*this is more of a data remediation task (in effort to save some data in case multiple rows
            //exhibit malform qualities (more of a structural concept)
    
            //i) type (qa) - pre-advance knowledge of schema
            //ii) length (qty)
            //iii) by pattern (like combo of numeric and string)
            //iv) regex pattern?
            //v) by plurality of pattern (majority)
    //TODO: generalized break from grid structure should be just general failure

}
