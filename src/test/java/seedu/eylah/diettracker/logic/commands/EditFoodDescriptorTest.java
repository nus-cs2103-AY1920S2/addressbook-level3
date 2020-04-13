package seedu.eylah.diettracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.DESC_PASTA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.DESC_PIZZA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_CALORIES_PIZZA;
//import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_DATE_PIZZA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_NAME_PIZZA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.VALID_TAG_FASTFOOD;

import org.junit.jupiter.api.Test;

import seedu.eylah.diettracker.logic.commands.EditCommand.EditFoodDescriptor;
import seedu.eylah.diettracker.testutil.EditFoodDescriptorBuilder;

public class EditFoodDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditFoodDescriptor descriptorWithSameValues = new EditFoodDescriptor(DESC_PASTA);
        assertTrue(DESC_PASTA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PASTA.equals(DESC_PASTA));

        // null -> returns false
        assertFalse(DESC_PASTA.equals(null));

        // different types -> returns false
        assertFalse(DESC_PASTA.equals(5));

        // different values -> returns false
        assertFalse(DESC_PASTA.equals(DESC_PIZZA));

        // different name -> returns false
        EditFoodDescriptor editedPasta = new EditFoodDescriptorBuilder(DESC_PASTA).withName(VALID_NAME_PIZZA).build();
        assertFalse(DESC_PASTA.equals(editedPasta));

        // different calories -> returns false
        editedPasta = new EditFoodDescriptorBuilder(DESC_PASTA).withCalories(VALID_CALORIES_PIZZA).build();
        assertFalse(DESC_PASTA.equals(editedPasta));

        // different date -> returns false
        // editedPasta = new EditFoodDescriptorBuilder(DESC_PASTA).withDate(VALID_DATE_PIZZA).build();
        // assertFalse(DESC_PASTA.equals(editedPasta));

        // different tags -> returns false
        editedPasta = new EditFoodDescriptorBuilder(DESC_PASTA).withTags(VALID_TAG_FASTFOOD).build();
        assertFalse(DESC_PASTA.equals(editedPasta));
    }
}
