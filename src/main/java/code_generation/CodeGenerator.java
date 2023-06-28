package code_generation;
import ut.pp.Attrs;

import java.util.ArrayList;

public class CodeGenerator {

    /**
     * Puts value in the register
     */

    ArrayList<String> generatedCode = new ArrayList<>();

    public void appendCode(String text) {
//        if(generatedCode.length() == 9) {
//            generatedCode += text + "\n, ";
//        }
        generatedCode.add(text);

    }
    public String getCode() {
        return "prog = " + generatedCode.toString();
    }

    public boolean allocateVar(Attrs attrs, String value) {

        System.out.println("[CodeGen] allocating var, value: " + value);

        String text = "Load " + "(ImmValue " + value + ") regA";
        System.out.println(text);
        appendCode(text);

        return true;
    }
}
