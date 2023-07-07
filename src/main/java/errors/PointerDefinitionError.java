package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerDefinitionError extends CompilerError{
    public PointerDefinitionError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Pointer error: variable \"" + name + "\" is a pointer and can only be assigned with other variable";
    }
}
