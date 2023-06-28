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
    public ParseTree parseString(String text) {
        MyLangLexer myLangLexer = new MyLangLexer(CharStreams.fromString(text));
        CommonTokenStream tokens = new CommonTokenStream(myLangLexer);
        MyLangParser parser = new MyLangParser(tokens);
        return parser.module();
    }
    @Test
    public void oneHello() {
        String input = "var x = 5; " +
                "{shared var x = 10;   }";
        String input1 = "var x = [1,True,3];";
        String input2 = "for (var x = 5; x < 5; z+=1) { y = 4; }";
        String input3 = "var x = 2; if x == 2 && x == True && x == True { x = 3;}";

        ParseTree tree =parseString(input3);

        visitor.visit(tree);
        assertEquals(1, tree.getChildCount()); // 1 for Hello, 1 for EOF
    }
    @Test
    public void testSimplestExpression_statement()
    {
        String input = ";";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testVariableDefinitionSyntaxIncomplete()
    {
        String input = "var x = 0 {}";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        // TODO Properly test this. ASK TA
    }
    @Test
    public void testJustVeriableDef()
    {
        String input = "var x = 0;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }

    @Test
    public void testReassignment()
    {
        String input = "var x = 0; x = 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }

    @Test
    public void testInLocalScope()
    {
        String input = "{var x = 0;} x = 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }

    @Test
    public void testTypeError()
    {
        String input = "var x = 1; x = True;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof TypeError);
    }
    @Test
    public void testTypeErrorBool()
    {
        String input = "var x = True; x = 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof TypeError);
    }
    @Test
    public void testManyNestedScopes()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} x = True;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testManyNestedScopesError()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testScopes_and_type_error_order()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = False;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertTrue(visitor.error_vector.get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testAssignmentPlusEq()
    {
        String input = "var x = 0; x += 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testAssignmentMinusEq()
    {
        String input = "var x = 0; x -= 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testAssignmentWithUnknownOperation()
    {
        String input = "var x = 0; x ?= 1;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        // TODO Properly test this. ASK TA
    }
    @Test
    public void testRelation()
    {
        String input = "var a = True; var b = False; var c = a <= b;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testManyRelations()
    {
        String input = "var a = True; var b = False; var c = a <= b > True < False;";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(0, visitor.error_vector.size());
    }
    @Test
    public void testForLoop() {
        String input = "var y = 0; for (y; x < 5; x += 1) { y+= 1; } ";
        ParseTree tree = parseString(input);

        visitor.visit(tree);
        assertEquals(4, visitor.error_vector.size());
    }
    @Test
    public void testFork() {
        String input = "var x = fork {var y = 0;}; y = 2; var z = 2; join z;";
        ParseTree tree = parseString(input);
        visitor.visit(tree);
        assertEquals(3, visitor.error_vector.size());
    }
}
