package ut.pp;

import code_generation.CodeGenerator;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        CodeGenerator.compileFile("test.txt", "output.hs");
    }
}
