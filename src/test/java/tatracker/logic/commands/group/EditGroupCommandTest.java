//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.module.TypicalModules.CS2103T;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.group.Group;
import tatracker.model.group.GroupType;
import tatracker.model.module.Module;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class EditGroupCommandTest {

    @Test
    public void execute_groupEditedByModel_editSuccessful() throws Exception {
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        validModule.addGroup(validGroup);
        modelStub.addModule(validModule);

        CommandResult commandResult = new EditGroupCommand(validGroup, validModule.getIdentifier(),
                "GNEW", GroupType.OTHER).execute(modelStub);

        assertEquals(String.format(EditGroupCommand.MESSAGE_EDIT_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
        assertEquals(Arrays.asList(validGroup), modelStub.modulesAdded.get(0).getGroupList());
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditGroupCommand editGroupCommand =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                editGroupCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        EditGroupCommand editGroupCommand =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                editGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module validModule = new ModuleBuilder().build();
        EditGroupCommand editGroupCommand =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);
        EditGroupCommand editGroupCommandTwo =
                new EditGroupCommand(new Group("G15"), "CS3243",
                        "GNEW", GroupType.OTHER);
        EditGroupCommand editGroupCommandCopy =
                new EditGroupCommand(new Group("G15"), validModule.getIdentifier(),
                        "GNEW", GroupType.OTHER);

        // same object -> returns true
        assertTrue(editGroupCommand.equals(editGroupCommand));

        // same values -> returns true
        assertTrue(editGroupCommand.equals(editGroupCommandCopy));

        // different types -> returns false
        assertFalse(editGroupCommand.equals(1));

        // null -> returns false
        assertFalse(editGroupCommand == null);

        // different modules -> returns false
        assertFalse(editGroupCommand.equals(editGroupCommandTwo));

        // different groups -> returns false
        DeleteGroupCommand editGroupCommandCopyTwo =
                new DeleteGroupCommand("G11", CS2103T.getIdentifier());
        assertFalse(editGroupCommand.equals(editGroupCommandCopyTwo));
    }

}
