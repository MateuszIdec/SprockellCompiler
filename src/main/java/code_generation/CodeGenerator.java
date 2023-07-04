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

/**
 * Responsible for generating the machine code from the source language.
 */
public class CodeGenerator {
    private static final ArrayList<ArrayList<String>> code = new ArrayList<>(new ArrayList<>());
    private static int threadID = 0;
    private static int threadCounter = 1;
    private static int scopeID = 0;

    public static int getCurrentCodeSize() {
        return code.get(threadID).size();
    }

    /**
     * Generate machine code for a given code.
     * @param text string containing the code.
     * @param consolePrint debug print of the generated code.
     * @return compiled code.
     * @throws Exception syntax error or contextual error.
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

        if(consolePrint) {
                System.out.println(prettyCodeWithLineNumbers(result.toString()));
            }

        return result.toString();
        }


    /**
     * Compile the code and save it.
     * @param inputFile filepath to the file containing the code to compile.
     * @param outputFile filepath where the result will be saved.
     * @param consolePrint debug print of the generated code.
     * @return {@code true} if the compilation is successful.
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

    /**
     * Machine code components to create instructions.
     */
    public static class MachineCode {

        public static void popRegister(String register) {
            code.get(threadID).add("Pop " + register);
        }

        public static void pushRegister(String register) {
            code.get(threadID).add("Push " + register);
        }

        public static void receiveRegister(String register) {
            code.get(threadID).add("Receive " + register);
        }

        public static void writeInstrFromRegA(int address) {
            code.get(threadID).add("WriteInstr regA (DirAddr " + address + ")");
        }

        public static void storeFromRegA(int address) {
            code.get(threadID).add("Store regA (DirAddr " + address + ")");
        }

        public static void computeEqual() {
            code.get(threadID).add("Compute Equal regA reg0 regA");
        }

        public static void loadDirAddr(int address) {
            code.get(threadID).add("Load (DirAddr " + address + ") regA");
        }

        public static void loadImmediate(String primitiveTypeValue) {
            code.get(threadID).add("Load (ImmValue " + primitiveTypeValue +") regA");
        }

        public static void computeOperationCode(String operationCode) {
            code.get(threadID).add("Compute " + operationCode + "regA regB regA");
        }

        public static void branchWithRel(String register, String relValue) {
            code.get(threadID).add("Branch " + register + " (Rel " + "(" + relValue + "))");
        }

        public static void readInstrWithInd(String address) {
            code.get(threadID).add("ReadInstr (IndAddr " + address + ")");
        }

        public static void branchReserveLine() {
            code.get(threadID).add("LINE RESERVED FOR BRANCH");
        }

        public static void jump(int startOfWhile) {
            code.get(threadID).add("Jump (Abs " + startOfWhile + ")");
        }

        public static void readInstrWithDirAddr(int address) {
            code.get(threadID).add("ReadInstr (DirAddr "+ address +")");
        }
        public static String getOperationCode(String operator)
        {
            switch (operator) {
                case "+":
                    return  "Add ";
                case "-":
                    return "Sub ";
                case "*":
                    return "Mul ";
                case "==":
                    return "Equal ";
                case "!=":
                    return "NEq ";
                case ">":
                    return "Gt ";
                case "<":
                    return "Lt ";
                case "||":
                    return "Or ";
                case "&&":
                    return "And ";
            }
            return null;
        }

        /**
         * Actions created from machine code instructions.
         */
        public static class Action {
            static int currentInstructionNumber;

            public static void progStart(int globalVarAddress) {
                if (code.size() < threadID + 1) {
                    code.add(new ArrayList<>());
                }

                loadImmediate("1");
                writeInstrFromRegA(globalVarAddress);
            }

            public static void progEnd(int globalVarAddress) {
                writeInstrFromRegA(globalVarAddress);
                code.get(threadID).add("EndProg");
            }

            public static void ifStatementBegin() {
                currentInstructionNumber = code.size();
                branchReserveLine();
            }

            public static void ifStatementEnd() {
                int instructionNumberAfterIfBody = code.size();
                int label = instructionNumberAfterIfBody - currentInstructionNumber;
                branchClaimReserved(currentInstructionNumber, "Branch regA (Rel " + label +")");
            }

            public static void whileStatementBegin() {
                popRegister("regA");
                computeEqual();
                branchReserveLine();
            }

            public static void whileStatementEnd(int startOfWhile, int branchInstructionNumber) {
                jump(startOfWhile);

                int instructionNrAfterBody = CodeGenerator.getCurrentCodeSize();

                String branchInstruction = "Branch regA (Abs " + instructionNrAfterBody + ")";
                CodeGenerator.MachineCode.Action.branchClaimReserved(branchInstructionNumber + 2, branchInstruction);
            }

            public static void branchClaimReserved(int branchReservedLineID, String branchInstruction) {
                code.get(threadID).set(branchReservedLineID, branchInstruction);
            }

            public static void readIO() {
                code.get(threadID).add("ReadInstr numberIO");
                receiveRegister("regA");
                pushRegister("regA");
            }

            public static void writeIO() {
                popRegister("regA");
                code.get(threadID).add("WriteInstr regA numberIO");
            }

            public static void threadID(int address) {
                loadImmediate(Integer.toString(address));
                pushRegister("regA");
            }

            public static void forkInitialization(int newThreadAddress) {
                scopeID = threadID;
                threadID = threadCounter;
                threadCounter++;
                code.add(new ArrayList<>());
                readInstrWithDirAddr(newThreadAddress);
                receiveRegister("regA");
                computeEqual();
                branchWithRel("regA", "-3");
            }

            public static void forkFinish(int newThreadAddress) {
                code.get(threadID).add("WriteInstr " + "reg0 " +  "(DirAddr "+ newThreadAddress + ")");
                code.get(threadID).add("EndProg");
                threadID = scopeID;
            }

            public static void forkEnd(int newThreadAddress) {
                loadImmediate(Integer.toString(newThreadAddress));
                pushRegister("regA");
                loadImmediate("1");
                writeInstrFromRegA(newThreadAddress);
            }

            public static void join() {
                popRegister("regA");
                readInstrWithInd("regA");
                receiveRegister("regB");
                branchWithRel("regB", "-2");
            }

            public static void testLockLoop(int address ) {
                code.get(threadID).add("TestAndSet (DirAddr "+ address +")");
                receiveRegister("regA");
                computeEqual();
                branchWithRel("regA","-3");
            }

            public static void placeLock(int address) {
                code.get(threadID).add("WriteInstr reg0 (DirAddr "+ address +")");
            }
        }
    }
}
