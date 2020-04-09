package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.*;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static fithelper.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static fithelper.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.jupiter.api.Test;

import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import fithelper.model.entry.Type;
import fithelper.commons.core.Messages;
import fithelper.commons.core.index.Index;
import fithelper.logic.commands.EditCommand.EditEntryDescriptor;
import fithelper.model.FitHelper;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.entry.Entry;
import fithelper.testutil.EditEntryDescriptorBuilder;
import fithelper.testutil.EntryBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalFitHelper(), new UserProfile(), new WeightRecords());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Entry editedEntry = new EntryBuilder().build();
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(editedEntry).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENTRY, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry);

        Type entryType= descriptor.getType();
        if (entryType.getValue().equalsIgnoreCase("food")) {
            Model expectedModel = new ModelManager(new FitHelper(model.getFitHelper()),
                    new UserProfile(), new WeightRecords());
            expectedModel.setEntry(model.getFilteredFoodEntryList().get(0), editedEntry);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } else {
            Model expectedModel = new ModelManager(new FitHelper(model.getFitHelper()),
                    new UserProfile(), new WeightRecords());
            expectedModel.setEntry(model.getFilteredSportsEntryList().get(0), editedEntry);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        }
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastFoodEntry = Index.fromOneBased(model.getFilteredFoodEntryList().size());
        Entry lastFoodEntry = model.getFilteredFoodEntryList().get(indexLastFoodEntry.getZeroBased());

        EntryBuilder foodEntryInList = new EntryBuilder(lastFoodEntry);
        Entry editedFoodEntry = foodEntryInList.withName(VALID_NAME_FOOD).withTime(VALID_TIME_FOOD)
                .withLocation(VALID_LOCATION_FOOD).withCalorie(VALID_CALORIE_FOOD).build();

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder().withName(VALID_NAME_FOOD)
                .withTime(VALID_TIME_FOOD).withLocation(VALID_LOCATION_FOOD)
                .withCalorie(VALID_CALORIE_FOOD).build();
        EditCommand editCommand = new EditCommand(indexLastFoodEntry, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedFoodEntry);

        Model expectedModel = new ModelManager(new FitHelper(model.getFitHelper()),
                new UserProfile(), new WeightRecords());
        expectedModel.setEntry(lastFoodEntry, editedFoodEntry);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENTRY, new EditEntryDescriptor());
        Entry editedFoodEntry = model.getFilteredFoodEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedFoodEntry);

        Model expectedModel = new ModelManager(new FitHelper(model.getFitHelper()), new UserProfile(),
                new WeightRecords());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        Entry entryInFilteredFoodList = model.getFilteredFoodEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        Entry editedFoodEntry = new EntryBuilder(entryInFilteredFoodList).withName(VALID_NAME_FOOD).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENTRY,
                new EditEntryDescriptorBuilder().withName(VALID_NAME_FOOD).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedFoodEntry);

        Model expectedModel = new ModelManager(new FitHelper(model.getFitHelper()), new UserProfile(),
                new WeightRecords());
        expectedModel.setEntry(model.getFilteredFoodEntryList().get(0), editedFoodEntry);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEntryUnfilteredList_failure() {
        Entry firstFoodEntry = model.getFilteredFoodEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder(firstFoodEntry).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ENTRY, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_duplicateEntryFilteredList_failure() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);

        // edit entry in filtered list into a duplicate in address book
        Entry entryInList = model.getFitHelper().getFoodList().get(INDEX_SECOND_ENTRY.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENTRY,
                new EditEntryDescriptorBuilder(entryInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENTRY);
    }

    @Test
    public void execute_invalidEntryIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFoodEntryList().size() + 1);
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder().withName(VALID_NAME_FOOD).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEntryIndexFilteredList_failure() {
        showEntryAtIndex(model, INDEX_FIRST_ENTRY);
        Index outOfBoundIndex = INDEX_SECOND_ENTRY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFitHelper().getFoodList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditEntryDescriptorBuilder().withName(VALID_NAME_FOOD).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ENTRY, DESC_FOOD);

        // same values -> returns true
        EditEntryDescriptor copyDescriptor = new EditEntryDescriptor(DESC_FOOD);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ENTRY, DESC_FOOD)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ENTRY, DESC_FOOD)));
    }

}

