package nasa.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {

    @Test
    public void isValidModuleName_validModuleName_true() {
        boolean result = ModuleName.isValidModuleName("Programming Methodology II");
        assertEquals(true, result);
    }

    @Test
    public void isValidModuleName_invalidModuleName_false() {
        /*
         * No special characters, only whitespaces or empty strings
         */
        assertEquals(false, ModuleName.isValidModuleName("123@@*"));
        assertEquals(false, ModuleName.isValidModuleName("\t\t\t\n"));
        assertEquals(false, ModuleName.isValidModuleName(""));
        assertEquals(false, ModuleName.isValidModuleName(" "));
    }
}
