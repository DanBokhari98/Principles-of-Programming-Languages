public class FconstFloatE extends Instruction {
	float val;
	
	FconstFloatE(float val) {
		this.val = val;
	}
	
	public String toString() {
		return "fconst " + this.val;
	}
}
