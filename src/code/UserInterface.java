package code;

import obj.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
	Scanner input = new Scanner(System.in);

	public UserInterface() {
		
	}
	
	public int displayTypeSelect() {
		boolean goodSelect = false;
		int selection = -1;
		while (!goodSelect) {
			System.out.println("Please select the type of Object you wish to serialize:\n"
					+ "1) Primitive Only Object\n"
					+ "2) Primitive Array Object\n"
					+ "3) Reference Only Object\n"
					+ "4) Reference Array Object\n"
					+ "5) Exit");
			
			try {
				selection = input.nextInt();
			} catch (InputMismatchException e) {	//is int?
				input.next();
			}
			if((selection < 1) || (selection > 5)) {	//is [1-4]
				System.out.println("***Please make a valid selection (1-5)***");	//Better error handling
			}
			else {
				goodSelect = true;
			}
		}
		return selection;
	}
	
	public Object createObj(int type) {
		if (type == 1) {
			PrimClass1 returnObject = new PrimClass1();
			returnObject = fillPrimClass1(returnObject);
			return returnObject;
		}
		else if (type == 2) {
			ArrayClass1 returnObject = new ArrayClass1();
			returnObject = fillArrayClass1(returnObject);
			return returnObject;
		}
		else if (type == 3) {
			RefClass1 returnObject = new RefClass1();
			returnObject = fillRefClass1(returnObject);
			return returnObject;
		}
		else if (type == 4) {
			RefArrayClass1 returnObject = new RefArrayClass1();
			returnObject = fillRefArrayClass1(returnObject);
			return returnObject;
		}
		else {
			return null;
		}
	}
	
	public PrimClass1 fillPrimClass1(PrimClass1 currPrimObj) {
		int iValue;
		char cValue;
		float fValue;
		boolean goodSelect = false;

		while (!goodSelect) {
			try {
				System.out.println("Please enter a value for the Object's integer field: ");
				iValue = input.nextInt();
				currPrimObj.setI(iValue);
				System.out.println("Please enter a value for the Object's character field: ");
				cValue = input.next().charAt(0);
				currPrimObj.setC(cValue);
				System.out.println("Please enter a value for the Object's float field: ");
				fValue = input.nextFloat();
				currPrimObj.setF(fValue);
				goodSelect = true;
			} catch (InputMismatchException e) {	//is int?
				System.out.println("Please make a valid selection");	//Better error handling
				input.next();
			}
		}
		return currPrimObj;
	}
	
	public ArrayClass1 fillArrayClass1(ArrayClass1 currArrayObj) {
		int iValue;
		char cValue;
		float fValue;
		boolean goodSelect = false;

		while (!goodSelect) {
			try {
				for (int i = 0; i < 3; i++) {
					System.out.println("Please enter a value for the Object's integer array at position " + Integer.toString(i) + ":");
					iValue = input.nextInt();
					currArrayObj.setI(iValue, i);
				}
				for (int i = 0; i < 3; i++) {
					System.out.println("Please enter a value for the Object's character array at position " + Integer.toString(i) + ":");
					cValue = input.next().charAt(0);
					currArrayObj.setC(cValue, i);
				}
				for (int i = 0; i < 3; i++) {
					System.out.println("Please enter a value for the Object's float array at position " + Integer.toString(i) + ":");
					fValue = input.nextFloat();
					currArrayObj.setF(fValue, i);
				}
				goodSelect = true;
			} catch (InputMismatchException e) {	//is int?
				System.out.println("Please make a valid selection");	//Better error handling
				input.next();
			}
		}
		return currArrayObj;
	}
	
	public RefClass1 fillRefClass1(RefClass1 currRefObj) {
		System.out.println("Please fill out the information for primClassA: ");
		currRefObj.setA(fillPrimClass1(currRefObj.getA()));
		System.out.println("Please fill out the information for primClassB: ");
		currRefObj.setB(fillPrimClass1(currRefObj.getB()));
		
		return currRefObj;
	}
		
	public RefArrayClass1 fillRefArrayClass1(RefArrayClass1 currRefArrObj) {
		System.out.println("Please fill out the information for primRef: ");
		currRefArrObj.setPrim(fillPrimClass1(currRefArrObj.getPrim()));
		System.out.println("Please fill out the information for arrayRef: ");
		currRefArrObj.setArr(fillArrayClass1(currRefArrObj.getArr()));
		
		return currRefArrObj;
	}
	
}
