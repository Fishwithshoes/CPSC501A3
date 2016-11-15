package code;

import java.util.List;
import java.util.ArrayList;

import org.jdom2.*;
import java.lang.reflect.*;

public class Deserializer {
	
	public Deserializer() {
		
	}
	
	public Object deserialize(org.jdom2.Document document) {
		Object retObj = null;	
		Element serial = document.getRootElement();
		Element rootObj = serial.getChild("object");
		retObj = deserializeElem(rootObj, serial);
		return retObj;
	}
	
	public Object primHandler (Field currField, String sValue) {
		Class<?> currFieldType = currField.getType();
		Object setValue = null;
		if (currFieldType == byte.class) {
			setValue = Byte.parseByte(sValue);
		}
		else if (currFieldType == short.class) {
			setValue = Short.parseShort(sValue);
		}
		else if (currFieldType == int.class) {
			setValue = Integer.parseInt(sValue);
		}
		else if (currFieldType == long.class) {
			setValue = Long.parseLong(sValue);
		}
		else if (currFieldType == float.class) {
			setValue = Float.parseFloat(sValue);
		}
		else if (currFieldType == double.class) {
			setValue = Double.parseDouble(sValue);
		}
		else if (currFieldType == boolean.class) {
			setValue = Boolean.parseBoolean(sValue);
		}
		else if (currFieldType == char.class) {
			setValue = sValue.charAt(0);
		}
		
		return setValue;
	}
	
	public Object refHandler (Field currField, String refValue, Element root) {
		Object setValue = null;
		List<Element> allFieldElems = root.getChildren();
		for (Element field : allFieldElems) {
			if (field.getAttribute("id").toString().equals(refValue)) {
				deserializeElem(field, root);
				break;
			}		
		}
		return setValue;
	}
	
	public Object deserializeElem(Element parent, Element root) {
		Object retObj = null;
		String rootObjName = parent.getAttributeValue("class");
		Class<?> retClass;
			try {
				retClass = Class.forName(rootObjName);
				retObj = retClass.newInstance();
			List<Element> objFieldElems = parent.getChildren();
			for (Element field : objFieldElems) {
				String fieldName = field.getAttributeValue("name");
				Field currObjField = retClass.getDeclaredField(fieldName); //Field Object
				String fieldValue = field.getValue(); //String rep of XML field value
				currObjField.setAccessible(true);
				if (currObjField.getType().isPrimitive())
					currObjField.set(retObj, primHandler(currObjField, fieldValue));
				else if (currObjField.getType().isArray()) {} //fill in
					//currObjField.set(retObj, arrayHandler(currObjField, fieldValue));	
				else {
					currObjField.set(retObj, refHandler(currObjField, fieldValue, root));
				}
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
}
