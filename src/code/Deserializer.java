package code;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import obj.*;

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
	
	public Object primHandler (Class<?> currFieldType, String sValue) {
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
	
	public Object arrayHandler (Field currField, String refValue, Element root, Object currObject) {
		Object setValue = null;
		boolean aCollection = false;
		try {
			currField.setAccessible(true);
		setValue = currField.get(currObject);
		aCollection = (setValue instanceof Collection<?>);
		List<Element> allFieldElems = root.getChildren();
		for (Element field : allFieldElems) {
			if (field.getAttribute("id").toString().contains(refValue)) {
				List<Element> arrayElems = field.getChildren(); //all values
				if (!(aCollection) && setValue.getClass().getComponentType().isPrimitive() ) {
					int i = 0;
					for (Element elem : arrayElems) {
						Object inValue = primHandler(setValue.getClass().getComponentType(), elem.getValue());
						Array.set(setValue, i, inValue);
						i++;					
					}
				}
				else {
					int i = 0;
					for (Element elem : arrayElems) {
						Object inValue = refHandler(elem.getValue(), root);
						if (aCollection) {
							((Collection<Object>) setValue).add(inValue);
						}
						else {
							Array.set(setValue, i, inValue);
							i++;
						}
					}
					
				}
			}
		}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //catch (InstantiationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		return setValue;
	}
	
	public Object refHandler (String refValue, Element root) {
		Object setValue = null;
		List<Element> allFieldElems = root.getChildren();
		for (Element field : allFieldElems) {
			if (field.getAttribute("id").toString().contains(refValue)) {
				setValue = deserializeElem(field, root);
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
					Class <?> currObjFieldType = currObjField.getType();
					String fieldValue = field.getValue(); //String rep of XML field value
					currObjField.setAccessible(true);
					if (currObjField.getType().isPrimitive())
						currObjField.set(retObj, primHandler(currObjFieldType, fieldValue));
					else if (currObjField.getType().isArray() || Collection.class.isAssignableFrom(currObjField.getType())) {
						currObjField.set(retObj, arrayHandler(currObjField, fieldValue, root, retObj));
					}
					else {
						currObjField.set(retObj, refHandler(fieldValue, root));
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
