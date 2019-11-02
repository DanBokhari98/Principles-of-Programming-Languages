public class CmpInstNameUnsignedInt extends Instruction {
	CmpInstName name;
	UnsignedInt val;
	
	public CmpInstNameUnsignedInt(CmpInstName n, UnsignedInt val) {
		name = n;
		this.val = val;
	}
	
	public void setVal(UnsignedInt s) {
		this.val = s;
	}
	
	public String toString() {
		return name.toString() + " " + this.val.val;
	}
}
