package errors;

import code_generation.Attrs;
import org.antlr.v4.runtime.ParserRuleContext;

public class PointerCallError extends CompilerError{
    boolean isPointer;
    public PointerCallError(ParserRuleContext ctx, Attrs attrs, boolean isPointer) {
        super(ctx, attrs);
        this.isPointer = isPointer;
    }

    @Override
    public String getText() {
        String errorString;
        if(isPointer)
            errorString = "Pointer call error: variable \"" + name + "\" can have a value of a variable it's pointing to changed by calling it with a \"*\"";
        else
            errorString = "Pointer call error: variable \"" + name + "\" is not a pointer";

        return getErrorHeader() + errorString;
    }
}
