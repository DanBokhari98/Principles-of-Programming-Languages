public class Label {
    UnsignedInt unsignedInt;

    public Label(UnsignedInt unsignedInt){
        this.unsignedInt = unsignedInt;
    }
    
    public String toString(){
    	return unsignedInt.toString();
    }
}
