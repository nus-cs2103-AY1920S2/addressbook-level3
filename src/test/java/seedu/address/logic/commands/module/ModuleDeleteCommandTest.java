package seedu.address.logic.commands.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.module.ModuleDeleteCommand.MESSAGE_DELETE_NON_EXISTENT_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;

public class ModuleDeleteCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_moduleCodeExist_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS2103T);
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(moduleCode);

        Module moduleToDelete = model.findModule(moduleCode).get();

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.deleteModuleCodeFromFacilitatorList(moduleToDelete.getModuleCode());
        expectedModel.deleteTasksWithModuleCode(moduleToDelete.getModuleCode());

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_moduleCodeDoesNotExist_throwsCommandException() {
        ModuleCode moduleCode = new ModuleCode("CS1101S");
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(moduleCode);

        assertCommandFailure(deleteCommand, model,
                String.format(MESSAGE_DELETE_NON_EXISTENT_MODULE, moduleCode));
    }

    @Test
    public void execute_validIndex_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.deleteModuleCodeFromFacilitatorList(moduleToDelete.getModuleCode());
        expectedModel.deleteTasksWithModuleCode(moduleToDelete.getModuleCode());

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_moduleViewedDeletedWithIndex_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        model.updateModule(Optional.of(moduleToDelete));
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(INDEX_FIRST);

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.deleteModuleCodeFromFacilitatorList(moduleToDelete.getModuleCode());
        expectedModel.deleteTasksWithModuleCode(moduleToDelete.getModuleCode());
        expectedModel.updateModule(Optional.empty());
        expectedModel.removeLessonFromModule(moduleToDelete.getModuleCode());

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_moduleViewedDeletedWithModuleCode_success() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS2103T);
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(moduleCode);

        Module moduleToDelete = model.findModule(moduleCode).get();
        model.updateModule(Optional.of(moduleToDelete));

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        expectedModel.deleteModuleCodeFromFacilitatorList(moduleToDelete.getModuleCode());
        expectedModel.deleteTasksWithModuleCode(moduleToDelete.getModuleCode());
        expectedModel.updateModule(Optional.empty());
        expectedModel.removeLessonFromModule(moduleToDelete.getModuleCode());

        assertCommandSuccess(deleteCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void equals() {
        ModuleCode cs2103t = new ModuleCode("CS2103T");
        ModuleCode cs2101 = new ModuleCode("CS2101");
        ModuleDeleteCommand deleteCs2103tCommand = new ModuleDeleteCommand(cs2103t);
        ModuleDeleteCommand deleteCs2101Command = new ModuleDeleteCommand(cs2101);
        ModuleDeleteCommand deleteFirstCommand = new ModuleDeleteCommand(INDEX_FIRST);
        ModuleDeleteCommand deleteSecondCommand = new ModuleDeleteCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteCs2103tCommand.equals(deleteCs2103tCommand));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same module code values -> returns true
        ModuleDeleteCommand deleteCs2103tCommandCopy = new ModuleDeleteCommand(cs2103t);
        assertTrue(deleteCs2103tCommand.equals(deleteCs2103tCommandCopy));

        // same index values -> returns true
        ModuleDeleteCommand deleteFirstCommandCopy = new ModuleDeleteCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCs2103tCommand.equals(1));
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteCs2103tCommand.equals(deleteFirstCommand));

        // null -> returns false
        assertFalse(deleteCs2103tCommand.equals(null));
        assertFalse(deleteFirstCommand.equals(null));

        // different module code -> returns false
        assertFalse(deleteCs2103tCommand.equals(deleteCs2101Command));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
