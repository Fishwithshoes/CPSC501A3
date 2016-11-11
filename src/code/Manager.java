package code;

import code.*;
import obj.*;

public class Manager {
	//private UserInterface UI = new UserInterface();
	//private int classSelected;
	
	public static void main(String args[]) {
		UserInterface UI = new UserInterface();
		int typeSelected;
		typeSelected = UI.displayTypeSelect();
		Object obj = new Object();
		obj = UI.createObj(typeSelected);
		
	}
}
