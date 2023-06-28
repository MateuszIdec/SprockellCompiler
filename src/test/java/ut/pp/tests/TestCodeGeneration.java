package ut.pp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import antlr4.ut.pp.parser.Visitor;
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

    @Before
    public void setup() {
        visitor = new Visitor();
    }

    @Test
    public void varDefinition() {
        String text = "var x = 1 + 2 * 3 + 4;";
        generateCode(text);

//        assertEquals("prog = [Load (ImmValue 10) regA, Store regA (DirAddr 0)]", visitor.getCode(0));

//        String text1 = "var x = 10; var y = 10;";
//        generateCode(text1);
        System.out.println(visitor.prettyCode(0));
    }
}
