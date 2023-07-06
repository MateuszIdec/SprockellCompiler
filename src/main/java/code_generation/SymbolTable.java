package code_generation;

import java.util.*;

public class SymbolTable {
    private final Vector<Map<String, Symbol>> symbolStack;
    public SymbolTable()
    {
        symbolStack = new Stack<>();
        symbolStack.add(new HashMap<>());
    }

    public static SymbolTable provideSymbolTableForFork(SymbolTable st, int TID)
    {
        SymbolTable newST = new SymbolTable();
        newST.openScope();
        Symbol tidSymbol = new Symbol();
        tidSymbol.address = TID;
        tidSymbol.type = Type.FORK;
        tidSymbol.isShared = false;
        newST.add("TID", tidSymbol);
        for (Map<String, Symbol> scopeMap : st.symbolStack) {
            for (Map.Entry<String, Symbol> entry : scopeMap.entrySet()) {
                String key = entry.getKey();
                Symbol value = entry.getValue().deepCopy();
                if(value.isShared)
                    newST.add(key, value.deepCopy());
            }
        }
        return newST;
    }

    public void openScope() {
        Map<String, Symbol> newScopeMap = new HashMap<>();
        symbolStack.addElement(newScopeMap);
    }

    public void closeScope() {
        if(symbolStack.size()==1)
            throw new RuntimeException();
        symbolStack.remove(symbolStack.size() - 1);
    }

    public boolean add(String id, Symbol type) {
        Map<String, Symbol> top = symbolStack.lastElement();
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
        return Objects.requireNonNull(getSymbol(id)).type;
    }
    public int getAddress(String id)
    {
        return Objects.requireNonNull(getSymbol(id)).address;
    }

    public int getSize(String id) {
        return getSymbol(id).size;
    }
    public boolean isShared(String id)
    {
        return Objects.requireNonNull(getSymbol(id)).isShared;
    }

    public boolean isPointer(String id) {
        return getSymbol(id).isPointer;
    }
    public Symbol getSymbol(String id)
    {
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

    public SymbolTable deepCopy() {
        SymbolTable clonedSymbolTable = new SymbolTable();

        for (Map<String, Symbol> scopeMap : symbolStack) {
            Map<String, Symbol> clonedScopeMap = new HashMap<>();

            for (Map.Entry<String, Symbol> entry : scopeMap.entrySet()) {
                String key = entry.getKey();
                Symbol value = entry.getValue().deepCopy(); // Assuming Symbol class implements deepCopy()

                clonedScopeMap.put(key, value);
            }

            clonedSymbolTable.symbolStack.add(clonedScopeMap);
        }

        return clonedSymbolTable;
    }
}