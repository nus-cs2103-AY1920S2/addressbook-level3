package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.Module;
import seedu.address.model.util.action.DoableAction;
import seedu.address.model.util.action.DoableActionList;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ModuleBuilder;

public class ModuleAddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddCommand(null));
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new ModuleAddCommand(validModule).execute(modelStub);

        assertEquals(String.format(ModuleAddCommand.MESSAGE_SUCCESS, validModule),
                commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        ModuleAddCommand moduleAddCommand = new ModuleAddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class,
                ModuleAddCommand.MESSAGE_DUPLICATE_MODULE, () -> moduleAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Module cs2103t = new ModuleBuilder().withModuleCode("CS2103T").build();
        Module cs2101 = new ModuleBuilder().withModuleCode("CS2101").build();
        ModuleAddCommand addCs2103tCommand = new ModuleAddCommand(cs2103t);
        ModuleAddCommand addCs2101Command = new ModuleAddCommand(cs2101);

        // same object -> returns true
        assertEquals(addCs2103tCommand, addCs2103tCommand);

        // same values -> returns true
        ModuleAddCommand addCs2103tCommandCopy = new ModuleAddCommand(cs2103t);
        assertEquals(addCs2103tCommand, addCs2103tCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addCs2103tCommand);

        // null -> returns false
        assertNotEquals(null, addCs2103tCommand);

        // different module code -> returns false
        assertNotEquals(addCs2103tCommand, addCs2101Command);
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();
        final DoableActionList actionList = new DoableActionList();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public void updateFacilitatorListForModule(Predicate<Facilitator> predicate) {
            //empty method body
        }

        @Override
        public void addAction(DoableAction<?> action) {
            actionList.addAction(action);
        }

        @Override
        public ReadOnlyModManager getModManager() {
            return new ModManager();
        }
    }
}
