package ut.pp;

public class NameNotFoundError extends CompilerError {
    public NameNotFoundError(int line_nr, int column_nr, Attrs attrs) {
        super(line_nr, column_nr, attrs);
    }

    @Override
    public String getText() {
        return getErrorHeader() + "Variable is not in visable scopes: " + attrs.name;
    }
}
