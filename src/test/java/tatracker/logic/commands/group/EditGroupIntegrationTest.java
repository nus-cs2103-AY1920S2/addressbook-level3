//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class EditGroupIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTaTracker(), new UserPrefs());
    }


    @Test
    public void execute_groupEditedByModel_editSuccessful() throws Exception {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        validModule.addGroup(validGroup);
        model.addModule(validModule);

        CommandResult commandResult = new EditGroupCommand(validGroup, validModule.getIdentifier(),
                "GNEW", GroupType.OTHER).execute(model);

        assertEquals(String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertTrue(model.hasGroup("GNEW", validModule.getIdentifier()));
        Group changedGroup = model.getModule(validModule.getIdentifier()).getGroup("GNEW");
        assertEquals(GroupType.OTHER, changedGroup.getGroupType());
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditGroupCommand editGroupCommand =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);
        model.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                editGroupCommand.execute(model));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditGroupCommand editGroupCommand =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                editGroupCommand.execute(model));
    }

}
