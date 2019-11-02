public class FconstFloat extends Instruction {
	double val;
	
	FconstFloat(double val) {
		this.val = val;
	}
	
	public String toString() {
		return "fconst " + this.val;
	}
}
