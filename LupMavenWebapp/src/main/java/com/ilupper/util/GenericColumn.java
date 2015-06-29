package com.accenture.dojo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

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
        
        //put the data to a set from a arraylist
        for (Object obj: objectList) {
            uniqueItems.add(obj);
        }
  
    }
    
    TreeSet<Object> sortedItems = null;
    public void sortData(HashSet<Object> hashSet) {
        sortedItems = new TreeSet<Object>(hashSet);       
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
								
								String holding = data.get(i);
								if (NumberDeterminant.isInteger(holding))
								    m.invoke(obj, NumberDeterminant.convertToInteger(holding));
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
    
        return uniqueItems;
    }

    
    public void setUniqueItems(HashSet<Object> hs) {
    
        this.uniqueItems = hs;
    }

    
    public TreeSet<Object> getSortedItems() {
    
        return sortedItems;
    }

    
    public void setSortedItems(TreeSet<Object> treeSet) {
    
        this.sortedItems = treeSet;
    }
}
