package errors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import ut.pp.Attrs;

public abstract class CompilerError {
    Integer lineNr;
    Integer columnNr;
    Attrs attrs;

    public CompilerError(ParserRuleContext ctx, Attrs attrs){
        Token token = ctx.start;
        this.lineNr = token.getLine();
        this.columnNr = token.getCharPositionInLine();
        this.attrs = attrs;
    }
    public String getErrorHeader()
    {
        return "Error (line: "+ lineNr + ", column: "+columnNr + "): ";
    }
    public abstract String getText();
}
