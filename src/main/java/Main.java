import code_generation.CodeGenerator;

public class Main {
    public static void main(String[] args) {
        if (CodeGenerator.compileFile("test.txt", "output.hs", false))
            System.out.println("Compilation successful");
    }
}
