package obj;

import java.util.ArrayList;

public class RefCollClass1 {
	
	private ArrayList<Object> testRefColl = new ArrayList<Object>();
	//PrimClass1 collPrimRef = new PrimClass1();
	//ArrayClass1 collArrayRef = new ArrayClass1();

	public RefCollClass1 () {

		//testRefColl.add(collPrimRef);
		//testRefColl.add(collArrayRef);
	}
	
	/*public PrimClass1 getCollPrim() {
		return (PrimClass1) testRefColl.get(testRefColl.indexOf(collPrimRef));
	}*/
	
	public void setCollPrim(PrimClass1 in) {
		testRefColl.add(in);
	}
	
	/*public ArrayClass1 getCollArr() {
		return (ArrayClass1) testRefColl.get(testRefColl.indexOf(collArrayRef));
	}*/
	
	public void setCollArr(ArrayClass1 in) {
		testRefColl.add(in);
	}
}
