package code_generation;

public class Symbol {
    public Type type = Type.ERROR;
    public boolean isShared = false;
    public boolean isPointer = false;
    public int address;
    public int size = 1;

    public Symbol deepCopy()
    {
        Symbol s = new Symbol();
        s.type = this.type;
        s.isShared = this.isShared;
        s.address = this.address;
        s.size = this.size;

        return s;
    }
}
