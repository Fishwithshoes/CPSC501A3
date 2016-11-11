package ut;

import code.UserInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestUserInterface {
	UserInterface testInterface = new UserInterface();
	
	@Test
	public void testInterfaceStart () {
		testInterface.displayTypeSelect();
	}

}
