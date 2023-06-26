package ut.pp;

public class TypeError extends CompilerError{
    public TypeError(int line_nr, int column_nr, Attrs attrs) {
        super(line_nr, column_nr, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + ": Type mismatch: " + attrs.name;
    }
}
