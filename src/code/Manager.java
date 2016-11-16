package code;

import code.*;
import obj.*;

import java.io.IOException;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Manager {
	
	public static void main(String args[]) {
		boolean exit = false;
		while (!exit) {
			UserInterface UI = new UserInterface();
			int typeSelected;
			typeSelected = UI.displayTypeSelect();
			Object obj = new Object();
			obj = UI.createObj(typeSelected);
			if (obj == null) {
				exit = true;
				System.out.println("Closing...");
				System.exit(0);
			}
			Serializer ser = new Serializer();
			Document objXML = ser.serialize(obj);
			
			XMLOutputter XMLOut = new XMLOutputter(Format.getPrettyFormat());
			try {
				XMLOut.output(objXML, System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Deserializer deSer = new Deserializer();
			Object newObj = new Object();
			newObj = deSer.deserialize(objXML);
			Inspector inspec = new Inspector();
			inspec.inspect(newObj, true);
		}
	}
}
