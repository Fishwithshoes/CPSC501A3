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

		
		Field [] decFields = inObj.getClass().getDeclaredFields();
		
		for (int i = 0; i < decFields.length; i++) {
			Element field = new Element("field");
			field.setAttribute("name", decFields[i].getName());
			field.setAttribute("declaringclass", inObj.getClass().getName());
			try {
				decFields[i].setAccessible(true);
				//boolean temp = decFields[i].getType().isPrimitive();
				if (decFields[i].getType().isPrimitive()) {
					field.addContent(primHandler(inObj, decFields[i]));
				}
				else if (decFields[i].getType().isArray() || decFields[i].get(inObj) instanceof Collection<?>) {
					arrayHandler(inObj, decFields[i], field);
				}
				else {
					field.addContent(refHandler(inObj, decFields[i]));
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			object.addContent(field);
		}
		
		return doc;
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
	
	public void arrayHandler(Object currObj, Field currField, Element field) {
		try {
			Object currArray = currField.get(currObj);
			if (currArray instanceof Collection<?>)
				currArray = ((Collection<?>) currArray).toArray();

			if (currArray.getClass().getComponentType().isPrimitive()) {
				for (int i = 0; i < Array.getLength(currArray); i++) {
				Element value = new Element("value");
				currField.setAccessible(true);
				value.addContent(Array.get(currArray, i).toString());
				field.addContent(value);
				}
			}
			else {			
				for (int i = 0; i < Array.getLength(currArray); i++) {
				Element reference = new Element("reference");
				currField.setAccessible(true);
				reference.addContent(Integer.toHexString(Array.get(currArray, i).hashCode()));
				field.addContent(reference);
				serialize(Array.get(currArray, i));
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Element refHandler(Object currObj, Field currField) {
		Element reference = new Element("reference");
		try {
			currField.setAccessible(true);
			reference.addContent(Integer.toHexString(currField.get(currObj).hashCode()));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
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
