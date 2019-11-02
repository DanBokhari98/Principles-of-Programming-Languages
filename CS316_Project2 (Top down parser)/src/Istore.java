public class Istore extends Instruction {
	UnsignedInt val;
	
	public Istore(UnsignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "istore " + val.toString();
	}
}
