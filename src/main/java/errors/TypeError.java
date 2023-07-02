package errors;

import org.antlr.v4.runtime.ParserRuleContext;
import ut.pp.Attrs;
import ut.pp.Type;

public class TypeError extends CompilerError{
    Type expected_type;
    public TypeError(ParserRuleContext ctx, Attrs name,Attrs value) {
        super(ctx, name);
         this.expected_type = value.type;
    }

    public TypeError(ParserRuleContext ctx, Attrs name,Type type) {
        super(ctx, name);
        this.expected_type = type;
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Type mismatch: variable \"" + attrs.name + "\" has a type " + attrs.type +", but has been assigned " + expected_type;
    }

    public String getTextForLock() {
        return getErrorHeader() + "Type mismatch: variable \"" + attrs.name +"\" has a type " + attrs.type + ", but lock statement doesn't allow this type";
    }
}
