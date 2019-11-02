public class Print extends Instruction{
	UnsignedInt val;
	
	public Print(UnsignedInt v) {
		this.val = v;
	}
	
	public String toString() {
		return "print " + val;
	}
}
