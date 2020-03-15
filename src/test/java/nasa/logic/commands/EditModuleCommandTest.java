package nasa.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static nasa.logic.commands.CommandTestUtil.DESC_AMY;
//import static nasa.logic.commands.CommandTestUtil.DESC_BOB;
//import static nasa.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static nasa.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static nasa.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
//import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
//import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static nasa.logic.commands.CommandTestUtil.showModuleAtIndex;
//import static nasa.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
//import static nasa.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
//import static nasa.testutil.TypicalActivities.getTypicalNasaBook;

import org.junit.jupiter.api.Test;

import nasa.commons.core.Messages;
import nasa.commons.core.index.Index;
import nasa.logic.commands.ClearCommand;
import nasa.logic.commands.EditModuleCommand;
import nasa.logic.commands.EditModuleCommand.EditModuleDescriptor;
import nasa.model.NasaBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.Module;
//import nasa.testutil.EditModuleDescriptorBuilder;
//import nasa.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditModuleCommand.
 */
public class EditModuleCommandTest {

    // private Model model = new ModelManager(getTypicalNasaBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        /*
        Module editedModule = new ModuleBuilder().build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setModule(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        /*
        Index indexLastModule = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setModule(lastModule, editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        /*
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, new EditModuleDescriptor());
        Module editedModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_filteredList_success() {
        /*
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleInFilteredList = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleInFilteredList).withName(VALID_NAME_BOB).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new UserPrefs());
        expectedModel.setModule(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);

         */
    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        /*
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SECOND_MODULE, descriptor);

        assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);

         */
    }

    @Test
    public void execute_duplicateModuleFilteredList_failure() {
        /*
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        // edit module in filtered list into a duplicate in nasa book
        Module moduleInList = model.getNasaBook().getModuleList().get(INDEX_SECOND_MODULE.getZeroBased());
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder(moduleInList).build());

        assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);

         */
    }

    @Test
    public void execute_invalidModuleIndexUnfilteredList_failure() {
        /*
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

         */
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of nasa book
     */
    @Test
    public void execute_invalidModuleIndexFilteredList_failure() {
        /*
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of nasa book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getNasaBook().getModuleList().size());

        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex,
                new EditModuleDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editModuleCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

         */
    }

    @Test
    public void equals() {
        /*
        final EditModuleCommand standardCommand = new EditModuleCommand(INDEX_FIRST_MODULE, DESC_AMY);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_AMY);
        EditModuleCommand commandWithSameValues = new EditModuleCommand(INDEX_FIRST_MODULE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_SECOND_MODULE, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_FIRST_MODULE, DESC_BOB)));

         */
    }

}

