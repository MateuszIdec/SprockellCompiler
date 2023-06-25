package ut.pp.tests;

import static org.junit.Assert.assertEquals;

import main.antlr4.ut.pp.parser.LangBaseVisitor;
import main.antlr4.ut.pp.parser.MyLangLexer;
import main.antlr4.ut.pp.parser.MyLangParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class TestParser {
    @Test
    public void oneHello() {
        String input = "var x = 10; " +
                "var y = True;";
        String input1 = "{ var x = 5; x = 7; x = True; }" +
                "x = 10;";

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input1));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        LangBaseVisitor visitor = new LangBaseVisitor();
        visitor.visit(tree);
        assertEquals(1, tree.getChildCount()); // 1 for Hello, 1 for EOF
    }
}
