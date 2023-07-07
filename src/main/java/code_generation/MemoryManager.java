package code_generation;

import java.util.ArrayList;
import java.util.Stack;

public class MemoryManager {
    static int GLOBAL_MEMORY_SIZE = 8;
    private Stack<Integer> freeGlobalMemoryAddresses = new Stack<>();

    private ArrayList<LocalMemoryManager> localMemoryManagers = new ArrayList<>();

    public MemoryManager() {
        for(int i = GLOBAL_MEMORY_SIZE-1; i >=0 ; i--)
        {
            freeGlobalMemoryAddresses.push(i);
        }
    }

    private class  LocalMemoryManager
    {
        int LOCAL_MEMORY_SIZE = 32;
        private int currLocalIndex = 0;
        Integer[] localMemory = new Integer[LOCAL_MEMORY_SIZE];


        public int createNewVariable(int size) throws OutOfMemoryError
        {
            if(currLocalIndex + size < LOCAL_MEMORY_SIZE)
            {
                int address = currLocalIndex;
                currLocalIndex += size;
                return address;
            }
            // Can not allocate new variable.
            throw new OutOfMemoryError();
        }
        public void updateLocalVar(int address, int newValue) throws Exception
        {
            // is valid address
            if(address >= LOCAL_MEMORY_SIZE)
            {
                throw new Exception();
            }
            localMemory[address] = newValue;
        }

    }

    public int allocateGlobalVariable() throws Exception
    {
        if(freeGlobalMemoryAddresses.size() >= 1)
            return freeGlobalMemoryAddresses.pop();
        throw new Exception();
    }
    public void deallocateGlobalVariable(int address) throws Exception
    {
        if(address < GLOBAL_MEMORY_SIZE)
            freeGlobalMemoryAddresses.push(address);
        throw new Exception();
        // USE IT IN JOIN
    }

    public void createNewLocalMemoryManager()
    {
        localMemoryManagers.add(new LocalMemoryManager());
    }
    public int createNewVariable(int localMemoryManagerIndex, int size)
    {
        return localMemoryManagers.get(localMemoryManagerIndex).createNewVariable(size);
    }


}
