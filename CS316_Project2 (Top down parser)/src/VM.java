import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class VM extends LexVM {
	static ArrayList<InstructionUnit> instStore = new ArrayList<>();
    static boolean errorFound = false;
    static HashMap<Integer, Integer> map = new HashMap<>();
    static Integer counter = 0;
    static Integer gotoNum = 0;
    static Integer errorNum = 0;
    public static InstructionList instructionList() {
        LinkedList<InstructionUnit> list = new LinkedList<>();
        InstructionUnit u = instructionUnit();
        list.add(u);
        instStore.add(u);
        int step = 0;
        while(state != State.UNDEF) {
        	++step;
        	counter = step;
        	u = instructionUnit();
        	list.add(u);
        	instStore.add(u);
        	if(t.isEmpty() || errorFound) break;
        }
        return new InstructionList(list);
    }

    public static void printToFile() {
    	int c = 0;
    	HashSet<Instruction> changed = new HashSet<>();
    	//for (int i = 0; i < instStore.size();i++)
        for(int i = 0; i < instStore.size();i++) {
        	InstructionUnit unit = instStore.get(i);
        	if(unit.label != null && map.containsKey(unit.label.unsignedInt.val)) {
        		String t = unit.label.toString();
        		Integer line = (unit.label.unsignedInt.val);
    			for(InstructionUnit u : instStore) {
    				if(changed.contains(u.instruction))
    					continue;
    				if(u.instruction instanceof Invoke) {
    					((Invoke)u.instruction).setValOne(new UnsignedInt(i));
    					changed.add(u.instruction);
    				}
    				else if(u.instruction instanceof CmpInstNameUnsignedInt) {
    					CmpInstNameUnsignedInt inst =  ((CmpInstNameUnsignedInt)u.instruction);
    					if(inst.val.val == line) {
    						inst.setVal(new UnsignedInt(map.get(line)));
        					changed.add(u.instruction);	
    					}
    				}
    				
    				else if(u.instruction instanceof Goto) {
    					Goto g = ((Goto)u.instruction);
    					if(g.val.val == i) {
    						g.val.val = i;
    						changed.add(u.instruction);
    					}
    				}
    				//Doesn't work because Icmpeq... and others are CmpInst type and not instructions 
    					/*if(u.instruction instanceof Icmpeq) ((Icmpeq)u.instruction).setVal(line);    					
    					if(u.instruction instanceof Icmpge) ((Icmpge)u.instruction).setVal(line);
    					if(u.instruction instanceof Icmpgt) ((Icmpgt)u.instruction).setVal(line);
    					if(u.instruction instanceof Icmple) ((Icmple)u.instruction).setVal(line);
    					if(u.instruction instanceof Icmplt) ((Icmplt)u.instruction).setVal(line);
    					if(u.instruction instanceof Icmpne) ((Icmple)u.instruction).setVal(line);
    					//START F INSTRUCTIONS
    					if(u.instruction instanceof Fcmpeq) ((Fcmpeq)u.instruction).setVal(line);
    					if(u.instruction instanceof Fcmpge) ((Fcmpge)u.instruction).setVal(line);
    					if(u.instruction instanceof Fcmpgt) ((Fcmpgt)u.instruction).setVal(line);
    					if(u.instruction instanceof Fcmple) ((Fcmple)u.instruction).setVal(line);
    					if(u.instruction instanceof Fcmplt) ((Fcmplt)u.instruction).setVal(line);
    					if(u.instruction instanceof Fcmpne) ((Fcmple)u.instruction).setVal(line);	*/
    			}
        	}
        	
        	if(c == counter) break;
        	c++;
        }
        
        for(int i = 0; i < c; i++) {
        	if(!map.containsKey(gotoNum) && gotoNum != 0) {
        		errorMsg(11);
        		break;
        	}
        	displayln(i + ": " + instStore.get(i).instruction);
        }
    }
    
    public static InstructionUnit instructionUnit(){
    	Label lab = label();
        Instruction ins = instruction();
        if(lab == null) {
        	return new InstructionUnit(ins);
        }else {
        	if(map.containsKey(lab.unsignedInt.val)) errorMsg(14);
        	
        	map.put(lab.unsignedInt.val, counter);
        	return new InstructionUnit(lab, ins);	
        }
    }

    private static Instruction instruction() {
    	switch(state) {
    		case Iconst:
    			getToken();
    			if(state == State.UnsignedInt) {
    				String token = t;
    				getToken();
    				UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
    				return new IconstUnsignedInt(val);
    			}else if(state == State.SignedInt){
    				String token = t;
    				getToken();
    				SignedInt val = new SignedInt(Integer.parseInt(token));
    				return new IconstSignedInt(val);
    			}else {
    				errorMsg(7);
    			}
    			break;
    		case Iload:
    			getToken();
    			if (state == State.UnsignedInt) {
    				String token = t;
    				getToken();
        			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
        			return new IloadUnsignedInt(val);
    			}

    		case Istore:
      			getToken();
    			if (state == State.UnsignedInt) {
    				String token = t;
    				getToken();
        			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
        			return new Istore(val);
    			}
    			break;
    		case Fconst:
      			getToken();
    			if (state == State.Float) {
    				String token = t;
    				getToken();
        			return new FconstFloat(Float.parseFloat(token));
    			} else if (state == State.FloatE) {
    				String token = t;
    				getToken();
        			return new FconstFloatE(Float.parseFloat(token));
    			}else {
    				errorMsg(8);
    			}
    			break;
    		case Fload:
      			getToken();
    			if (state == State.UnsignedInt) {
    				String token = t;
    				getToken();
        			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
        			return new Fload(val);
    			}
    			break;
    		case Fstore:
      			getToken();
    			if (state == State.UnsignedInt) {
    				String token = t;
    				getToken();
        			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
        			return new Fstore(val);
    			}
    			break;
    		case Iadd:
    			getToken();
    			return new Iadd();
    		case Isub:
    			getToken();
    			return new Isub();
    		case Imul:
    			getToken();
    			return new Imul();
    		case Idiv:
    			getToken();
    			return new Idiv();
    		case Fadd:
    			getToken();
    			return new Fadd();
    		case Fsub:
    			getToken();
    			return new Fsub();
    		case Fmul:
    			getToken();
    			return new Fmul();
    		case Fdiv:
    			getToken();
    			return new Fdiv();
    		case IntToFloat:
    			getToken();
    			return new IntToFloat();
    		case Goto:
    			getToken();
    			if (state == State.UnsignedInt) {
    				String token = t;
    				getToken();
        			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
        			gotoNum = Integer.parseInt(token);
        			return new Goto(val);
    			}
    			break;
    		case Invoke:
    			getToken();
    			if (state == State.UnsignedInt) {
    				String int1 = t;
    				getToken();
    				if(state == State.Comma) {
    					getToken();
    					if(state == State.UnsignedInt) {
    						String int2 = t;
    						getToken();
    						if(state == State.Comma) {
    							getToken();
    							if(state == State.UnsignedInt) {
    								String int3 = t;
    								getToken();
    								UnsignedInt one = new UnsignedInt(Integer.parseInt(int1));
    								UnsignedInt two = new UnsignedInt(Integer.parseInt(int2));
    								UnsignedInt three = new UnsignedInt(Integer.parseInt(int3));
    								return new Invoke(one, two, three);
    							}else errorMsg(6);
    						}else errorMsg(9);
    					}else errorMsg(6);
    				}else errorMsg(9);
    					
    			}
    		case Return:
    			getToken();
    			return new Return();
    		case Ireturn:
    			getToken();
    			return new IReturn();
    		case Freturn:
    			getToken();
    			return new FReturn();
    		case Print:
    			getToken();
    			String token = t;
    			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
    			return new Print(val);
    		//ALL ICMP AND FCMP Parse trees handle tokens
    		case Icmpeq:
    			return cmpint();
    		case Icmpne:
    			return cmpint();
    		case Icmplt:
    			return cmpint();
    		case Icmple:
    			return cmpint();
    		case Icmpgt:
    			return cmpint();
    		case Icmpge:
    			return cmpint();
    		case Fcmpeq:
    			return cmpint();
    		case Fcmpne:
    			return cmpint();
    		case Fcmplt:
    			return cmpint();
    		case Fcmple:
    			return cmpint();
    		case Fcmpgt:
    			return cmpint();
    		case Fcmpge:
    			return cmpint();
    	}
            return null; // Null for now;
    }
    
    public static CmpInstNameUnsignedInt cmpint() {
    	CmpInstName name = null;
    	switch(state) {
			case Icmpeq:
				getToken();
				name = new Icmpeq();
				break;
			case Icmpne:
				getToken();
				name = new Icmpne();
				break;
			case Icmplt:
				getToken();
				name = new Icmplt();
				break;
			case Icmple:
				getToken();
				name = new Icmple();
				break;
			case Icmpgt:
				getToken();
				name = new Icmpgt();
				break;
			case Icmpge:
				getToken();
				name = new Icmpge();
				break;
			case Fcmpeq:
				getToken();
				name = new Fcmpeq();
				break;
			case Fcmpne:
				getToken();
				name = new Fcmpne();
				break;
			case Fcmplt:
				getToken();
				name = new Fcmplt();
				break;
			case Fcmple:
				getToken();
				name = new Fcmple();
				break;
			case Fcmpgt:
				getToken();
				name = new Fcmpgt();
				break;
			case Fcmpge:
				getToken();
				name = new Fcmpge();
				break;
    	}
		if(state == State.UnsignedInt) {
			String token = t;
			getToken();
			UnsignedInt val = new UnsignedInt(Integer.parseInt(token));
			return new CmpInstNameUnsignedInt(name,val);
		}
		return null;
    }

    public static Label label() {
        if (state == State.UnsignedInt) {
            String unsignedInt = t;
            UnsignedInt u = new UnsignedInt(Integer.parseInt(t));
            getToken();
            if (state == State.Colon){
                getToken();
                return new Label(u);
            }
        }
        return null;
    }

    //Error Message Function
    public static void errorMsg(int i) {
    	errorNum = i;
        errorFound = true;
        switch (i) {
            case 1:
                displayln(" arith op or ) expected");
                return;
            case 2:
                displayln(" id, int, float, or ( expected");
                return;
            case 3:
                displayln(" = expected");
                return;
            case 4:
                displayln(t + " : expected");
                return;
            case 5:
                displayln(" id expected");
                return;
            case 6:
            	System.out.println("UnsignedInt expected");
                displayln(" UnsignedInt expected");
                return;
            case 7:
            	System.out.println(t + " : Syntax Error, unexpected symbol where integer expected");
            	System.out.println(t + "  -- unexpected symbol");
            	displayln(t + " : Syntax Error, unexpected symbol where integer expected");
            	displayln(t + "  -- unexpected symbol");
            	return;
            case 8:
            	System.out.println(t + " : Syntax Error, unexpected symbol where floating-point number expected");
            	displayln(t + " : Syntax Error, unexpected symbol where floating-point number expected");
            	getToken();
            case 9:
            	System.out.println(t + " unexpected symbol where ',' is expected");
            case 10:
            	System.out.println(t +" : Syntax Error, unexpected symbol where : expected");
            	displayln(t +" : Syntax Error, unexpected symbol where : expected");
            case 11:
            	System.out.println("Target label " + gotoNum + " of goto instruction is missing.");
            	displayln("Target label " + gotoNum + " of goto instruction is missing.");
            case 12:
            	//Should be invoke num
            	System.out.println("Target label " + gotoNum + " of invoke instruction is missing.");
            	displayln("Target label " + gotoNum + " of invoke instruction is missing.");
            case 13:
            	System.out.println("Target label " + gotoNum + " of icmpgt instruction is missing.");
            	displayln("Target label " + gotoNum + " of icmpgt instruction is missing.");
            case 14:
            	displayln("Label occurs more than once");
        }
    }
    
    //Runs the program
    public static void main(String [] argv) {
    	setIO( argv[0], argv[1] );
		setLex();
		getToken();
		InstructionList instList = instructionList();
		if(!t.isEmpty()) { //PRINT THE ERROR MESSAGES 
			//errorMsg(5);
		}else if(!errorFound) { // IF ERROR IS NOT FOUND PRINT THE PARSE TREE TO FILE
			printToFile();
		}else if(errorFound) {
			errorMsg(errorNum);
		}
		closeIO();
    }
}