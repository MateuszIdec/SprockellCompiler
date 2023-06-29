package errors;

import org.antlr.v4.runtime.ParserRuleContext;
import ut.pp.Attrs;

public class NameNotFoundError extends CompilerError {
    public NameNotFoundError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + " Name not found: variable \"" + attrs.name + "\" is not in visible scopes";
    }
}
