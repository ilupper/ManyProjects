package com.ilupper.util;

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

import com.accenture.dojo.experiment.PersonFunction;
import com.accenture.dojo.orgchart.Person;
import com.accenture.dojo.orgchart.PersonnelIDComparator;

public class GenericColumn {

	ArrayList<String> schema = new ArrayList<String>(), list;
	ArrayList<Object> objectList = new ArrayList<Object>();

	HashSet<Object> uniqueItems = new HashSet<Object>();
    
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

    public static void main(String[] args) {
        new GenericColumn("Person.txt", "com.accenture.dojo.orgchart.Person");
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
						if (methodField.contains( tempField ) && methodField.startsWith("set"))
							try {
								//debug
								System.out.println("Method found and executing.. " + m.getName());
								System.out.println("Field found and executing.. " + f.getName());
								
								LocalDate ld = null;
								String holding = data.get(i);
								if (TypeDeterminant.isInteger(holding))
								    m.invoke(obj, TypeDeterminant.convertToInteger(holding));
								else if ( (ld = TypeDeterminant.convertToLocalDate(holding)) != null) {
								    m.invoke(obj, ld);
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
    
    public TreeSet<Object> getSortedItems2(Comparator<? extends Object> comparator) {
        
        @SuppressWarnings("unchecked")
        Comparator<Object> comparator2 = (Comparator<Object>)comparator;
        Comparator<Object> reversed = comparator2.reversed();
        
        Comparator<? extends Object> temp = new PersonnelIDComparator();
        @SuppressWarnings("unchecked")
        Comparator<Object> pidc = (Comparator<Object>)temp;
        Comparator<Object> multi = reversed.thenComparing( pidc );
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
    
    public TreeSet<Object> getSortedItemsViaFunction() {
        
        //testing functions in java 8
        Comparator<Object> multi = null;
        multi = Comparator.comparing(new PersonFunction()); //probably not how it's done
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }

    public TreeSet<Object> getSortedItemsViaMethodRef() {
        
        //testing functions in java 8
        Comparator<? extends Object> temp = null;
        temp = Comparator.comparing(Person::getFirstName);
        @SuppressWarnings("unchecked")
        Comparator<Object> multi = (Comparator<Object>)temp;
        
        sortedItems = new TreeSet<Object>(multi);     
        sortedItems.addAll(uniqueItems);
        return sortedItems;
    }
}
