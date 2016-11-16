package ut;

import code.Deserializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.jdom2.*;

public class TestDeserializer {
	private Deserializer testDe = new Deserializer();
	
	@Test
	public void testPrimHandler() {
		Object test = 3;
		Object result = testDe.primHandler(int.class, "3");
		assertEquals(test,result);
	}

}
