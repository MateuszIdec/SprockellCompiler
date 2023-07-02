package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class LockTypeError extends TypeError{

    public LockTypeError(ParserRuleContext ctx, Attrs name) {
        super(ctx, name);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Type mismatch: variable \"" + attrs.name +"\" has a type " + attrs.type + ", but lock statement doesn't allow this type";
    }
}
