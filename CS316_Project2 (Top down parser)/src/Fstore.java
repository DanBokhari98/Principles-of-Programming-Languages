public class Fstore extends Instruction {
	UnsignedInt val;
	
	public Fstore(UnsignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "fstore ";
	}
}
