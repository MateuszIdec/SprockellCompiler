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
    private static ArrayList<ArrayList<String>> code = new ArrayList<>(new ArrayList<>());
    private static int threadID = 0;
    private int threadCounter = 1;

    public void incrementThreadID() {
        threadID++;
    }
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
        // TODO remove when code generation will happen in CodeGenerator
        code = visitor.getCode();

        // Check if there are any parsing errors
        if(visitor.getErrorVector().size() > 0) {
            // Print all the errors
            for(CompilerError error : visitor.getErrorVector()) {
                System.err.println(error.getText());
            }
            throw new Exception("Parsing error");
        }

        StringBuilder result = new StringBuilder();
        int threadCount = 0;


        result.append("module Main where \n\nimport Sprockell \n\n");
        for(ArrayList<String> threadCode : code) {
            result.append("prog").append(threadCount++).append(" = ");
            result.append(prettyCode(threadCode.toString())).append("\n\n");
        }

        result.append("\n\nmain = run [");
        for(int id = 0; id < threadCount - 1; id++) {
            result.append("prog").append(id);
                result.append(",");
        }
        result.append("prog").append(threadCount - 1).append("]");

        if(consolePrint){
                System.out.println(prettyCodeWithLineNumbers(result.toString()));
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
        String inputFileCode;
        String machineCode;

        try {
            inputFileCode = new String(Files.readAllBytes(inputPath));
        }
        catch(IOException e) {
            System.err.println("Input file path \"" + inputPath + "\" does not exist");
            return false;
        }

        // Don't generate output file if there are any errors in the code generation stage
        try {
            machineCode = generateCode(inputFileCode, consolePrint);
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

    private static String prettyCode(String code) {
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

    private static String prettyCodeWithLineNumbers(String code) {
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
    public static class MachineCode {

        public static void popRegA() {
            code.get(threadID).add("Pop regA");
        }

        public static void popRegB() {
            code.get(threadID).add("Pop regB");
        }

        public static void pushRegA() {
            code.get(threadID).add("Push regA");
        }

        public static void progStart(int globalVarAddress) {
            if (code.get(threadID) == null) {
                code.add(new ArrayList<>());
            }

            code.get(threadID).add("Load (ImmValue 1) regA");
            code.get(threadID).add("WriteInstr regA (DirAddr " + globalVarAddress + ")");
        }

        public static void progEnd(int globalVarAddress) {
            code.get(threadID).add("WriteInstr " + "reg0 " + "(DirAddr " + globalVarAddress + ")");
            code.get(threadID).add("EndProg");
        }

        public static void writeInstrFromRegA(int address) {
            popRegA();
            code.get(threadID).add("WriteInstr regA (DirAddr " + address + ")");
        }

        public static void storeFromRegA(int address) {
            popRegA();
            code.get(threadID).add("Store regA (DirAddr " + address + ")");
        }

        public static void computeEqual() {
            code.get(threadID).add("Compute Equal regA reg0 regA");
        }

        public static void computeOperationCode(String operationCode) {
            code.get(threadID).add("Compute " + operationCode + "regA regB regA");
        }

        public static void branchWithRel(String label) {
            code.get(threadID).add("Branch RegA (Rel " + label + " )");
        }

        public static void branchPartial() {
            code.get(threadID).add("Branch RegA ");
        }

        public static void branchFinishPartial(int partialBranchNumber, String instructionNumberAfterBody) {
            code.get(threadID).set(partialBranchNumber, "( Abs " + instructionNumberAfterBody + " )");
        }

        public static void jump(int startOfWhile) {
            code.get(threadID).add("Jump (Abs " + startOfWhile + ")");
        }

        public static void readIO() {
            code.get(threadID).add("ReadInstr numberIO");
            code.get(threadID).add("Receive regA");
            code.get(threadID).add("Push regA");
        }

        public static void writeIO() {
            code.get(threadID).add("WriteInstr regA numberIO");
        }

        public static void getThreadID(int address) {
            code.get(threadID).add("Load (ImmValue "+address+") regA");
            code.get(threadID).add("Push regA");
        }

        public static void readInstr(int address) {
            code.get(threadID).add("ReadInstr (DirAddr "+ address +")");
            code.get(threadID).add("Receive RegA");
        }

        public static void load(int address) {
            code.get(threadID).add("Load (DirAddr " + address + ") regA");
            pushRegA();
        }

        public static void loadImmediate(String primitiveTypeValue) {
            code.get(threadID).add("Load (ImmValue " + primitiveTypeValue +") regA");
            pushRegA();
        }

        public static void forkInitialization(int newThreadAddress) {
            code.get(threadID).add("ReadInstr (DirAddr "+ newThreadAddress + ")");
            code.get(threadID).add("Receive regA");
            code.get(threadID).add("Compute Equal regA reg0 regA");
            code.get(threadID).add("Branch regA (Rel (-3))");
        }

        public static void forkFinish(int newThreadAddress) {
            code.get(threadID).add("WriteInstr " + "reg0 " +  "(DirAddr "+ newThreadAddress + ")");
            code.get(threadID).add("EndProg");
        }

        public static void forkEnd(int newThreadAddress) {
            code.get(threadID).add("Load (ImmValue "+ newThreadAddress +") regA");
            code.get(threadID).add("Push regA");
            code.get(threadID).add("Load (ImmValue 1) regA");
            code.get(threadID).add("WriteInstr " + "regA " +  "(DirAddr "+ newThreadAddress + ")");
        }

        public static void join() {
            code.get(threadID).add("Pop regA");
            code.get(threadID).add("ReadInstr (IndAddr regA)");
            code.get(threadID).add("Receive regB");
            code.get(threadID).add("Branch regB (Rel (-2))");
        }

        public static void testLockLoop(int address ) {
            code.get(threadID).add("TestAndSet (DirAddr "+ address +")");
            code.get(threadID).add("Receive regA");
            code.get(threadID).add("Compute Equal regA reg0 regA");
            code.get(threadID).add("Branch regA (Rel (-3))");
        }

        public static void placeLock(int address) {
            code.get(threadID).add("WriteInstr reg0 (DirAddr "+ address +")");
        }
    }
}
