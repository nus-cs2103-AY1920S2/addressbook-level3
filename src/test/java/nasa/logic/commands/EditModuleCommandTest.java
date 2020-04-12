package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.DESC_CS1231;
import static nasa.logic.commands.CommandTestUtil.DESC_CS2030;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.model.util.SampleDataUtil.getSampleNasaBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nasa.logic.commands.module.EditModuleCommand;
import nasa.logic.commands.module.EditModuleCommand.EditModuleDescriptor;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.NasaBook;
import nasa.model.UserPrefs;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.testutil.EditModuleDescriptorBuilder;
import nasa.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditModuleCommand.
 */
public class EditModuleCommandTest {

    private final Model model = new ModelManager(getSampleNasaBook(), new HistoryBook<>(), new HistoryBook<>(),
            new UserPrefs());
    private final ModuleCode firstModuleCodeToEdit = model.getFilteredModuleList().get(0).getModuleCode();
    private final ModuleCode lastModuleCodeToEdit = model.getFilteredModuleList()
             .get(model.getFilteredModuleList().size() - 1).getModuleCode();

    @Test
    public void execute_allFieldsSpecified_success() {
        Module editedModule = new ModuleBuilder().build(); // module with attribute moduleName and moduleCode

        // Descriptor built off editedModule
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();

        // Command making edits to moduleCode with descriptor
        EditModuleCommand editModuleCommand = new EditModuleCommand(firstModuleCodeToEdit, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        /**
         * Initializing model with same initial state as global model variable
         */
        Model expectedModel = new ModelManager(new NasaBook(model.getNasaBook()), new HistoryBook<>(),
                new HistoryBook<>(), new UserPrefs());

        // Replace existing module in model with new, editedModule
        expectedModel.setModule(firstModuleCodeToEdit, editedModule);

        /**
         * Check if successful message from editModuleCommand is equal to expectedMessage,
         * and model is equal to expectedModel
         */
        assertCommandSuccess(editModuleCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        // Module firstModule = model.getFilteredModuleList().get(0);
        // EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        // EditModuleCommand editModuleCommand = new EditModuleCommand(firstModuleCodeToEdit, descriptor);

        // assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);

    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        // EditModuleCommand editModuleCommand = new EditModuleCommand(firstModuleCodeToEdit,
        // new EditModuleDescriptor());

        // assertCommandFailure(editModuleCommand, model, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void equals() {
        final EditModuleCommand standardCommand = new EditModuleCommand(lastModuleCodeToEdit, DESC_CS1231);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_CS1231);
        EditModuleCommand commandWithSameValues = new EditModuleCommand(lastModuleCodeToEdit, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(firstModuleCodeToEdit, DESC_CS1231)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(lastModuleCodeToEdit, DESC_CS2030)));

    }

}

