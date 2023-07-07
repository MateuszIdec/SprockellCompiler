package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class PrintError extends TypeError{

    public PrintError(ParserRuleContext ctx, Attrs name) {
        super(ctx, name);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Print error: variable \"" + name +"\" has a type " + type + ", but this type cannot be printed";
    }
}
