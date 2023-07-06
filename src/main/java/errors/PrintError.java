package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintError extends CompilerError {
    public PrintError(ParserRuleContext ctx, Attrs name) {
        super(ctx, name);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "\"" + attrs.name + "\" cannot be printed, because it has a type " + attrs.type;
    }
}
