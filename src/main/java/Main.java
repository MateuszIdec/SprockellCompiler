import code_generation.CodeGenerator;

public class Main {
    public static void main(String[] args) {
        String input = "test.txt";
        String output = "output.hs";

        if(args.length == 2) {
            input = args[0];
            output = args[1];
        } else  if (args.length > 2){
            System.err.println("Too many arguments. Required amount of arguments is 2");
        }

        if (CodeGenerator.compileFile(input, output, false))
            System.out.println("Compilation successful");
    }
}
