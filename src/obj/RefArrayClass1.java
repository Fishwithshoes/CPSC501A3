package obj;

public class RefArrayClass1 {
	private Object [] testRefArray = new Object[2];
	
	public RefArrayClass1 () {
		PrimClass1 primRef = new PrimClass1();
		ArrayClass1 arrayRef = new ArrayClass1();
		testRefArray[0] = primRef;
		testRefArray[1] = arrayRef;
	}
	
	public PrimClass1 getPrim() {
		return (PrimClass1) testRefArray[0];
	}
	
	public void setPrim(PrimClass1 in) {
		testRefArray[0] = in;
	}
	
	public ArrayClass1 getArr() {
		return (ArrayClass1) testRefArray[1];
	}
	
	public void setArr(ArrayClass1 in) {
		testRefArray[1] = in;
	}

}
