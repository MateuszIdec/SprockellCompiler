package code_generation;

import java.util.ArrayList;
import java.util.Stack;

public class MemoryManager {
    static int GLOBAL_MEMORY_SIZE = 8;
    private Integer[] globalMemory = new Integer[GLOBAL_MEMORY_SIZE];
    private Stack<Integer> freeGlobalMemoryAddresses = new Stack<>();
    private int currentGlobalIndex = 0;

    private ArrayList<LocalMemoryManager> localMemoryManagers = new ArrayList<>();

    public MemoryManager() {
        for(int i = GLOBAL_MEMORY_SIZE; i >=0 ; i--)
        {
            freeGlobalMemoryAddresses.push(i);
        }
    }

    private class  LocalMemoryManager
    {
        int LOCAL_MEMORY_SIZE = 32;
        String[] registers = {"regA", "regB", "regC", "regD", "regD", "regE", "regF"};
        private int currentRegisterIndex = 0;
        private int currLocalIndex = 0;
        ArrayList<String> currRegisters = new ArrayList<>(); // List of registers that are currently being used
        Stack<String> deallocatedRegisters = new Stack<>();
        Integer[] localMemory = new Integer[LOCAL_MEMORY_SIZE];

        public String allocateRegister()
        {
            if(deallocatedRegisters.size() == 0 )
            {
                if(currentRegisterIndex < registers.length)
                {
                    String reg = registers[currentRegisterIndex];
                    currentRegisterIndex++;
                    currRegisters.add(reg);
                    return reg;
                }
                else
                {
                    // Not enough registers!!!
                }
            }
            else
            {
                return deallocatedRegisters.pop();
            }
            return null;
        }
        public void deallocateRegister(String regName)
        {
            // maybe add check the regName in registers
            deallocatedRegisters.push(regName);
        }
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
        if(freeGlobalMemoryAddresses.size() != 0)
            return freeGlobalMemoryAddresses.pop();
        throw new Exception();
    }
    public void deallocateGlobalVariable(int address) throws Exception
    {
        if(address < GLOBAL_MEMORY_SIZE)
            freeGlobalMemoryAddresses.push(address);
        throw new Exception();
    }

    public void createNewLocalMemoryManager()
    {
        localMemoryManagers.add(new LocalMemoryManager());
    }
    public String allocateRegister(int localMemoryManagerIndex)
    {
        return localMemoryManagers.get(localMemoryManagerIndex).allocateRegister();
    }
    public void deallocateRegister(int localMemoryManagerIndex, String regName)
    {
        localMemoryManagers.get(localMemoryManagerIndex).deallocateRegister(regName);
    }
    public int createNewVariable(int localMemoryManagerIndex, int size)
    {
        return localMemoryManagers.get(localMemoryManagerIndex).createNewVariable(size);
    }
    public void updateLocalVar(int localMemoryManagerIndex, int address, int size) throws Exception
    {
        localMemoryManagers.get(localMemoryManagerIndex).updateLocalVar(address, size);
    }


}
