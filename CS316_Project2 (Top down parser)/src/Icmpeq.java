public class Icmpeq extends CmpInstName {
	public int val;
	
	public void setVal(int s) {
		this.val = s;
	}
	
	public Icmpeq() {
	}

	public String toString() {
		return "Icmpeq ";
	}
	
	public int getVal() {
		return this.val;
	}
}
