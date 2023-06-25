package ut.pp;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class SymbolTable {

    private final Vector<Map<String, Type>> symbolStack;
    public enum Type {
        INT, BOOL, STRING
    }
    public Type getType (String type) {
        if(type.charAt(0) == '\"')
            return SymbolTable.Type.STRING;
        else if(type.equals("True") || type.equals("False"))
            return SymbolTable.Type.BOOL;
        else
            return SymbolTable.Type.INT;
    }
    public SymbolTable()
    {
        symbolStack = new Stack<>();
        symbolStack.add(new HashMap<>());
    }

    public void openScope() {
        Map<String, Type> newScopeMap = new HashMap<>();
        symbolStack.addElement(newScopeMap);
    }

    public void closeScope() {
        if(symbolStack.size()==1)
            throw new RuntimeException();
        symbolStack.remove(symbolStack.size() - 1);
    }

    public boolean add(String id, String type) {
        Map<String, Type> top = symbolStack.lastElement();
        if(top.containsKey(id))
        {
            return false;
        }
        else
        {
            top.put(id, getType(type));
            return true;
        }
    }

    public boolean contains(String id, String type) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
                if(symbolStack.get(depth).get(id) == getType(type))
                    return true;
            }
            depth -= 1;
        }
        return false;
    }

    /**
     * Perform scope and type checking on a variable
     * @return
     * <p> 0 - variable in scope with matching type</p>
     * <p> 1 - not in scope </p>
     * <p> 2 - type mismatch </p>
     */
    public int check(String id, String type) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
                if(symbolStack.get(depth).get(id) != getType(type))
                    return 2;
                return 0;
            }
            depth -= 1;
        }
        return 1;
    }

}