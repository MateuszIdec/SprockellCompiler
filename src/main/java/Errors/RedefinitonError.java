package Errors;

import org.antlr.v4.runtime.ParserRuleContext;
import ut.pp.Attrs;

public class RedefinitonError extends CompilerError {

    public RedefinitonError(ParserRuleContext ctx, Attrs attrs) {
        super(ctx, attrs);
    }

    @Override
    public String getText(){
        return getErrorHeader() + "Redefinition of: " + attrs.name;
    }
}
