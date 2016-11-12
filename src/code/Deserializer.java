package code;

import java.util.List;
import java.util.ArrayList;

import org.jdom2.*;
import java.lang.reflect.*;

public class Deserializer {
	
	public Deserializer() {
		
	}
	
	public Object deserialize(org.jdom2.Document document) {
		Class<?> retClass;
		Object retObj = null;
		
		Element root = document.getRootElement();
		Element object = root.getChild("object");
		String objName = object.getAttributeValue("class");
		List<Element> objFieldElems = object.getChildren();
		try {
			retClass = Class.forName(objName);
			retObj = retClass.newInstance();
			for (Element field : objFieldElems) {
				String fieldName = field.getAttributeValue("name");
				Field currObjField = retClass.getDeclaredField(fieldName); //Field Object
				String fieldValue = field.getValue(); //String rep of XML field value
				if (currObjField.getType().isPrimitive())
					primHandler(retObj, currObjField, fieldValue);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retObj;
	}
	
	public void primHandler (Object currObj, Field currField, String sValue) {
		Class<?> currFieldType = currField.getType();
		Object setValue = null;
		if (currFieldType == int.class) {
			setValue = Integer.parseInt(sValue);
		}
		
		try {
			currField.setAccessible(true);
			currField.set(currObj, setValue);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
