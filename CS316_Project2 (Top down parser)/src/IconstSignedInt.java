public class IconstSignedInt extends Instruction {
	SignedInt val;
	public IconstSignedInt(SignedInt val) {
		this.val = val;
	}
	
	public String toString() {
		return "iconst " + val;
	}
}
