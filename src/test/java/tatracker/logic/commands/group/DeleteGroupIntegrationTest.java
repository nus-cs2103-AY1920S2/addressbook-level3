//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class DeleteGroupIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }


    @Test
    public void execute_groupDeletedByModel_addSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new DeleteGroupCommand(validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(model);

        assertEquals(String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertFalse(model.hasGroup(validGroup.getIdentifier(), validModule.getIdentifier()));
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand =
                new DeleteGroupCommand(validGroup.getIdentifier(), validModule.getIdentifier());
        model.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                deleteGroupCommand.execute(model));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand =
                new DeleteGroupCommand(validGroup.getIdentifier(), "CS3243");
        model.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                deleteGroupCommand.execute(model));
    }

}
