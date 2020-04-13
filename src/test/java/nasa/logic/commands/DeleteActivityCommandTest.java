package nasa.logic.commands;

import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;

public class DeleteActivityCommandTest {

    private Model model = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new HistoryBook<>(),
            new UserPrefs());

    /*
    @Test
    public void execute_validActivityUnfilteredList_success() {
        Module module = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode);

        String expectedMessage = String.format(INDEX_FIRST_ACTIVITY.toString()
            + DeleteActivityCommand.MESSAGE_DELETE_ACTIVITY_SUCCESS);

        ModelManager expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());
        expectedModel.removeActivityByIndex(moduleCode, INDEX_FIRST_ACTIVITY);

        assertCommandSuccess(deleteActivityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        // an index that is invalid (> list.size())
        Module module = model.getFilteredModuleList().get(INDEX_SECOND_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(Index.fromOneBased(6), moduleCode);

        assertCommandFailure(deleteActivityCommand, model, DeleteActivityCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_invalidModuleUnfilteredList_failure() {
        // a module that does not exist
        ModuleCode moduleCode = new ModuleCode("UTW1001K");
        DeleteActivityCommand deleteActivityCommand = new DeleteActivityCommand(INDEX_FIRST_ACTIVITY, moduleCode);

        assertCommandFailure(deleteActivityCommand, model, DeleteActivityCommand.MESSAGE_MODULE_NOT_FOUND);
    }*/
}
