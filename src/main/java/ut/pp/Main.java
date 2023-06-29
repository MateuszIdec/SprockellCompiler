package ut.pp;

import code_generation.CodeGenerator;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        CodeGenerator.compileFile("test.txt", "output.hs");
    }
}
