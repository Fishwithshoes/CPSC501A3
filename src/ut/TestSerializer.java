package ut;

import code.Serializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.jdom2.*;

public class TestSerializer {
	private TestObject testObj = new TestObject();
	private Serializer testSer = new Serializer();
	
	@Test
	public void testPrimHandler() throws NoSuchFieldException, SecurityException {
		Element test = new Element("value");
		test.addContent("3");
		Field primField = testObj.getClass().getDeclaredField("testInt");
		Element result = testSer.primHandler(testObj, primField);
		assertEquals(test.getValue(),result.getValue());
	}
}
