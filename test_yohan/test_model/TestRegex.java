package test_model;

import java.util.ArrayList;
import java.util.Scanner;

public class TestRegex {
	
	private static ArrayList<String> bucles = new ArrayList<String>();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String regex = sc.nextLine();
		System.out.println("La regex es: " +regex);
		
		getAllBucles(regex);
		System.out.println("Bucles de la regex:");
		printBuclesList();
		
		sc.close();
	}
	
	
	
	private static void getAllBucles(String regex) {
		String auxString = regex;
		for (int i = 0; i < regex.length(); i++) {
			if(regex.charAt(i)=='*') {
				bucles.add(getBucleElement(auxString));
				auxString = auxString.replaceFirst("[*]", "");
			}
		}
	}
	
	
	private static String getBucleElement(String regex) {
		String element = "";
		
		int i = 0;
		
		while(i<regex.length() && regex.charAt(i)!='*')
			i++;
		
		if(regex.substring(i-1, i).equals(")"))
			element = getGroupBucle(regex, i);
		else
			element = regex.substring(i-1, i);
		return element;
	}
	
	//((ab)*c)* 8
	private static String getGroupBucle(String regex, int i) { //Se ubica en el parentesis de salida
		int j = i-2; //<-- = 0 - (
		int cont = 0; //--> = -1
		
		while(j>=0 && cont >=0) {
			if(regex.charAt(j)==')') {
				cont ++;				
			}
			else if(regex.charAt(j)=='(') {
				cont--;			
			}
			j--;
		}
		
		return regex.substring(j+1,i);	// 
	}
	
	private static void printBuclesList() {
		for (int i = 0; i < bucles.size(); i++) {
			System.out.println(bucles.get(i));
		}
	}
}
