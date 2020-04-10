//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_GROUP;
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

public class AddGroupIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        model.addModule(validModule);
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult =
                new AddGroupCommand(validGroup, validModule.getIdentifier()).execute(model);

        assertEquals(String.format(AddGroupCommand.MESSAGE_ADD_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasGroup(validGroup.getIdentifier(), validModule.getIdentifier()));
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup, validModule.getIdentifier());
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_GROUP, () ->
                addGroupCommand.execute(model));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup, "CS3243");
        model.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                addGroupCommand.execute(model));
    }

}
