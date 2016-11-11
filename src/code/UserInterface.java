package code;

import obj.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
	Scanner input = new Scanner(System.in);

	public UserInterface() {
		
	}
	
	public int displayTypeSelect() {
		int selection = -1;
		System.out.println("Please select the type of Object you wish to serialize:\n"
				+ "1) Primitive Only Object\n"
				+ "2) Reference Object\n"
				+ "3) Primitive Array Object\n"
				+ "4) Reference Array Object");
		
		try {
			selection = input.nextInt();
		} catch (InputMismatchException e) {	//is int?
			System.out.println("Please make a valid selection (1-4)");	//Better error handling
		}
		if((selection < 1) || (selection > 4)) {	//is [1-4]
			System.out.println("Please make a valid selection (1-4)");	//Better error handling
		}
		return selection;
	}
	
	public Object createObj(int type) {
		if (type == 1) {
			PrimClass1 returnObject = new PrimClass1();
			returnObject = fillPrimClass1(returnObject);
			return returnObject;
		}
		return null;
	}
	
	public PrimClass1 fillPrimClass1(PrimClass1 currPrimObj) {
		int value = 0;
		System.out.println("Please enter a value for the Object's integer field: ");
		try {
			value = input.nextInt();
		} catch (InputMismatchException e) {	//is int?
			System.out.println("Please make a valid integer selection");	//Better error handling
		}
		currPrimObj.setI(value);
		return currPrimObj;
	}
	
}
