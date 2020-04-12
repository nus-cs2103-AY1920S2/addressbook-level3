//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.group.TypicalGroups.MANY_STUDENTS;
import static tatracker.testutil.module.TypicalModules.CS2103T;
import static tatracker.testutil.module.TypicalModules.CS3243;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.ModelStub;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.testutil.group.GroupBuilder;
import tatracker.testutil.module.ModuleBuilder;

public class DeleteGroupCommandTest {

    @Test
    public void execute_groupDeletedByModel_addSuccessful() throws Exception {
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        validModule.addGroup(validGroup);
        modelStub.addModule(validModule);

        CommandResult commandResult = new DeleteGroupCommand(validGroup.getIdentifier(),
                validModule.getIdentifier()).execute(modelStub);

        assertEquals(String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
        assertEquals(Arrays.asList(), modelStub.modulesAdded.get(0).getGroupList());
    }

    @Test
    public void execute_invalidGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand =
                new DeleteGroupCommand(validGroup.getIdentifier(), validModule.getIdentifier());
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_GROUP_CODE, () ->
                deleteGroupCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(validGroup.getIdentifier(), "CS3243");
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                deleteGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        DeleteGroupCommand deleteSoftwareEngineeringCommand =
                new DeleteGroupCommand(MANY_STUDENTS.getIdentifier(), CS2103T.getIdentifier());
        DeleteGroupCommand deleteArtificialIntelligenceCommand =
                new DeleteGroupCommand(MANY_STUDENTS.getIdentifier(), CS3243.getIdentifier());

        // same object -> returns true
        assertTrue(deleteSoftwareEngineeringCommand.equals(deleteSoftwareEngineeringCommand));

        // same values -> returns true
        DeleteGroupCommand deleteSoftwareEngineeringCommandCopy =
                new DeleteGroupCommand(MANY_STUDENTS.getIdentifier(), CS2103T.getIdentifier());
        assertTrue(deleteSoftwareEngineeringCommand.equals(deleteSoftwareEngineeringCommandCopy));

        // different types -> returns false
        assertFalse(deleteSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSoftwareEngineeringCommand == null);

        // different modules -> returns false
        assertFalse(deleteSoftwareEngineeringCommand.equals(deleteArtificialIntelligenceCommand));

        // different groups -> returns false
        DeleteGroupCommand deleteSoftwareEngineeringCommandCopyTwo =
                new DeleteGroupCommand("G11", CS2103T.getIdentifier());
        assertFalse(deleteSoftwareEngineeringCommand.equals(deleteSoftwareEngineeringCommandCopyTwo));
    }

}
