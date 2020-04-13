package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2101;
import static seedu.address.testutil.TypicalModules.CS2103T;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ModuleBuilder;

public class ModuleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null, null));
    }

    @Test
    public void isSameModule() {
        //same object -> returns true
        assertTrue(CS2103T.isSameModule(CS2103T));

        //null -> return false
        assertFalse(CS2103T.isSameModule(null));

        // different module code -> returns false
        Module otherModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS2101).build();
        assertFalse(CS2103T.isSameModule(otherModule));

        // same name, different description -> returns true
        otherModule = new ModuleBuilder().withDescription(VALID_DESCRIPTION_CS2101).build();
        assertTrue(CS2103T.isSameModule(otherModule));

        // same name, same description -> returns true
        otherModule = new ModuleBuilder().build();
        assertTrue(CS2103T.isSameModule(otherModule));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module otherModule = new ModuleBuilder().build();
        assertTrue(CS2103T.equals(otherModule));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different facilitator -> returns false
        otherModule = CS2101;
        assertFalse(CS2103T.equals(otherModule));

        // different module code -> returns false
        otherModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS2101).build();
        assertFalse(CS2103T.equals(otherModule));

        // different description -> returns false
        otherModule = new ModuleBuilder().withDescription(VALID_DESCRIPTION_CS2101).build();
        assertFalse(CS2103T.equals(otherModule));
    }
}
