package ut.pp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import antlr4.ut.pp.parser.Visitor;
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
    public void varDefinitionTid() {
        String text = "var x = Tid;";

        generateCode(text);
        assertEquals(fullCode("Load (ImmValue 0) regA, Store regA (DirAddr 0)"), visitor.getCode(0));
    }

    @Test
    public void varDefinitionWithAddition() {
        String text = "var x = 2 + 2;";

        generateCode(text);
        assertEquals(fullCode("Load (ImmValue 2) regA, Load (ImmValue 2) regB, " +
                "Compute Add regA regB regB, Store regB (DirAddr 0)"),visitor.getCode(0));
    }
    @Test
    public void printStatement() {
        String text = "print 2 + 2";

        generateCode(text);
        assertEquals(fullCode("Load (ImmValue 2) regA, Load (ImmValue 2) regB," +
                " Compute Add regA regB regB, WriteInstr regB numberIO"),visitor.getCode(0));
    }
}
