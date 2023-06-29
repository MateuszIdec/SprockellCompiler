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
import errors.NameNotFoundError;
import errors.TypeError;

public class TestParser {
    static Visitor visitor;

    @Before
    public void setup() {
        visitor = new Visitor();
    }

    /**
     * Clears the {@code visitor.error_vector} and then invokes {@code visitor.visit()} for a parse tree
     * generated from {@code text}
     * @param text string to parse
     * @return error count
     */
    public int parseString(String text) {
        visitor.error_vector.clear();

        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();

        visitor.visit(tree);
        return visitor.error_vector.size();
    }
    @Test
    public void testSimplestExpression_statement()
    {
        String input = ";";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testVariableDefinitionSyntaxIncomplete()
    {
        String input = "var x = 0 {}";
        parseString(input);

        // TODO Properly test this. ASK TA
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

        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }

    @Test
    public void testTypeError()
    {
        String input = "var x = 1; x = True;";
        parseString(input);

        assertTrue(visitor.error_vector.get(0) instanceof TypeError);
    }
    @Test
    public void testTypeErrorBool()
    {
        String input = "var x = True; x = 1;";
        parseString(input);

        assertTrue(visitor.error_vector.get(0) instanceof TypeError);
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

        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testScopes_and_type_error_order()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = False;";
        parseString(input);

        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testAssignmentPlusEq()
    {
        String input = "var x = 0; x += 1;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testAssignmentMinusEq()
    {
        String input = "var x = 0; x -= 1;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testAssignmentWithUnknownOperation()
    {
        String input = "var x = 0; x ?= 1;";
        parseString(input);

        // TODO Properly test this. ASK TA
    }
    @Test
    public void testRelation()
    {
        String input = "var a = True; var b = False; var c = a <= b;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testManyRelations()
    {
        String input = "var a = True; var b = False; var c = a <= b > True < False;";

        assertEquals(0, parseString(input));
    }
    @Test
    public void testForLoop() {
        String input = "var y = 0; for (y; x < 5; x += 1) { y+= 1; } ";

        assertEquals(4, parseString(input));
    }
    @Test
    public void testFork() {
        String input = "var x = fork {var y = 0;}; y = 2; var z = 2; join z;";

        assertEquals(3, parseString(input));
    }
    @Test
    public void testLock() {
        String input = "var x = fork { var y = 0;}; var y = 5; lock x;";

        assertEquals(1, parseString(input));
    }
    @Test
    public void testJoin() {
        String input = "var x = fork { var y = 0;}; var y = 5; join y;";

        assertEquals(1, parseString(input));
    }
    @Test
    public void testTid(){
        String input = "var x = Tid;";

        assertEquals(0,parseString(input));
    }
    @Test
    public void testPrint() {
        String input = "var x = 5; print x + 2;";

        assertEquals(0, parseString(input));
    }
}