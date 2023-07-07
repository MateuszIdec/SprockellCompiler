package ut.pp.tests;

import static org.junit.Assert.assertEquals;

import antlr4.ut.pp.parser.*;
import errors.LexerErrorListener;
import org.antlr.v4.runtime.*;
import org.junit.Before;
import org.junit.Test;


public class TestSyntax {
    static MyLangParser parser;
    static LexerErrorListener lexerErrorListener;
    int lexerErrors;
    int parserErrors;

    @Before
    public void setup() {
        lexerErrorListener = new LexerErrorListener();
    }

    /**
     * Resets LexerErrorListener error count and then invokes a sequence needed for checking of the syntax errors
     */
    public void parseString(String text) {
        lexerErrorListener.resetSyntaxErrorCount();

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        myLangLexer.addErrorListener(lexerErrorListener);
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        parser = new MyLangParser(tokens);
        parser.module();
        lexerErrors = lexerErrorListener.getSyntaxErrorsCount();
        parserErrors = parser.getNumberOfSyntaxErrors();
    }

    @Test
    public void emptyCode() {
        parseString(" ");
        assertEquals(0, lexerErrors);
        assertEquals(1, parserErrors);
    }

    @Test
    public void emptyScope() {
        // Expecting one error, because empty scope is not allowed
        parseString("{ }");
        assertEquals(0, lexerErrors);
        assertEquals(1, parserErrors);

        parseString("while x < 5 { }");
        assertEquals(0,lexerErrors);
        assertEquals(1,parserErrors);

        parseString("if x == 3 { }");
        assertEquals(0,lexerErrors);
        assertEquals(1,parserErrors);
    }

    @Test
    public void variableDef()
    {
        parseString("var x = 0; x ?= 1;");
        assertEquals(1, lexerErrors);
    }

    @Test
    public void wrongVariableDefinition() {
        // Expecting no errors, because it should be detected at elaboration phase
        parseString("varx;");
        assertEquals(0, lexerErrors);
        assertEquals(1, parserErrors);
    }

    @Test
    public void printNegativeNumber() {
        parseString("print -x;");
        assertEquals(0, lexerErrors);
        assertEquals(1, parserErrors);
    }
}