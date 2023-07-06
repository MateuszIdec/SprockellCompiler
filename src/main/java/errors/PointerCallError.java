package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerCallError extends CompilerError{
    public PointerCallError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Pointer call error: variable \"" + attrs.name + "\" is not a pointer";
    }
}
