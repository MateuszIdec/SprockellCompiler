package ut.pp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import antlr4.ut.pp.parser.*;
import code_generation.Visitor;
import errors.LockTypeError;
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
    public int parseStringAndGetErrorCount(String text) {
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
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testVariableDef()
    {
        String input = "var x = 0;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = -6;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testVariableDefinitionWithAddExpression() {
        String input = "var x = 2 + 3;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testVariableDefinitionWithSubExpression() {
        String input = "var x = 2 - 3;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testVariableDefinitionWithMulExpression() {
        String input = "var x = 3 * 4;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testVariableDefinitionFromOtherVariable() {
        String input = "var x = 4; var y = x;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testVariableDefinitionFromOtherVariableWithAdd() {
        String input = "var x = 4; var y = x + 5";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 4; var y = 5 * x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testVariableDefinitionFromOtherVariableWithSub() {
        String input = "var x = 4; var y = x - 5";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 4; var y = 5 - x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testVariableDefinitionFromOtherVariableWithMult() {
        String input = "var x = 4; var y = x * 5";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 4; var y = 5 * x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testReassignment()
    {
        String input = "var x = 0; x = 1;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 0; x = -12;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testInLocalScope()
    {
        String input = "{var x = 0;} x = 1;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input1 = "var x = 5; { var x = 3; }";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }

    @Test
    public void testTypeErrorBoolAssignedToInt()
    {
        String input = "var x = 1; x = True;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testTypeErrorIntAssignedToBool()
    {
        String input = "var x = True; x = 1;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testManyNestedScopes()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} x = True;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testManyNestedScopesError()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = 1;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testScopes_and_type_error_order()
    {
        String input = "var x = True; {x = False; var y = 2; {var z = 3; z = 0;} y = 10;} z = False;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testAssignmentPlusEq()
    {
        String input = "var x = 0; x = x + 1;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testAssignmentMinusEq()
    {
        String input = "var x = 0; x = x - 1;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testAssignmentMul() {
        String input = "var x = 0; x = x * 2; x = x * x;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
        @Test
    public void testRelation()
    {
        String input = "var a = True; var b = False; var c = a <= b; b = c;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testManyRelations()
    {
        String input = "var a = True; var b = False; var c = a <= b > True < False;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testFork() {
        String input = "var x = fork {var y = 0;}; y = 2; var z = 2; join z;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
        assertTrue(visitor.getErrorVector().get(1) instanceof TypeError);
    }
    @Test
    public void testLockNonexistentVariable() {
        String input = "lock x;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
    }
    @Test
    public void testLockInteger() {
        String input = "var x = 5; lock x;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof LockTypeError);
    }
    @Test
    public void testLockBoolean() {
        String input = "var x = True; lock x;";
        assertEquals(1, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testLockSharedBoolean() {
        String input = "shared var x = True; lock x;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testLockFork() {
        String input = "var x = fork { var y = 0;}; lock x;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof LockTypeError);
    }
    @Test
    public void testLockSharedInteger() {
        String input = "shared var x = 5; lock x;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof LockTypeError);
    }
    @Test
    public void testJoin() {
        String input = "var x = fork { var y = 0;}; var y = 5; join y;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testTid() {
        String input = "var x = Tid; x = 2;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = Tid + 2;";
        assertEquals(0, parseStringAndGetErrorCount(input1));

        String input2 = "if Tid == 2 {;}";
        assertEquals(0, parseStringAndGetErrorCount(input2));

        String input3 = "var x = 2; if Tid == x {;} if x == Tid {;}";
        assertEquals(0, parseStringAndGetErrorCount(input3));

        String input4 = "var x = True; if Tid == x {;}";
        parseStringAndGetErrorCount(input4);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testPrintNonexistentVariable() {
        String input = "print x;";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input1 = "print x + 5";
        parseStringAndGetErrorCount(input1);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input2 = "print 5 + x";
        assertEquals(1, parseStringAndGetErrorCount(input2));
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input3 = "print x - 5";
        assertEquals(1, parseStringAndGetErrorCount(input3));
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input4 = "print 5 * x";
        assertEquals(1, parseStringAndGetErrorCount(input4));
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

        String input5 = "print x * 5";
        assertEquals(1, parseStringAndGetErrorCount(input5));
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);

    }
    @Test
    public void testPrintAddExpression() {
        String input = "print 2 + 2;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }

    @Test
    public void testPrintSubExpression() {
        String input = "print 2 - 2;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testPrintMulExpression() {
        String input = "print 2 * 5;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testPrintAddExpressionWithVariable() {
        String input = "var x = 5; print x + 5;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 5; print 5 + x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testPrintSubExpressionWithVariable() {
        String input = "var x = 5; print x - 5;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 5; print 5 - x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }
    @Test
    public void testPrintMulExpressionWithVariable() {
        String input = "var x = 5; print x * 5;";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = 5; print 5 * x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }

    @Test
    public void testPrintIntType() {
        String input = "var x = 5; print x;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testPrintBooleanType() {
        String input = "var x = True; print x;";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testPrintThreadStatusType() {
        String input = "var thread0 = fork {;}; print thread0;";
        assertEquals(0, parseStringAndGetErrorCount(input));

    }
    @Test
    public void testIfIntToBoolComparison() {
        String input = "var x = 0; if x == True {;}";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }
    @Test
    public void testIfBoolToIntComparison() {
        String input = "var x = 0; if True == x {;}";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }

    @Test
    public void testIfStatementWithThreadStatusVariable() {
        String input = "var y = 0; var x = fork {var y = 2;}; join x; if x == 1 {y = 1;} if 1 == x {y = 1;}";
        assertEquals(0, parseStringAndGetErrorCount(input));
    }
    @Test
    public void testIf() {
        String input = "var x = 0; if y == 5 { y = 2;}";
        parseStringAndGetErrorCount(input);
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
        assertTrue(visitor.getErrorVector().get(1) instanceof NameNotFoundError);

        String input1 = "var x = 0; if x == 0 { var x = 2; }";
        assertEquals(0, parseStringAndGetErrorCount(input1));

        String input2 = "var x = 0; if x == 0 { x = True; }";
        parseStringAndGetErrorCount(input2);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);

        String input3 = "var x = True; var y = 5; if x == y {x = False;}";
        parseStringAndGetErrorCount(input3);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);

        String input4 = "var x = True; var y = False; if x == y {y = False;}";
        assertEquals(0, parseStringAndGetErrorCount(input4));
    }
    @Test
    public void testWhile() {
        String input = "var x = 0; while y < 10 { y = y + 1; }";
        parseStringAndGetErrorCount(input);
        assertEquals(3, parseStringAndGetErrorCount(input));
        assertTrue(visitor.getErrorVector().get(0) instanceof NameNotFoundError);
        assertTrue(visitor.getErrorVector().get(1) instanceof NameNotFoundError);
        assertTrue(visitor.getErrorVector().get(2) instanceof NameNotFoundError);

        String input1 = "var x = 0; while x < 5 { x = True; }";
        parseStringAndGetErrorCount(input1);
        assertTrue(visitor.getErrorVector().get(0) instanceof TypeError);
    }

    @Test
    public void testArray() {
        String input = "var x = [1,True,3];";
        assertEquals(1, parseStringAndGetErrorCount(input));

        String input1 = "var x = [1,2,3]; var y = x[2];";
        assertEquals(0, parseStringAndGetErrorCount(input1));

        String input2 = "var x = [1,2,3]; var y = True; y = x[2];";
        assertEquals(1, parseStringAndGetErrorCount(input2));

//        String input3 = "var x = [1,2,3]; print x;";
//        assertEquals(1, parseStringAndGetErrorCount(input3));
    }

    @Test
    public void testString() {
        String input = "var x = \"a\";";
        assertEquals(0, parseStringAndGetErrorCount(input));

        String input1 = "var x = \"1ab2\"; print x;";
        assertEquals(0, parseStringAndGetErrorCount(input1));
    }

//    @Test
//    public void testPointer() {
//        String input = "var *x = 5;";
//        parseStringAndGetErrorCount(input);
//        assertTrue(visitor.getErrorVector().get(0) instanceof PointerDefinitionError);
//
//        String input1 = "var x = 5; print *x;";
//        parseStringAndGetErrorCount(input1);
//        assertTrue(visitor.getErrorVector().get(0) instanceof PointerCallError);
//
//        String input2 = "var x = 5; var ptr = &x; ptr = x;";
//        assertEquals(0, parseStringAndGetErrorCount(input2));
//
//    }
}