package errors;

import org.antlr.v4.runtime.ParserRuleContext;
import ut.pp.Attrs;

public class RedefinitionError extends CompilerError {

    public RedefinitionError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText(){
        return getErrorHeader() + "redefinition of \"" + attrs.name + "\"";
    }
}
