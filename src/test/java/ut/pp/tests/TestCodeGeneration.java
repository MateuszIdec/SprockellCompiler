package ut.pp.tests;

import static org.junit.Assert.assertEquals;

import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import code_generation.Visitor;
import code_generation.CodeGenerator;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

public class TestCodeGeneration {
    Visitor visitor;

    /**
     * Generates machine code
     */
    public void generateCode(String text) {

        visitor = new Visitor();
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();
        visitor.visit(tree);
    }

    String fullCode(String text) {
        String prologue = "prog = [Load (ImmValue 1) regA, WriteInstr regA (DirAddr 0), ";
        String epilogue = ", WriteInstr reg0 (DirAddr 0), EndProg]";
        return prologue + text + epilogue;
    }

    @Before
    public void setup() {
        visitor = new Visitor();
    }

    @Test
    public void bankCode() throws Exception {
        String bank = "shared var x = False;\n" +
                "shared var bank = 0;\n" +
                "var t1 = fork {\n" +
                "    var i = 15;\n" +
                "    while i > 0\n" +
                "    {\n" +
                "        print 1;\n" +
                "        i = i - 1;\n" +
                "        lock x;\n" +
                "        bank = bank + i;\n" +
                "        unlock x;\n" +
                "    }\n" +
                "    print 999;\n" +
                "};\n" +
                "\n" +
                "var t2 = fork {\n" +
                "    var i = 15 ;\n" +
                "    while i > 0\n" +
                "    {\n" +
                "        print 2;\n" +
                "        i = i - 1;\n" +
                "        lock x;\n" +
                "        bank = bank + i;\n" +
                "        unlock x;\n" +
                "    }\n" +
                "    print 1000;\n" +
                "};\n" +
                "join t1;\n" +
                "join t2;\n" +
                "print bank;";
        assertEquals(bankCode, CodeGenerator.generateCode(bank, false));
    }

    @Test
    public void exclusion() throws Exception {
        CodeGenerator.reset();
        String peterson = "shared var lock0 = False;\n" +
                "shared var lock1 = False;\n" +
                "\n" +
                "shared var turn = 0;\n" +
                "\n" +
                "var thread0 = fork {\n" +
                "        lock0 = True;\n" +
                "        turn = 1;\n" +
                "        while (lock1 == True && turn == 1)\n" +
                "        {\n" +
                "            // busy wait\n" +
                "            ;\n" +
                "        }\n" +
                "        // critical section\n" +
                "\n" +
                "        // end of critical section\n" +
                "        lock0 = False;\n" +
                "};\n" +
                "var thread1 = fork {\n" +
                "        lock1 = True;\n" +
                "        turn = 0;\n" +
                "        while (lock0 == True && turn == 0)\n" +
                "        {\n" +
                "            // busy wait\n" +
                "            ;\n" +
                "        }\n" +
                "        // critical section\n" +
                "\n" +
                "        // end of critical section\n" +
                "        lock1 = False;\n" +
                "};\n" +
                "\n" +
                "join thread1;\n" +
                "join thread0;\n" +
                "\n" +
                "print 999;";

        assertEquals(petersonCode, CodeGenerator.generateCode(peterson, false));
    }

    String bankCode = "module Main where \n" +
            "\n" +
            "import Sprockell \n" +
            "\n" +
            "prog0 = [Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 0)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , Load (ImmValue 3) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 3)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , Load (ImmValue 4) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 4)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 1)\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , Load (DirAddr 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , ReadInstr (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr regA (DirAddr 0)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog1 = [ReadInstr (DirAddr 3)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 15) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Gt regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Abs 50)\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , TestAndSet (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Add regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , WriteInstr reg0 (DirAddr 1)\n" +
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 999) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 3)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog2 = [ReadInstr (DirAddr 4)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 15) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Gt regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Abs 50)\n" +
            "      , Load (ImmValue 2) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , TestAndSet (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Add regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , WriteInstr reg0 (DirAddr 1)\n" +
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 1000) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 4)\n" +
            "      , EndProg]\n" +
            "\n" +
            "\n" +
            "\n" +
            "main = run [prog0,prog1,prog2]";

    String petersonCode = "module Main where \n" +
            "\n" +
            "import Sprockell \n" +
            "\n" +
            "prog0 = [Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 0)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , WriteInstr regA (DirAddr 3)\n" +
            "      , Load (ImmValue 4) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 4)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 0)\n" +
            "      , Load (ImmValue 5) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 5)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 1)\n" +
            "      , Load (DirAddr 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , Load (DirAddr 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , Load (ImmValue 999) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr regA (DirAddr 0)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog1 = [ReadInstr (DirAddr 4)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 3)\n" +
            "      , ReadInstr (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA regB regA\n" +
            "      , Push regA\n" +
            "      , ReadInstr (DirAddr 3)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute And regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Abs 38)\n" +
            "      , Jump (Abs 12)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , WriteInstr reg0 (DirAddr 4)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog2 = [ReadInstr (DirAddr 5)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 3)\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA regB regA\n" +
            "      , Push regA\n" +
            "      , ReadInstr (DirAddr 3)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute And regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Abs 38)\n" +
            "      , Jump (Abs 12)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 2)\n" +
            "      , WriteInstr reg0 (DirAddr 5)\n" +
            "      , EndProg]\n" +
            "\n" +
            "\n" +
            "\n" +
            "main = run [prog0,prog1,prog2]";
}
