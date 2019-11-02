public class IloadUnsignedInt extends Instruction {
	UnsignedInt val;
	
	public IloadUnsignedInt(UnsignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "iload " + val.toString();
	}
}
