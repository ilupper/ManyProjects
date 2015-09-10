package com.ilupper.restful;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
 
public class EntryPoint extends Application
{
    private Set<Object> singletons = new HashSet();
    private Set<Class<?>> empty = new HashSet();
 
    public EntryPoint() {
        // ADD YOUR RESTFUL RESOURCES HERE
        this.singletons.add(new SimpleService());
    }
 
    public Set<Class<?>> getClasses()
    {
        return this.empty;
    }
 
    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}