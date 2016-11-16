package code;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Collection;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	Document doc;
	Element root;
	HashMap<Integer, Element> serialMap;
	
	public Serializer() {
		doc = new Document();
		root = new Element("serialized");
		doc.setRootElement(root); //namespace?
		serialMap = new HashMap<Integer, Element>();
	}
	
	public org.jdom2.Document serialize(Object inObj) {
		Element object = new Element("object");
		object.setAttribute("class", inObj.getClass().getName());
		object.setAttribute("id", Integer.toHexString(inObj.hashCode()));
		if (serialMap.containsKey(inObj.hashCode()) == false) {
			serialMap.put(inObj.hashCode(), object);
			root.addContent(object);
		}
		
		if (inObj.getClass().isArray()) {
			object.setAttribute("length", Integer.toString(Array.getLength(inObj)));
			arrayHandler(inObj, object);
		}
		
		else {
		Field [] decFields = inObj.getClass().getDeclaredFields();
		
		for (int i = 0; i < decFields.length; i++) {
			Element field = new Element("field");
			field.setAttribute("name", decFields[i].getName());
			field.setAttribute("declaringclass", inObj.getClass().getName());
			try {
				decFields[i].setAccessible(true);
				if (decFields[i].getType().isPrimitive()) {
					field.addContent(primHandler(inObj, decFields[i]));
				}
				else {
					field.addContent(refHandler(inObj, decFields[i]));
				}
			
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} /*catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			object.addContent(field);
		}
		}
		
		return doc;
	}
	
	public void arrayHandler(Object currObj, Element field) {
		try {
			//Object currArray = currField.get(currObj);

			if (currObj.getClass().getComponentType().isPrimitive()) {
				for (int i = 0; i < Array.getLength(currObj); i++) {
				Element value = new Element("value");
				//currField.setAccessible(true);
				value.addContent(Array.get(currObj, i).toString());
				field.addContent(value);
				}
			}
			else {			
				for (int i = 0; i < Array.getLength(currObj); i++) {
				Element reference = new Element("reference");
				//currField.setAccessible(true);
				reference.addContent(Integer.toHexString(Array.get(currObj, i).hashCode()));
				field.addContent(reference);
				serialize(Array.get(currObj, i));
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Element primHandler(Object currObj, Field currField) {
		Element value = new Element("value");
		try {
			currField.setAccessible(true);
			value.addContent(currField.get(currObj).toString());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;	
	}
	
	public Element refHandler(Object currObj, Field currField) {
		Element reference = new Element("reference");
		try {
			currField.setAccessible(true);
			reference.addContent(Integer.toHexString(currField.get(currObj).hashCode()));
			serialize(currField.get(currObj));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reference;
	}

}
