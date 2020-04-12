package nasa.model.module;

import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;
import static nasa.testutil.TypicalModules.CS2106;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.testutil.ModuleBuilder;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidModuleCode));
    }

    /*

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Module module = new ModuleBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> module.getTags().remove(0));
    }

     */

    /*
    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(CS2103T.isSameModule(CS2103T);

        // null -> returns false
        assertFalse(CS2103T.isSameModule(null));

        // different phone and email -> returns false
        Module editedAlice = new ModuleBuilder(CS2103T).withPhone(VALID_PHONE_CS2101).withEmail(VALID_EMAIL_CS2101)
        .build();
        assertFalse(CS2103T.isSameModule(editedAlice));

        // different name -> returns false
        editedAlice = new ModuleBuilder(CS2103T).withName(VALID_NAME_CS2101).build();
        assertFalse(CS2103T.isSameModule(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ModuleBuilder(CS2103T).withEmail(VALID_EMAIL_CS2101).withAddress(VALID_ADDRESS_CS2101)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(CS2103T.isSameModule(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ModuleBuilder(CS2103T).withPhone(VALID_PHONE_CS2101).withAddress(VALID_ADDRESS_CS2101)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(CS2103T.isSameModule(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ModuleBuilder(CS2103T).withAddress(VALID_ADDRESS_CS2101).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(CS2103T.isSameModule(editedAlice));
    }

     */

    //TODO: Add test method for equals, similar idea to ModuleTest
    @Test
    public void equals() {
        // same values -> returns true
        Module cs2103tCopy = new ModuleBuilder().withCode("CS2103T").withName("SOFTWARE ENGINEERING").build();
        assertTrue(CS2103T.equals(cs2103tCopy));

        // same object -> returns true
        assertTrue(CS2103T.equals(CS2103T));

        // null -> returns false
        assertFalse(CS2103T.equals(null));

        // different type -> returns false
        assertFalse(CS2103T.equals(5));

        // different module -> returns false
        assertFalse(CS2103T.equals(CS2106));

        // different code-> returns false
        Module editedcs2103t = new ModuleBuilder().withCode("CS2113").build();
        assertFalse(CS2103T.equals(editedcs2103t));

        // different name -> returns false
        editedcs2103t = new ModuleBuilder().withName("Object Oriented Programming").build();
        assertFalse(CS2103T.equals(editedcs2103t));

        /*
        // different tags -> returns false
        editedAlice = new ModuleBuilder(CS2103T).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(CS2103T.equals(editedAlice));
         */
    }
}
