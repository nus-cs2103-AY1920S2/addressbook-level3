package seedu.address.logic.commands.facilitator;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModules.CS2103T;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.Module;
import seedu.address.model.util.action.DoableAction;
import seedu.address.testutil.FacilitatorBuilder;
import seedu.address.testutil.ModelStub;

public class FacilAddCommandTest {

    @Test
    public void constructor_nullFacilitator_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FacilAddCommand(null));
    }

    @Test
    public void execute_facilitatorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFacilitatorAdded modelStub = new ModelStubAcceptingFacilitatorAdded();
        Facilitator validFacilitator = new FacilitatorBuilder().withModuleCodes().build();

        CommandResult commandResult = new FacilAddCommand(validFacilitator).execute(modelStub);

        assertEquals(String.format(FacilAddCommand.MESSAGE_SUCCESS, validFacilitator),
                commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validFacilitator), modelStub.facilitatorsAdded);
    }

    @Test
    public void execute_duplicateFacilitator_throwsCommandException() {
        Facilitator validFacilitator = new FacilitatorBuilder().build();
        FacilAddCommand facilAddCommand = new FacilAddCommand(validFacilitator);
        ModelStub modelStub = new ModelStubWithFacilitator(validFacilitator);

        assertThrows(CommandException.class,
                FacilAddCommand.MESSAGE_DUPLICATE_FACILITATOR, () -> facilAddCommand.execute(modelStub));
    }

    @Test
    public void execute_moduleDoesNotExist_throwsCommandException() {
        Facilitator validFacilitator = new FacilitatorBuilder().withModuleCodes("CS2101").build();
        FacilAddCommand facilAddCommand = new FacilAddCommand(validFacilitator);
        ModelStub modelStub = new ModelStubWithoutModule(CS2103T);

        assertThrows(CommandException.class, String.format(
                FacilAddCommand.MESSAGE_MODULE_DOES_NOT_EXIST, "CS2101"), () -> facilAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Facilitator alice = new FacilitatorBuilder().withName("Alice").build();
        Facilitator bob = new FacilitatorBuilder().withName("Bob").build();
        FacilAddCommand addAliceCommand = new FacilAddCommand(alice);
        FacilAddCommand addBobCommand = new FacilAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        FacilAddCommand addAliceCommandCopy = new FacilAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different facilitator -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that contains a single facilitator.
     */
    private class ModelStubWithFacilitator extends ModelStub {
        private final Facilitator facilitator;

        ModelStubWithFacilitator(Facilitator facilitator) {
            requireNonNull(facilitator);
            this.facilitator = facilitator;
        }

        @Override
        public boolean hasFacilitator(Facilitator facilitator) {
            requireNonNull(facilitator);
            return this.facilitator.isSameFacilitator(facilitator);
        }
    }

    /**
     * A Model stub that always accept the facilitator being added.
     */
    private class ModelStubAcceptingFacilitatorAdded extends ModelStub {
        final ArrayList<Facilitator> facilitatorsAdded = new ArrayList<>();

        @Override
        public boolean hasFacilitator(Facilitator facilitator) {
            requireNonNull(facilitator);
            return facilitatorsAdded.stream().anyMatch(facilitator::isSameFacilitator);
        }

        @Override
        public void addFacilitator(Facilitator facilitator) {
            requireNonNull(facilitator);
            facilitatorsAdded.add(facilitator);
        }

        @Override
        public void addAction(DoableAction<?> action) {
            //empty method body
        }

        @Override
        public ReadOnlyModManager getModManager() {
            return new ModManager();
        }
    }

    /**
     * A Model stub that does not contain module of the facilitator to be added.
     */
    private class ModelStubWithoutModule extends ModelStub {
        private final ArrayList<Facilitator> facilitatorsAdded = new ArrayList<>();
        private final Module module;

        ModelStubWithoutModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModuleCode(String moduleCode) {
            requireNonNull(moduleCode);
            return module.getModuleCode().value.equals(moduleCode);
        }

        @Override
        public boolean hasFacilitator(Facilitator facilitator) {
            requireNonNull(facilitator);
            return facilitatorsAdded.stream().anyMatch(facilitator::isSameFacilitator);
        }

        @Override
        public void addAction(DoableAction<?> action) {
            //empty method body
        }
    }
}
