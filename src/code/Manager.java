package code;

import code.*;
import obj.*;
import org.jdom2.*;

public class Manager {
	//private UserInterface UI = new UserInterface();
	//private int classSelected;
	
	public static void main(String args[]) {
		UserInterface UI = new UserInterface();
		int typeSelected;
		typeSelected = UI.displayTypeSelect();
		Object obj = new Object();
		obj = UI.createObj(typeSelected);
		Serializer ser = new Serializer();
		Document objXML = ser.serialize(obj);
		
	}
}
