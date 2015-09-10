package com.ilupper.util.grid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ilupper.domain.Passenger;
import com.ilupper.domain.Person;
import com.ilupper.experiment.PersonFunction;
import com.ilupper.util.TypeDeterminant;
import com.ilupper.util.comparators.IdComparator;

public class GenericColumn {

	ArrayList<String> schema = new ArrayList<String>(), list;
	ArrayList<Object> objectList = new ArrayList<Object>();

	HashSet<Object> uniqueItems = new HashSet<Object>();
    Logger log = LoggerFactory.getLogger(this.getClass());
    
    public GenericColumn(String filename, String dtoName) {

        this.setFileInfo(filename);

        // the schema
        try {
            this.setUpColumns(br.readLine());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //the model
        Class<?> objectClass = null;
        try {
            objectClass = Class.forName(dtoName);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        
        // the content
        try {
            String line = null;
            do {
                line = br.readLine();
                if (line != null)
                    this.matchDataToColumns(line, objectClass);
            } while (line != null);

            this.closeFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          
    }

	public void populateDAO(ArrayList<String> data, Object obj) {
	
		//attempt to use reflection on Person to automate fields
		Class<?> genericClass = obj.getClass();
		Field[] fieldList = genericClass.getFields();
		Method[] methodList = genericClass.getMethods();
		
		for (int i=0; i < data.size(); i++) {
			String match = schema.get(i);
			for (Field f: fieldList) {
				if (match.equalsIgnoreCase(f.getName()))
					for (Method m: methodList) {
						//lc to compare
						String tempField = f.getName();
						tempField = tempField.toLowerCase();
						String methodField = m.getName();
						methodField = methodField.toLowerCase();
						//loop thru methods
						int indexSetVerbiage = 3;
						if (methodField.startsWith( tempField, indexSetVerbiage ) && methodField.startsWith("set"))
							try {
								//debug
								log.debug("Method found and executing.. " + m.getName());
								log.debug("Field found and executing.. " + f.getName());
								
								LocalDate ld = null;Double dbl = null;char character = '?';
								String holding = data.get(i);
								if (TypeDeterminant.isInteger(holding))
								    m.invoke(obj, TypeDeterminant.convertToInteger(holding));
								else if ( (ld = TypeDeterminant.convertToLocalDate(holding)) != null) {
								    m.invoke(obj, ld);
								}
								else if ( (dbl = TypeDeterminant.convertToDouble(holding)) != null) {
									m.invoke(obj, dbl);
								}
								else if ( (character = TypeDeterminant.convertToChar(holding)) != '?') {
									m.invoke(obj, character);
								}
								else
								    m.invoke(obj, holding);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
					}
			}
		}
		
	}

	public void setUpColumns(String heading) {

		StringTokenizer st = new StringTokenizer(heading, ",");
		while (st.hasMoreTokens())
			schema.add(st.nextToken().trim());
	}

	FileReader fr = null;
	BufferedReader br;

	public void matchDataToColumns(String row, Class<?> objectClass) {

		list = new ArrayList<String>();

		StringTokenizer st = new StringTokenizer(row, ",");
		while (st.hasMoreTokens())
			this.list.add(st.nextToken().trim());

		Object object = null;
		try {
			object = objectClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		populateDAO(list, object);
		objectList.add(object);
	}

	public void setFileInfo(String filename) {
		File file = new File(filename);

		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		br = new BufferedReader(fr);
	}

	public void closeFile() {
		try {
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    
    public HashSet<Object> getUniqueItems() {
    
        for (Object obj: objectList) {
            uniqueItems.add(obj);
        }
        return uniqueItems;
    }

    
    public void setUniqueItems(HashSet<Object> hs) {
    
        this.uniqueItems = hs;
    }

    public void setSortedItems(TreeSet<Object> treeSet) {
        
        this.sortedItems = treeSet;
    }
    
    public TreeSet<Object> getSortedItems() {
        
        this.sortData(uniqueItems);
        return sortedItems;
    }

    TreeSet<Object> sortedItems = null;
    public void sortData(HashSet<Object> hashSet) {
        sortedItems = new TreeSet<Object>(hashSet);       
    }
    
    public TreeSet<Object> getSortedItemsComparator(Comparator<? extends Object> comparator) {
        
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator2 = (Comparator<Object>)comparator;
        
        sortedItems = new TreeSet<Object>(comparator2);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }

    public TreeSet<Object> getSortedItemsReversed(Comparator<? extends Object> comparator) {
        
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator2 = (Comparator<Object>)comparator;
        Comparator<Object> reversed = comparator2.reversed();
        
        sortedItems = new TreeSet<Object>(reversed);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }

    public TreeSet<Object> getSortedItemsMulti(Comparator<? extends Object> comparator) {
        
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator2 = (Comparator<Object>)comparator;
        Comparator<Object> reversed = comparator2.reversed();
        
        Comparator<? extends Object> temp = new IdComparator();
        @SuppressWarnings("unchecked")
        Comparator<Object> pidc = (Comparator<Object>)temp;
        Comparator<Object> multi = reversed.thenComparing( pidc );
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
    
    public TreeSet<Object> getSortedItemsById() {
        
        Comparator<? extends Object> temp = new IdComparator();
        @SuppressWarnings("unchecked")
		Comparator<Object> multi = (Comparator<Object>)temp;
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
    
    public TreeSet<Object> getSortedItemsViaFunction() {
        
        //testing functions in java 8
        @SuppressWarnings({"unchecked", "rawtypes"})
        Comparator<Object> multi = Comparator.comparing(new PersonFunction()); //probably not how it's done
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }

    public TreeSet<Object> getSortedItemsViaMethodRef() {
        
        //testing functions in java 8
        Comparator<? extends Object> temp = null;
        temp = Comparator.comparing(Person::getFirst);
        @SuppressWarnings("unchecked")
        Comparator<Object> multi = (Comparator<Object>)temp;
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
    
    /**
     * Theoretically, a method ref is a lambda in short verse
     * @return
     */
    public TreeSet<Object> getSortedItemsViaLamdba() {
        
        @SuppressWarnings("unused")
        Comparator<String> comp
        = (first, second) // Same as (String first, String second)
           -> Integer.compare(first.length(), second.length());
           
        @SuppressWarnings("unused")
        Comparator<Person> comp2
            = (Person p1, Person p2) -> String.CASE_INSENSITIVE_ORDER.compare(p1.getFirst(), p2.getFirst());
              

            Comparator<? extends Object> comp3
                = (Person p1, Person p2) -> String.CASE_INSENSITIVE_ORDER.compare(p1.getFirst(), p2.getFirst());
                  
        //testing functions in java 8
        Comparator<? extends Object> temp = null;
        temp = comp3;
        @SuppressWarnings("unchecked")
        Comparator<Object> multi = (Comparator<Object>)temp;
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }

    /**
     * modeled after a lambda expression bc silly to make a LocalDateComparator (based on just 1 field)
     * - small object, too much naming for little substance? 
     * 
     * @return
     */
    public TreeSet<Object> getSortedByDate() {
        Comparator<? extends Object> temp = Comparator.comparing(Passenger::getBoardingDate);
        @SuppressWarnings("unchecked")
        Comparator<Object> multi = (Comparator<Object>)temp;
        sortedItems = new TreeSet<Object>(multi);
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
}
