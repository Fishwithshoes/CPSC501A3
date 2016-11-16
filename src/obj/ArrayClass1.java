package obj;

import java.util.ArrayList;

public class ArrayClass1 {
	private int [] intArray = new int[3];
	private char [] charArray = new char[3];
	private float [] floatArray = new float[3];
	
	
	public ArrayClass1() {

	}

	public void setI(int in, int index) {
		intArray[index] = in;
	}
	
	public void setC(char in, int index) {
		charArray[index] = in;
	}
	
	public void setF(float in, int index) {
		floatArray[index] = in;
	}
}
