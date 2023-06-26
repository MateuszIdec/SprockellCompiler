package ut.pp.tests;

import static org.junit.Assert.assertEquals;

import main.antlr4.ut.pp.parser.MyLangLexer;
import main.antlr4.ut.pp.parser.MyLangParser;
import main.antlr4.ut.pp.parser.Visitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class TestParser {
    @Test
    public void oneHello() {
        String input = "{ var x = a10; } " +
                "x = 2;";
        String input1 = "var x = [1,True,3];";
        String input2 = "for (var x = 5; x < 5; z+=1) { y = 4; }";
        String input3 = "var x = 2; if x == 2 { y = 3;}";

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(input3));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        Visitor visitor = new Visitor();
        visitor.visit(tree);
        assertEquals(1, tree.getChildCount()); // 1 for Hello, 1 for EOF
    }
}
