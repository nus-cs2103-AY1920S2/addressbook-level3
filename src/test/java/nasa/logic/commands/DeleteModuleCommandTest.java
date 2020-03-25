package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.Test;

import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

public class DeleteModuleCommandTest {

    private Model model = new ModelManager(getTypicalNasaBook(), new UserPrefs());

    @Test
    public void execute_validModuleUnfilteredList_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        DeleteModuleCommand deleteCommand = new DeleteModuleCommand(moduleToDelete.getModuleCode());

        String expectedMessage = String.format(DeleteModuleCommand.MESSAGE_DELETE_MODULE_SUCCESS,
                moduleToDelete.getModuleCode().toString());

        ModelManager expectedModel = new ModelManager(model.getNasaBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidModuleUnfilteredList_failure() {
        // a  module that does not exist
        DeleteModuleCommand deleteModuleCommand = new DeleteModuleCommand(new ModuleCode("MA1521"));
        assertCommandFailure(deleteModuleCommand, model, DeleteModuleCommand.MESSAGE_FAILURE);
    }

}
