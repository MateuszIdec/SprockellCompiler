package code_generation;
import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import errors.CompilerError;
import errors.LexerErrorListener;
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

    /**
     * Generate machine code for a given code
     * @param text string containing the code
     * @param consolePrint debug print of the generated code
     * @return compiled code
     * @throws Exception syntax error or contextual error
     */
    public static String generateCode(String text, boolean consolePrint) throws Exception {
        Visitor visitor = new Visitor();
        LexerErrorListener lexerErrorListener = new LexerErrorListener();
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        myLangLexer.addErrorListener(lexerErrorListener);
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        // Check if there are any token recognition errors
        if(lexerErrorListener.getSyntaxErrorsCount() > 0)
            throw new Exception("Syntax error");

        // Check if there are any syntax errors
        if(parser.getNumberOfSyntaxErrors() > 0)
            throw new Exception("Syntax error");

        visitor.visit(tree);

        // Check if there are any parsing errors
        if(visitor.errorVector.size() > 0) {
            // Print all the errors
            for(CompilerError error : visitor.getErrorVector()) {
                System.err.println(error.getText());
            }
            throw new Exception("Parsing error");
        }

        ArrayList<ArrayList<String>> code = visitor.getCode();
        StringBuilder result = new StringBuilder();
        ArrayList<String> finalCode = new ArrayList<>();
        int i = 0;
        int threadCount = 0;

        for(ArrayList<String> threadCode : code) {
            finalCode.add("prog"+(i++)+" = " + threadCode.toString());
        }

        result.append("module Main where \n\nimport Sprockell \n\n");
        for(String x : finalCode) {
            result.append(prettyCode(x)).append("\n\n");
            threadCount++;
        }

        result.append("\n\nmain = run [");
        for (int id = 0; id < threadCount; id++){
            result.append("prog").append(id);
            if(id != threadCount - 1)
                result.append(",");
            else
                result.append("]");
        }

        if(consolePrint){
            for(String x : finalCode) {
                System.out.println(prettyCodeWithLineNumbers(x));
            }
        }

        return result.toString();
    }

    /**
     * Compile the code and save it
     * @param inputFile filepath to the file containing the code to compile
     * @param outputFile filepath where the result will be saved
     * @param consolePrint debug print of the generated code
     * @return {@code true} if the compilation is successful
     */
    public static boolean compileFile(String inputFile, String outputFile, boolean consolePrint) {
        Path inputPath = FileSystems.getDefault().getPath("", inputFile);
        Path outputPath = FileSystems.getDefault().getPath("", outputFile);
        String code;
        String machineCode;

        try {
            code = new String(Files.readAllBytes(inputPath));
        }
        catch(IOException e) {
            System.err.println("Input file path \"" + inputPath + "\" does not exist");
            return false;
        }

        // Don't generate output file if there are any errors in the code generation stage
        try {
            machineCode = generateCode(code, consolePrint);
        } catch (Exception e) {
            return false;
        }

        File output = new File(outputPath.toString());

        try {
            FileWriter fileWriter = new FileWriter(output);
            fileWriter.write(machineCode);
            fileWriter.close();
        } catch(IOException e) {
            System.err.println("Writing to file \"" + outputPath + "\" failed");
            return false;
        }

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

    public static String prettyCodeWithLineNumbers(String code) {
        StringBuilder result = new StringBuilder();
        int lineNumber = 1;

        for(int x = 0; x < code.length(); x++) {
            if(code.charAt(x) == ',') {
                result.append("\n").append(lineNumber).append("     ,");
                lineNumber++;
            }
            else {
                result.append(code.charAt(x));
            }
        }

        return result.toString();
    }
}
