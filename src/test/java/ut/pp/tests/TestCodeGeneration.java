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

    @Before
    public void setup() {
        visitor = new Visitor();
    }

    @Test
    public void bankCode() throws Exception {
        String bank = "\n" +
                "\n" +
                "    shared var bank_account = 0;\n" +
                "    shared var x = False;\n" +
                "\n" +
                "    var producer1 = fork\n" +
                "    {\n" +
                "        var i = 100;\n" +
                "        while i > 0\n" +
                "        {\n" +
                "            lock x;\n" +
                "            bank_account = bank_account + 1;\n" +
                "            print bank_account;\n" +
                "            unlock x;\n" +
                "            i = i - 1;\n" +
                "        }\n" +
                "        print 111;\n" +
                "    };\n" +
                "\n" +
                "    var producer2 = fork\n" +
                "    {\n" +
                "        var i = 100;\n" +
                "        while i > 0\n" +
                "        {\n" +
                "            lock x;\n" +
                "            bank_account = bank_account + 2;\n" +
                "            print bank_account;\n" +
                "            unlock x;\n" +
                "            i = i - 1;\n" +
                "        }\n" +
                "        print 222;\n" +
                "    };\n" +
                "\n" +
                "    var consumer1 = fork\n" +
                "    {\n" +
                "        var i = 100;\n" +
                "        while i > 0\n" +
                "        {\n" +
                "            lock x;\n" +
                "            bank_account = bank_account - 1;\n" +
                "            print bank_account;\n" +
                "            unlock x;\n" +
                "            i = i - 1;\n" +
                "        }\n" +
                "        print 0-111;\n" +
                "    };\n" +
                "\n" +
                "    var consumer2 = fork\n" +
                "    {\n" +
                "        var i = 100;\n" +
                "        while i > 0\n" +
                "        {\n" +
                "            lock x;\n" +
                "            bank_account = bank_account - 2;\n" +
                "            print bank_account;\n" +
                "            unlock x;\n" +
                "            i = i - 1;\n" +
                "        }\n" +
                "        print 0-222;\n" +
                "    };\n" +
                "\n" +
                "    join producer1;\n" +
                "    join producer2;\n" +
                "    join consumer1;\n" +
                "    join consumer2;\n" +
                "    print bank_account;";
        assertEquals(bankCode, CodeGenerator.generateCode(bank, false));
    }

    String bankCode = "module Main where \n" +
            "\n" +
            "import Sprockell \n" +
            "\n" +
            "prog0 = [Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 0)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
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
            "      , Load (ImmValue 5) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 5)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 2)\n" +
            "      , Load (ImmValue 6) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , WriteInstr regA (DirAddr 6)\n" +
            "      , Pop regA\n" +
            "      , Store regA (DirAddr 3)\n" +
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
            "      , Load (DirAddr 2) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , Load (DirAddr 3) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , ReadInstr (IndAddr regA)\n" +
            "      , Receive regB\n" +
            "      , Branch regB (Rel (-2))\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 0)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog1 = [ReadInstr (DirAddr 3)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 100) regA\n" +
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
            "      , Branch regA ( Abs 51 )\n" +
            "      , TestAndSet (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Add regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 2)\n" +
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
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 111) regA\n" +
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
            "      , Load (ImmValue 100) regA\n" +
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
            "      , Branch regA ( Abs 51 )\n" +
            "      , TestAndSet (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 2) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Add regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 2)\n" +
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
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 222) regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 4)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog3 = [ReadInstr (DirAddr 5)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 100) regA\n" +
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
            "      , Branch regA ( Abs 51 )\n" +
            "      , TestAndSet (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 1) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 2)\n" +
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
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 111) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 5)\n" +
            "      , EndProg]\n" +
            "\n" +
            "prog4 = [ReadInstr (DirAddr 6)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , Load (ImmValue 100) regA\n" +
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
            "      , Branch regA ( Abs 51 )\n" +
            "      , TestAndSet (DirAddr 2)\n" +
            "      , Receive regA\n" +
            "      , Compute Equal regA reg0 regA\n" +
            "      , Branch regA (Rel (-3))\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 2) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA (DirAddr 1)\n" +
            "      , ReadInstr (DirAddr 1)\n" +
            "      , Receive regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 2)\n" +
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
            "      , Jump (Abs 8)\n" +
            "      , Load (ImmValue 0) regA\n" +
            "      , Push regA\n" +
            "      , Load (ImmValue 222) regA\n" +
            "      , Push regA\n" +
            "      , Pop regB\n" +
            "      , Pop regA\n" +
            "      , Compute Sub regA regB regA\n" +
            "      , Push regA\n" +
            "      , Pop regA\n" +
            "      , WriteInstr regA numberIO\n" +
            "      , WriteInstr reg0 (DirAddr 6)\n" +
            "      , EndProg]\n" +
            "\n" +
            "\n" +
            "\n" +
            "main = run [prog0,prog1,prog2,prog3,prog4]";
}