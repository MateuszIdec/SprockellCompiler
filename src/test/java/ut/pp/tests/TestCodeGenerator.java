package ut.pp.tests;

import code_generation.CodeGenerator;
import org.junit.Before;
import org.junit.Test;

public class TestCodeGenerator {
    CodeGenerator codeGenerator;

    @Before
    public void setup() {
        codeGenerator = new CodeGenerator();
    }

    @Test
    public void emptyProg() {
        codeGenerator.appendCode("test");
        codeGenerator.appendCode("test1");

        System.out.println(codeGenerator.getCode());
    }
}
