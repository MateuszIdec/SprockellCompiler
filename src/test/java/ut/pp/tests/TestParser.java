package ut.pp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import antlr4.ut.pp.parser.MyLangLexer;
import antlr4.ut.pp.parser.MyLangParser;
import code_generation.Visitor;
import errors.PointerCallError;
import errors.PointerDefinitionError;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;
import errors.NameNotFoundError;
import errors.TypeError;


public class TestParser {
    static Visitor visitor;
    static MyLangParser parser;

    @Before
    public void setup() {
        visitor = new Visitor();
    }

    /**
     * Clears the {@code Visitor.errorVector}, {@code Visitor.symbolTables} and then invokes {@code Visitor.visit()} for a parse tree
     * generated from {@code text}
     * @param text string to parse
     * @return size of the error vector in Visitor
     */
    public int parseString(String text) {
        visitor.clearErrorVector();
        visitor.clearSymbolTables();

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        visitor.visit(tree);
        System.out.println();
        return visitor.getErrorVector().size();
    }
    @Test
    public void testSimplestExpression_statement()
    {
        String input = ";";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testJustVariableDef()
    {
        String input = "var x = 0;";

        assertEquals(0, parseString(input));
    }

    @Test
    public void testReassignment()
    {
        String input = "var x = 0; x = 1;";

        assertEquals(0, parseString(input));
    }

    @Test
    public void testInLocalScope()
    {
        String input = "{var x = 0;} x = 1;";

        parseString(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input1 = "var x = 5; { var x = 3; }";
        assertEquals(0, parseString(input1));
    }

    @Test
    public void testTypeError()
    {
        String input = "var x = 1; x = True;";
        parseString(input);

        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testTypeErrorBool()
    {
        String input = "var x = True; x = 1;";
        parseString(input);

        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testManyNestedScopes()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} x = True;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testManyNestedScopesError()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = 1;";
        parseString(input);

        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testScopes_and_type_error_order()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = False;";
        parseString(input);

        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testAssignmentPlusEq()
    {
        String input = "var x = 0; x = x + 1;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testAssignmentMinusEq()
    {
        String input = "var x = 0; x = x - 1;";

        assertEquals(0, parseString(input));
    }
//    @Test
//    public void testRelation()
//    {
//        String input = "var a = True; var b = False; var c = a <= b;";
//
//        assertEquals(0, parseString(input));
//    }
    @Test
    public void testManyRelations()
    {
        String input = "var a = True; var b = False; var c = a <= b > True < False;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testFork() {
        String input = "var x = fork {var y = 0;}; y = 2; var z = 2; join z;";

        assertEquals(2, parseString(input));
    }
    @Test
    public void testLock() {
        String input = "var x = fork { var y = 0;}; var y = 5; lock x;";
        assertEquals(1, parseString(input));

        String input1 = "lock x;";
        assertEquals(1, parseString(input1));
    }
    @Test
    public void testJoin() {
        String input = "var x = fork { var y = 0;}; var y = 5; join y;";

        assertEquals(1, parseString(input));
    }
    @Test
    public void testTid(){
        String input = "var x = Tid; x = 2;";

        assertEquals(0,parseString(input));
    }
    @Test
    public void testPrint() {
        String input = "var x = 5; print x + 2;";
        assertEquals(0, parseString(input));

        String input1 = "print x;";
        assertEquals(1, parseString(input1));
    }
    @Test
    public void testIf() {
        String input = "var x = 0; if y == 5 { y = 2;}";
        String input1 = "var x = 0; if x == 0 { var x = 2; }";
        String input2 = "var x = 0; if x == 0 { x = True; }";

        assertEquals(2, parseString(input));
        assertEquals(0,parseString(input1));
        assertEquals(1,parseString(input2));
    }
    @Test
    public void testWhile() {
        String input = "var x = 0; while y < 10 { y = y + 1; }";
        String input1 = "var x = 0; while x < 5 { x = True; }";

        assertEquals(3, parseString(input));
        assertEquals(1,parseString(input1));
    }

    @Test
    public void testArray() {
        String input = "var x = [1,True,3];";
        assertEquals(1, parseString(input));

        String input1 = "var x = [1,2,3]; var y = x[2];";
        assertEquals(0, parseString(input1));

        String input2 = "var x = [1,2,3]; var y = True; y = x[2];";
        assertEquals(1, parseString(input2));

        String input3 = "var x = [1,2,3]; print x;";
        assertEquals(1, parseString(input3));
    }

    @Test
    public void testString() {
        String input = "var x = \"a\";";
        assertEquals(0, parseString(input));

        String input1 = "var x = \"1ab2\"; print x;";
        assertEquals(0, parseString(input1));
    }

    @Test
    public void testPointer() {
        String input = "var *x = 5;";
        parseString(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof PointerDefinitionError);

        String input1 = "var x = 5; print *x;";
        parseString(input1);
        assertTrue(visitor.getErrorVector().get(0) instanceof PointerCallError);

        String input2 = "var x = 5; var ptr = &x; ptr = x;";
        assertEquals(0, parseString(input2));

    }
}