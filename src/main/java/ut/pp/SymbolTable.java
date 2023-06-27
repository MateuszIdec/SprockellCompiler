package ut.pp;

import java.util.*;

public class SymbolTable {
    private final Vector<Map<String, Type>> symbolStack;
    public SymbolTable()
    {
        symbolStack = new Stack<>();
        symbolStack.add(new HashMap<>());
    }

    public enum Type {
        INT, BOOL, STRING, ARRAY, FORK, ERROR
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

    public boolean add(String id, Type type) {
        Map<String, Type> top = symbolStack.lastElement();
        if(top.containsKey(id))
        {
            return false;
        }
        else
        {
            top.put(id, type);
            return true;
        }
    }

    /**
     * Check if variable exists in current or outer scope
     */
    public boolean contains(String id) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
               return true;
            }
            depth -= 1;
        }
        return false;
    }

    public Type getType (String id) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
                return symbolStack.get(depth).get(id);
            }
            depth -= 1;
        }
        return null;
    }
    /**
     * Perform scope and type checking on a variable
     * @return
     * <p> 0 - variable in scope with matching type </p>
     * <p> 1 - not in scope </p>
     * <p> 2 - type mismatch </p>
     */

    public int check(String id, Type type) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
                if(symbolStack.get(depth).get(id) != type)
                    return 2;
                return 0;
            }
            depth -= 1;
        }
        return 1;
    }

    /**
     * Check if variable is in current scope
     */
    public boolean checkLocalScope(String id) {
        int depth = symbolStack.size()-1;
        return symbolStack.get(depth).containsKey(id);
    }
}