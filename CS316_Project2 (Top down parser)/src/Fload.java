public class Fload extends Instruction {
	UnsignedInt val;
	
	public Fload(UnsignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "fload " + val.toString();
	}
}
