public class InstructionUnit {
    Label label;
    Instruction instruction;
    //Optional Constructor
    public InstructionUnit(Label l, Instruction i) {
    	label = l;
    	instruction = i;
    }
    //Default Constructor
    public InstructionUnit(Instruction i) {
    	label = null;
    	instruction = i;
    }
}
