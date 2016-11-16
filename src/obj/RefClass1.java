package obj;

public class RefClass1 {
	private PrimClass1 primClassA = new PrimClass1();
	private PrimClass1 primClassB = new PrimClass1();

	public RefClass1() {
		
	}
	
	public PrimClass1 getA() {
		return primClassA;
	}
	
	public void setA(PrimClass1 in) {
		primClassA = in;
	}
	
	public PrimClass1 getB() {
		return primClassB;
	}
	
	public void setB(PrimClass1 in) {
		primClassB = in;
	}
}
