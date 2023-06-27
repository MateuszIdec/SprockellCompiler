package Errors;

import org.antlr.v4.runtime.ParserRuleContext;
import ut.pp.Attrs;
import ut.pp.SymbolTable;

public class TypeError extends CompilerError{
    SymbolTable.Type expected_type;
    public TypeError(ParserRuleContext ctx, Attrs name,Attrs value) {
        super(ctx, name);
         this.expected_type = value.type;
    }
    public TypeError(ParserRuleContext ctx, Attrs name,SymbolTable.Type type) {
        super(ctx, name);
        this.expected_type = type;
    }

    @Override
    public String getText() {
        return getErrorHeader() + ": Type mismatch: " + attrs.name + "is: " +" but expected to be " ;//+ expected_type.toString();
    }
}
