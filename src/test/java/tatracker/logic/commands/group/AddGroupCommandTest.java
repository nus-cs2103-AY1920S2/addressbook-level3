//@@author aakanksha-rai

package tatracker.logic.commands.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_GROUP;
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

public class AddGroupCommandTest {
    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGroupCommand(null, null));
    }

    @Test
    public void execute_groupAcceptedByModel_addSuccessful() throws Exception {
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();
        modelStub.addModule(validModule);
        Group validGroup = new GroupBuilder().build();

        CommandResult commandResult = new AddGroupCommand(validGroup, validModule.getIdentifier()).execute(modelStub);

        assertEquals(String.format(AddGroupCommand.MESSAGE_ADD_GROUP_SUCCESS,
                validModule.getIdentifier(),
                validGroup.getIdentifier()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
        assertEquals(Arrays.asList(validGroup), modelStub.modulesAdded.get(0).getGroupList());
    }

    @Test
    public void execute_duplicateGroup_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup, validModule.getIdentifier());
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        validModule.addGroup(validGroup);
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_GROUP, () ->
                addGroupCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        Group validGroup = new GroupBuilder().build();
        AddGroupCommand addGroupCommand = new AddGroupCommand(validGroup, "CS3243");
        ModelStub.ModelStubAcceptingModuleAdded modelStub = new ModelStub.ModelStubAcceptingModuleAdded();
        modelStub.addModule(validModule);

        assertThrows(CommandException.class, MESSAGE_INVALID_MODULE_CODE, () ->
                addGroupCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddGroupCommand addSoftwareEngineeringCommand = new AddGroupCommand(MANY_STUDENTS, CS2103T.getIdentifier());
        AddGroupCommand addArtificialIntelligenceCommand = new AddGroupCommand(MANY_STUDENTS, CS3243.getIdentifier());

        // same object -> returns true
        assertTrue(addSoftwareEngineeringCommand.equals(addSoftwareEngineeringCommand));

        // same values -> returns true
        AddGroupCommand addSoftwareEngineeringCommandCopy = new AddGroupCommand(MANY_STUDENTS, CS2103T.getIdentifier());
        assertTrue(addSoftwareEngineeringCommand.equals(addSoftwareEngineeringCommandCopy));

        // different types -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(1));

        // null -> returns false
        assertFalse(addSoftwareEngineeringCommand == null);

        // different person -> returns false
        assertFalse(addSoftwareEngineeringCommand.equals(addArtificialIntelligenceCommand));
    }
}
