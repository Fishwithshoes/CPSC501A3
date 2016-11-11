package code;

import java.lang.reflect.*;
import org.jdom2.*;

public class Serializer {
	
	public org.jdom2.Document serialize(Object inObj) {
		Document doc = new Document();
		Element root = new Element("serialized");
		doc.setRootElement(root); //namespace?
		Element object = new Element("object");
		object.setAttribute("class", object.getClass().getName());
		object.setAttribute("id", object.toString());
		
		Field [] decFields = inObj.getClass().getDeclaredFields();
		for (int i = 0; i < decFields.length; i++) {
			Element field = new Element("field");
			field.setAttribute("name", decFields[i].getName());
			field.setAttribute("declaringclass", inObj.getClass().getName());
			Element value = new Element("value");
			try {
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
		
		
		
		return doc;
	}

}
