package seedu.address.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RecipeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recipe recipe = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recipe.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameRecipe(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameRecipe(null));

        // different phone and email -> returns false
        Recipe editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameRecipe(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameRecipe(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRecipe(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRecipe(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameRecipe(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recipe aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different recipe -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Recipe editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
