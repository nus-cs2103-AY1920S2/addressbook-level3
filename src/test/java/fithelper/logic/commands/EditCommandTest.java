package fithelper.logic.commands;

import static fithelper.logic.commands.CommandTestUtil.DESC_FOOD;
import static fithelper.logic.commands.CommandTestUtil.assertCommandSuccess;
import static fithelper.testutil.TypicalEntriesUtil.getTypicalFitHelper;
import static fithelper.testutil.TypicalIndexesUtil.INDEX_FIRST_ENTRY;
import static fithelper.testutil.TypicalIndexesUtil.INDEX_SECOND_ENTRY;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fithelper.logic.commands.EditCommand.EditEntryDescriptor;
import fithelper.model.FitHelper;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.UserProfile;
import fithelper.model.WeightRecords;
import fithelper.model.entry.Entry;
import fithelper.model.entry.Type;
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

        Type entryType = descriptor.getType();
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
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ENTRY, DESC_FOOD);

        // same values -> returns true
        EditEntryDescriptor copyDescriptor = new EditEntryDescriptor(DESC_FOOD);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ENTRY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ENTRY, DESC_FOOD)));

        // different descriptor -> returns false
        assertTrue(standardCommand.equals(new EditCommand(INDEX_FIRST_ENTRY, DESC_FOOD)));
    }

}

