package ut.pp;

public class RedefinitonError extends CompilerError {

    public RedefinitonError(int line_nr, int column_nr, Attrs attrs) {
        super(line_nr, column_nr, attrs);
    }

    @Override
    public String getText(){
        return getErrorHeader() + "Redefinition of: " + attrs.name;
    }
}
