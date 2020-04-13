package seedu.recipe.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.recipe.logic.commands.CommandTestUtil.DESC_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_STEP_FISH;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TIME_FISH;

import org.junit.jupiter.api.Test;

import seedu.recipe.logic.commands.recipe.EditCommand.EditRecipeDescriptor;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeDescriptor descriptorWithSameValues = new EditRecipeDescriptor(DESC_TURKEY_SANDWICH);
        assertTrue(DESC_TURKEY_SANDWICH.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_TURKEY_SANDWICH.equals(DESC_TURKEY_SANDWICH));

        // null -> returns false
        assertFalse(DESC_TURKEY_SANDWICH.equals(null));

        // different types -> returns false
        assertFalse(DESC_TURKEY_SANDWICH.equals(5));

        // different values -> returns false
        assertFalse(DESC_TURKEY_SANDWICH.equals(DESC_FISH));

        // different name -> returns false
        EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(DESC_TURKEY_SANDWICH)
                .withName(VALID_NAME_FISH).build();
        assertFalse(DESC_TURKEY_SANDWICH.equals(editedAmy));

        // different time -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_TURKEY_SANDWICH).withTime(VALID_TIME_FISH).build();
        assertFalse(DESC_TURKEY_SANDWICH.equals(editedAmy));

        // different step -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_TURKEY_SANDWICH).withSteps(VALID_STEP_FISH).build();
        assertFalse(DESC_TURKEY_SANDWICH.equals(editedAmy));
    }
}
