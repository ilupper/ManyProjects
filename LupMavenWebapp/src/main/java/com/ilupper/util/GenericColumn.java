package com.ilupper.util;

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
import java.util.Iterator;
import java.util.StringTokenizer;

public class GenericColumn {

	ArrayList<String> schema = new ArrayList<String>(), list;
	//ArrayList<Person> personList = new ArrayList<Person>();
	ArrayList<Object> objectList = new ArrayList<Object>();

	public void populateDAO(ArrayList<String> list, Object obj) {
	
		//attempt to use reflection on Person to automate fields
		Class genericClass = obj.getClass();
		Field[] fieldList = genericClass.getFields();
		Method[] methodList = genericClass.getMethods();
		
		for (int i=0; i < list.size(); i++) {
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
								
								m.invoke(obj, list.get(i));
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

	public GenericColumn() {

		//this.setFileInfo("importFile_mini.txt");
		this.setFileInfo("importFile.txt");

		// the schema
		try {
			this.setUpColumns(br.readLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// the content
		try {
			String line = null;
			do {
				line = br.readLine();
				if (line != null)
					this.matchDataToColumns(line);
			} while (line != null);

			this.closeFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		//Person temp = null; //Debug code
		
		//put the data to a set from a arraylist
		HashSet<Person> hs = new HashSet<Person>();
		for (Object obj: objectList) {
			System.out.println("Person first name is " + ((Person)obj).getFirst());
			System.out.println("Person last name is " + ((Person)obj).getLast());
			System.out.println("Person id is " + ((Person)obj).getId());
			hs.add((Person)obj);
			/*	Debug code **
			 * if (temp == null)
				temp=person;
			else {
				System.out.println("falsity? " + temp.equals(person));
			}
			*/
		}

		//print out the results: should be unique
		Iterator<Person> iterator = hs.iterator();
		while (iterator.hasNext()) {
			Person p = (Person)iterator.next();
			System.out.println("Name is " + p.getFirst());
		}	
	}

	public static void main(String[] args) {
		new GenericColumn();
	}

	public void setUpColumns(String heading) {

		StringTokenizer st = new StringTokenizer(heading, ",");
		while (st.hasMoreTokens())
			schema.add(st.nextToken().trim());
	}

	FileReader fr = null;
	BufferedReader br;

	public void matchDataToColumns(String row) {

		list = new ArrayList<String>();

		StringTokenizer st = new StringTokenizer(row, ",");
		while (st.hasMoreTokens())
			this.list.add(st.nextToken().trim());

		Class objectClass = null;
		try {
			objectClass = Class.forName("com.ilupper.util.Person");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
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
			// TODO Auto-generated catch block
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
}
