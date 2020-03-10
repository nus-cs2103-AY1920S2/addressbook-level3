package seedu.address.model.food;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFood.PASTA;
import static seedu.address.testutil.TypicalFood.BURGER;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.FoodBuilder;
import seedu.address.testutil.PersonBuilder;

public class FoodTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Food food = new FoodBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> food.getTags().remove(0));
    }

    @Test
    public void isSameFood() {
        // same object -> returns true
        assertTrue(PASTA.isSameFood(PASTA));

        // null -> returns false
        assertFalse(PASTA.isSameFood(null));

        // different name -> returns false
        Food editedAlice = new FoodBuilder(PASTA).withName(VALID_NAME_BOB).build();
        assertFalse(PASTA.isSameFood(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new FoodBuilder(PASTA)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PASTA.isSameFood(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new FoodBuilder(PASTA).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PASTA.isSameFood(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Food aliceCopy = new FoodBuilder(PASTA).build();
        assertTrue(PASTA.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PASTA.equals(PASTA));

        // null -> returns false
        assertFalse(PASTA.equals(null));

        // different type -> returns false
        assertFalse(PASTA.equals(5));

        // different person -> returns false
        assertFalse(PASTA.equals(BURGER));

        // IMPLEMENT AFTER ADDING COMMANDS
//        // different name -> returns false
//        Food editedAlice = new FoodBuilder(PASTA).withName(VALID_NAME_BURGER).build();
//        assertFalse(PASTA.equals(editedAlice));
//
//        // different tags -> returns false
//        editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
//        assertFalse(ALICE.equals(editedAlice));
    }
}
