package code;

import java.io.IOException;
import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	
	public Serializer() {
		
	}
	
	public org.jdom2.Document serialize(Object inObj) {
		Document doc = new Document();
		Element root = new Element("serialized");
		Element object = new Element("object");
		object.setAttribute("class", inObj.getClass().getName());
		object.setAttribute("id", Integer.toHexString(inObj.hashCode()));
		
		Field [] decFields = inObj.getClass().getDeclaredFields();
		for (int i = 0; i < decFields.length; i++) {
			Element field = new Element("field");
			field.setAttribute("name", decFields[i].getName());
			field.setAttribute("declaringclass", inObj.getClass().getName());
			Element value = new Element("value");
			try {
				decFields[i].setAccessible(true);
				value.addContent(decFields[i].get(inObj).toString());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			field.addContent(value);
			object.addContent(field);
		}
		root.addContent(object);
		doc.setRootElement(root); //namespace?
		
		XMLOutputter XMLOut = new XMLOutputter(Format.getPrettyFormat());
		try {
			XMLOut.output(doc, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}

}
