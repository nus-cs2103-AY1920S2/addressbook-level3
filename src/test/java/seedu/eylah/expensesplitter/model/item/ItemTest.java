package seedu.eylah.expensesplitter.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.eylah.expensesplitter.testutil.TypicalItem.BEERTOWER;
import static seedu.eylah.expensesplitter.testutil.TypicalItem.POPCORN;
import static seedu.eylah.expensesplitter.testutil.TypicalItem.STEAMBOAT;

import org.junit.jupiter.api.Test;

import seedu.eylah.expensesplitter.testutil.ItemBuilder;

public class ItemTest {

    @Test
    public void isSameItem() {
        // same item -> returns true
        assertTrue(BEERTOWER.isSameItem(BEERTOWER));

        // null -> returns false
        assertFalse(BEERTOWER.isSameItem(null));

        // ADD AFTER COMMANDS INCORPORATED
        //// different name -> returns false
        //Food editedPasta = new FoodBuilder(PASTA).withName(VALID_NAME_BOB).build();
        //assertFalse(PASTA.isSameFood(editedPasta));
        //
        //// same name, same phone, same email, different attributes -> returns true
        //editedPasta = new FoodBuilder(PASTA).withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(PASTA.isSameFood(editedPasta));
    }

    @Test
    public void equals() {
        // same Item values -> returns true
        Item steamboatCopy = new ItemBuilder(STEAMBOAT).build();
        assertTrue(STEAMBOAT.equals(steamboatCopy));

        // same object -> returns true
        assertTrue(STEAMBOAT.equals(STEAMBOAT));

        // null -> returns false
        assertFalse(STEAMBOAT.equals(null));

        // different name -> returns false
        assertFalse(STEAMBOAT.equals(BEERTOWER));

        // different item -> returns false
        assertFalse(STEAMBOAT.equals(POPCORN));

        // IMPLEMENT AFTER ADDING COMMANDS
        //// different name -> returns false
        //Food editedAlice = new FoodBuilder(PASTA).withName(VALID_NAME_BURGER).build();
        //assertFalse(PASTA.equals(editedAlice));
        //
        //// different tags -> returns false
        //editedAlice = new FoodBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        //assertFalse(ALICE.equals(editedAlice));
    }


}
