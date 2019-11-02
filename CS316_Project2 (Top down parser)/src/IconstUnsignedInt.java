public class IconstUnsignedInt extends Instruction {
	UnsignedInt val;
	
	IconstUnsignedInt(UnsignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "iconst " + this.val;
	}
}
