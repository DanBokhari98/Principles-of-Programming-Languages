public class Goto extends Instruction {
	UnsignedInt val;
	public Goto(UnsignedInt v) {
		this.val = v;
	}
	
	public String toString() {
		return "goto: " + val;
	}
}
