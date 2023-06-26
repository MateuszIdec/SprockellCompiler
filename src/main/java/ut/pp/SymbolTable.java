package ut.pp;

import java.util.*;

public class SymbolTable {

    public Var var;
    private final Vector<Map<String, Var>> symbolStack;
    public static class Var {
        public Type type;
        public ArrayList<String> value;
        public Var(Type type, ArrayList<String> value) {
            this.type = type;
            this.value = value;
        }
    }

    public SymbolTable()
    {
        symbolStack = new Stack<>();
        symbolStack.add(new HashMap<>());
    }

    public enum Type {
        INT, BOOL, STRING, ARRAY, ERROR
    }


    public void openScope() {
        Map<String, Var> newScopeMap = new HashMap<>();
        symbolStack.addElement(newScopeMap);
    }

    public void closeScope() {
        if(symbolStack.size()==1)
            throw new RuntimeException();
        symbolStack.remove(symbolStack.size() - 1);
    }

    public boolean add(String id, Var var) {
        Map<String, Var> top = symbolStack.lastElement();
        if(top.containsKey(id))
        {
            return false;
        }
        else
        {
            top.put(id, var);
            return true;
        }
    }

    public boolean contains(String id, Type type) {
        int depth = symbolStack.size()-1;
        while(depth >= 0)
        {
            if(symbolStack.get(depth).containsKey(id)) {
                if(symbolStack.get(depth).get(id).type == type)
                    return true;
            }
            depth -= 1;
        }
        return false;
    }

    public Var getType(String id) {
        int depth = symbolStack.size()-1;
        return symbolStack.get(depth).get(id);
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
                if(symbolStack.get(depth).get(id).type != type)
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