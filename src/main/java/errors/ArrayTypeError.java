package errors;

import code_generation.Attrs;
import code_generation.Type;
import org.antlr.v4.runtime.ParserRuleContext;

public class ArrayTypeError extends TypeError {
    public ArrayTypeError(ParserRuleContext ctx, Attrs name, Type expectedType) {
        super(ctx, name, expectedType);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Type mismatch: " + "In array expected a type " + expected_type + ", got " + type + " instead";
    }
}
