package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import nasa.model.HistoryBook;
import nasa.model.module.UniqueModuleList;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;

import org.junit.jupiter.api.Test;

import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.testutil.NasaBookBuilder;

public class DeleteModuleCommandTest {

    private Model model = new ModelManager(new NasaBookBuilder().build(), new HistoryBook<>(), new UserPrefs());

    @Test
    public void execute_validModuleUnfilteredList_success() {
        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        DeleteModuleCommand deleteCommand = new DeleteModuleCommand(moduleToDelete.getModuleCode());

        String expectedMessage = String.format(moduleToDelete.getModuleCode().toString()
            + DeleteModuleCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getNasaBook(), new HistoryBook<>(), new UserPrefs());
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
