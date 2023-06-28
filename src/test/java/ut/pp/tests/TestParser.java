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
import Errors.NameNotFoundError;
import Errors.TypeError;
import org.junit.jupiter.api.BeforeEach;

public class TestParser {
    static Visitor visitor;

    @Before
    public void setup() {
        visitor = new Visitor();
    }
    @BeforeEach
    public void beforeEach() {
        visitor.error_vector.clear();
    }
    public void parseString(String text) {
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        ParseTree tree = parser.module();
        visitor.visit(tree);
    }
    @Test
    public void testSimplestExpression_statement()
    {
        String input = ";";
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testVariableDefinitionSyntaxIncomplete()
    {
        String input = "var x = 0 {}";
        parseString(input);

        // TODO Properly test this. ASK TA
    }
    @Test
    public void testJustVeriableDef()
    {
        String input = "var x = 0;";
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
    }

    @Test
    public void testReassignment()
    {
        String input = "var x = 0; x = 1;";
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
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
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
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
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testAssignmentMinusEq()
    {
        String input = "var x = 0; x -= 1;";
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
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
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testManyRelations()
    {
        String input = "var a = True; var b = False; var c = a <= b > True < False;";
        parseString(input);

        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testForLoop() {
        String input = "var y = 0; for (y; x < 5; x += 1) { y+= 1; } ";
        parseString(input);

        assertEquals(4, visitor.error_vector.size());
    }
    @Test
    public void testFork() {
        String input = "var x = fork {var y = 0;}; y = 2; var z = 2; join z;";
        parseString(input);

        assertEquals(3, visitor.error_vector.size());
    }
    @Test
    public void testLock() {
        String input = "var x = fork { var y = 0;}; var y = 5; lock x;";
        parseString(input);

        assertEquals(1, visitor.error_vector.size());
    }
    @Test
    public void testJoin() {
        String input = "var x = fork { var y = 0;}; var y = 5; join y;";
        parseString(input);

        assertEquals(1, visitor.error_vector.size());
    }
}
