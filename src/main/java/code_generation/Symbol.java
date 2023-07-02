package code_generation;

public class Symbol {
    public Type type = Type.ERROR;
    public boolean isShared = false;
    public int address;

    public Symbol deepCopy()
    {
        Symbol s = new Symbol();
        s.type = this.type;
        s.isShared = this.isShared;
        s.address = this.address;
        return s;
    }
}
