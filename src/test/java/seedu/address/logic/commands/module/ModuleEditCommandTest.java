package seedu.address.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.module.ModuleEditCommand.MESSAGE_NON_EXISTENT_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.ModManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.EditModuleDescriptorBuilder;
import seedu.address.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ModuleEditCommand}.
 */
public class ModuleEditCommandTest {

    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedIndex_success() {
        Module editedModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        Module moduleToEdit = model.getFilteredModuleList().get(0);
        expectedModel.setModule(moduleToEdit, editedModule);
        expectedModel.setModuleCodeInFacilitatorList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());
        expectedModel.setModuleCodeInTaskList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());
        expectedModel.setModuleCodeInLessonList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedModuleCode_success() {
        Module editedModule = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS1101S)
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        Module moduleToEdit = model.findModule(new ModuleCode(VALID_MODULE_CODE_CS2103T)).get();
        expectedModel.setModule(moduleToEdit, editedModule);
        expectedModel.setModuleCodeInFacilitatorList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());
        expectedModel.setModuleCodeInTaskList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());
        expectedModel.setModuleCodeInLessonList(moduleToEdit.getModuleCode(), editedModule.getModuleCode());

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedIndex_success() {
        Index indexLastModule = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withDescription(VALID_DESCRIPTION_CS1101S).build();

        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setModule(lastModule, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedModuleCode_success() {
        Module moduleToEdit = model.findModule(new ModuleCode(VALID_MODULE_CODE_CS2101)).get();

        ModuleBuilder moduleInList = new ModuleBuilder(moduleToEdit);
        Module editedModule = moduleInList.withDescription(VALID_DESCRIPTION_CS1101S).build();

        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101), descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setModule(moduleToEdit, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_moduleViewedEditedWithIndex_success() {
        Index indexLastModule = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastModule.getZeroBased());
        model.updateModule(Optional.of(lastModule));

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withDescription(VALID_DESCRIPTION_CS1101S).build();

        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setModule(lastModule, editedModule);

        expectedModel.updateModule(Optional.of(editedModule));
        expectedModel.updateFacilitatorListForModule(
                new ModuleCodesContainKeywordPredicate(editedModule.getModuleCode().value));
        expectedModel.updateTaskListForModule(x -> x.getModuleCode().equals(editedModule.getModuleCode()));

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_moduleViewedEditedWithModuleCode_success() {
        Module moduleToEdit = model.findModule(new ModuleCode(VALID_MODULE_CODE_CS2101)).get();
        model.updateModule(Optional.of(moduleToEdit));

        ModuleBuilder moduleInList = new ModuleBuilder(moduleToEdit);
        Module editedModule = moduleInList.withDescription(VALID_DESCRIPTION_CS1101S).build();

        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_CS1101S).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101), descriptor);

        String expectedMessage = String.format(ModuleEditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new ModManager(model.getModManager()), new UserPrefs());
        expectedModel.setModule(moduleToEdit, editedModule);

        expectedModel.updateModule(Optional.of(editedModule));
        expectedModel.updateFacilitatorListForModule(
                new ModuleCodesContainKeywordPredicate(editedModule.getModuleCode().value));
        expectedModel.updateTaskListForModule(x -> x.getModuleCode().equals(editedModule.getModuleCode()));

        assertCommandSuccess(editCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_invalidModuleIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS2101).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_nonExistentModule_failure() {
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS2101).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS1101S), descriptor);

        assertCommandFailure(editCommand, model, String.format(MESSAGE_NON_EXISTENT_MODULE, VALID_MODULE_CODE_CS1101S));
    }

    @Test
    public void execute_duplicateModuleIndex_failure() {
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, ModuleEditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateModuleModuleCode_failure() {
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        ModuleEditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), descriptor);

        assertCommandFailure(editCommand, model, ModuleEditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_notEditedIndex_failure() {
        ModuleEditCommand editCommand = new ModuleEditCommand(INDEX_FIRST,
                new ModuleEditCommand.EditModuleDescriptor());
        String expectedMessage = Messages.MESSAGE_NOT_EDITED;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void execute_notEditedModuleCode_failure() {
        ModuleEditCommand editCommand = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T),
                new ModuleEditCommand.EditModuleDescriptor());
        String expectedMessage = Messages.MESSAGE_NOT_EDITED;
        assertCommandFailure(editCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final ModuleEditCommand standardIndexCommand = new ModuleEditCommand(INDEX_FIRST, DESC_CS1101S);
        final ModuleEditCommand standardModuleCodeCommand =
                new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS1101S), DESC_CS1101S);

        // same index and descriptor values -> returns true
        ModuleEditCommand.EditModuleDescriptor copyDescriptor =
                new ModuleEditCommand.EditModuleDescriptor(DESC_CS1101S);
        ModuleEditCommand commandWithSameValues = new ModuleEditCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardIndexCommand.equals(commandWithSameValues));

        // same module code and descriptor values -> returns true
        commandWithSameValues = new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS1101S), copyDescriptor);
        assertTrue(standardModuleCodeCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardIndexCommand.equals(standardIndexCommand));
        assertTrue(standardModuleCodeCommand.equals(standardModuleCodeCommand));

        // null -> returns false
        assertFalse(standardIndexCommand.equals(null));
        assertFalse(standardModuleCodeCommand.equals(null));

        // different types -> returns false
        assertFalse(standardIndexCommand.equals(new ClearCommand()));
        assertFalse(standardModuleCodeCommand.equals(new ClearCommand()));

        // different constructor -> returns false
        assertFalse(standardIndexCommand.equals(standardModuleCodeCommand));

        // different index -> returns false
        assertFalse(standardIndexCommand.equals(new ModuleEditCommand(INDEX_SECOND, DESC_CS1101S)));

        // different module code -> returns false
        assertFalse(standardModuleCodeCommand.equals(new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2101),
                DESC_CS1101S)));

        // different descriptor -> returns false
        assertFalse(standardIndexCommand.equals(new ModuleEditCommand(INDEX_FIRST, DESC_CS2101)));
        assertFalse(standardModuleCodeCommand.equals(new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS1101S),
                DESC_CS2101)));
    }

}
