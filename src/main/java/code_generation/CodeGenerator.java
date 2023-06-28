package code_generation;
import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import antlr4.ut.pp.parser.Visitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.Path;

public class CodeGenerator {

    public static ArrayList<String> generateCode(String text)
    {
        Visitor visitor = new Visitor();
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();
        visitor.visit(tree);
        ArrayList<String> code = visitor.getCode();
        ArrayList<String> result = new ArrayList<>();
        for(String threadCode : code) {
            result.add("module Main where \n\nimport Sprockell \n\nprog::[Instruction] \n"
                    + prettyCode(threadCode) + "\n\nmain = run [prog]");
        }
        return result;
    }

    public static boolean compileFile(String input, String output) throws IOException {
        Path inputPath = FileSystems.getDefault().getPath("", input);
        Path outputPath = FileSystems.getDefault().getPath("", output);

        String result = new String(Files.readAllBytes(inputPath));

        File outputFile = new File(outputPath.toString());
        String code = generateCode(result).get(0);
        FileWriter fileWriter = new FileWriter(outputFile);
        fileWriter.write(code);
        fileWriter.close();

        return true;

    }
    public static String prettyCode(String code) {
        StringBuilder result = new StringBuilder();

        for(int x = 0; x < code.length(); x++) {
            if(code.charAt(x) == ',') {
                result.append("\n      ,");
            }
            else {
                result.append(code.charAt(x));
            }
        }
        return result.toString();
    }
}
