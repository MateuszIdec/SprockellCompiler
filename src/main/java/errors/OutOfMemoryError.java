package errors;

import org.antlr.v4.runtime.ParserRuleContext;
import code_generation.Attrs;

public class OutOfMemoryError extends CompilerError{
    public OutOfMemoryError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Out of memory!";
    }
}
