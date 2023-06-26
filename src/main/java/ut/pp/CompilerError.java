package ut.pp;

public abstract class CompilerError {
    Integer lineNr;
    Integer columnNr;
    Attrs attrs;

    public CompilerError(int line_nr, int column_nr, Attrs attrs){
        this.lineNr = line_nr;
        this.columnNr = column_nr;
        this.attrs = attrs;
    }
    public String getErrorHeader()
    {
        return"Error: line: "+ lineNr + ": column: "+columnNr + ":";
    }
    public abstract String getText();
}
