package nasa.model.module;

import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleName(invalidName));
    }

    @Test
    public void isValidModuleName() {
        // null name
        assertThrows(NullPointerException.class, () -> ModuleName.isValidModuleName(null));

        // invalid module name
        // No special characters, only whitespaces or empty strings
        assertFalse(ModuleName.isValidModuleName("")); // empty string
        assertFalse(ModuleName.isValidModuleName(" ")); // spaces only
        assertFalse(ModuleName.isValidModuleName("^")); // only non-alphanumeric characters
        assertFalse(ModuleName.isValidModuleName("peter*")); // contains non-alphanumeric characters
        assertFalse(ModuleName.isValidModuleName("123@@*")); // only non-alphanumeric characters
        assertFalse(ModuleName.isValidModuleName("\t\t\t\n")); // contains non-alphanumeric characters


        // valid module name
        assertTrue(ModuleName.isValidModuleName("Econometrics")); // alphabets only
        assertTrue(ModuleName.isValidModuleName("12345")); // numbers only
        assertTrue(ModuleName.isValidModuleName("peter the 2nd")); // alphanumeric characters
        assertTrue(ModuleName.isValidModuleName("Software Engineering")); // with capital letters
        assertTrue(ModuleName.isValidModuleName("Programming Methodology II")); // long names
    }
}
