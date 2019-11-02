public class Invoke extends Instruction{
	UnsignedInt val;
	UnsignedInt val2;
	UnsignedInt val3;
	public Invoke(UnsignedInt v, UnsignedInt v2, UnsignedInt v3) {
		this.val = v;
		this.val2 = v2;
		this.val3 = v3;
	}
	
	public String toString() {
		return "Invoke: " + val + "," + val2 + "," + val3;
	}
	
	public void setValOne(UnsignedInt t) {
		this.val = t;
	}
}