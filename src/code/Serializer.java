package code;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	Document doc;
	Element root;
	HashMap<Class<?>, Integer> serialMap = new HashMap<Class<?>, Integer>();
	
	public Serializer() {
		doc = new Document();
		root = new Element("serialized");
		doc.setRootElement(root); //namespace?
	}
	
	public org.jdom2.Document serialize(Object inObj) {
		Element object = new Element("object");
		object.setAttribute("class", inObj.getClass().getName());
		object.setAttribute("id", Integer.toHexString(inObj.hashCode()));
		root.addContent(object);
		
		Field [] decFields = inObj.getClass().getDeclaredFields();
		
		for (int i = 0; i < decFields.length; i++) {
			Element field = new Element("field");
			field.setAttribute("name", decFields[i].getName());
			field.setAttribute("declaringclass", inObj.getClass().getName());
			try {
				decFields[i].setAccessible(true);
				boolean temp = decFields[i].getType().isPrimitive();
				if (decFields[i].getType().isPrimitive()) {
					field.addContent(primHandler(inObj, decFields[i]));
				}
				else if (decFields[i].getType().isArray()) {
					//field.addContent(arrayHandler(inObj,decFields[i]));
				}
				else {
					field.addContent(refHandler(inObj, decFields[i]));
				}
			} catch (IllegalArgumentException e) {
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
	
	public Element arrayHandler(Object currObj, Field currField) {
		try {
			Object currArray = currField.get(currObj); //this is array, need to access element type for if/else
			if (currField.get(currObj).getClass().isPrimitive()) {
				for (int i; i < 
				Element value = new Element("value");
				currField.setAccessible(true);
				value.addContent(currField.get(currObj).toString());
				return value;
			}
			else {
				Element reference = new Element("reference");
				currField.setAccessible(true);
				reference.addContent(Integer.toHexString(currField.get(currObj).hashCode()));
				seri
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
