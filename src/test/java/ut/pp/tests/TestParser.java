package ut.pp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import main.antlr4.ut.pp.parser.MyLangLexer;
import main.antlr4.ut.pp.parser.MyLangParser;
import main.antlr4.ut.pp.parser.Visitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import ut.pp.NameNotFoundError;
import ut.pp.TypeError;

public class TestParser {
    @Test
    public void oneHello() {
        String input = "var x = 5; " +
                "x = True;";
        String input1 = "var x = [1,True,3];";
        String input2 = "for (var x = 5; x < 5; z+=1) { y = 4; }";
        String input3 = "var x = 2; if x == 2 { y = 3;}";

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertEquals(1, tree.getChildCount()); // 1 for Hello, 1 for EOF
    }
    @Test
    public void testJustVeriableDef()
    {
        String input = "var x = 0;";
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }

    @Test
    public void testIfReassignment()
    {
        String input = "var x = 0; x = 1;";
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }

    @Test
    public void testInLocalScope()
    {
        String input = "{var x = 0;} x = 1;";
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }

    @Test
    public void testTypeError()
    {
        String input = "var x = 1; x = True;";
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof TypeError);
    }



}
