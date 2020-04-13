package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFacilitators.ALICE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;
import static seedu.address.testutil.TypicalModules.CS2103T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.Description;
import seedu.address.testutil.ModelStub;

public class ModuleViewCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_moduleCodeExist_success() throws Exception {
        Task task = Task.makeNonScheduledTask(new Description("Assignment"), new ModuleCode("CS2103T"));
        ModuleViewCommandTest.ModelStubWithModule modelStub =
                new ModuleViewCommandTest.ModelStubWithModule(CS2103T, ALICE, task);
        ModuleCode moduleCode = CS2103T.getModuleCode();
        Module moduleToView = model.findModule(moduleCode).get();

        CommandResult commandResult = new ModuleViewCommand(moduleCode).execute(modelStub);

        assertEquals(String.format(ModuleViewCommand.MESSAGE_SUCCESS, moduleToView),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(ALICE), modelStub.facilitatorList);
        assertEquals(Arrays.asList(task), modelStub.taskList);
    }

    @Test
    public void execute_moduleCodeDoesNotExist_throwsCommandException() {
        ModuleCode validModuleCode = CS2103T.getModuleCode();
        ModuleViewCommand moduleViewCommand = new ModuleViewCommand(validModuleCode);
        ModelStub modelStub = new ModuleViewCommandTest.ModelStubWithoutModule();

        assertThrows(CommandException.class,
                String.format(ModuleViewCommand.MESSAGE_MODULE_DOES_NOT_EXIST,
                CS2103T.getModuleCode().value), () -> moduleViewCommand.execute(modelStub));
    }

    @Test
    public void execute_validIndex_success() {
        Module moduleToView = model.getFilteredModuleList().get(INDEX_FIRST.getZeroBased());
        ModuleViewCommand viewCommand = new ModuleViewCommand(INDEX_FIRST);

        String expectedMessage = String.format(ModuleViewCommand.MESSAGE_SUCCESS, moduleToView);

        ModelManager expectedModel = new ModelManager(model.getModManager(), new UserPrefs());
        expectedModel.updateModule(Optional.of(moduleToView));
        expectedModel.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(
                moduleToView.getModuleCode().value));
        expectedModel.updateTaskListForModule(x -> x.getModuleCode().equals(moduleToView.getModuleCode()));

        assertCommandSuccess(viewCommand, model, expectedMessage, CommandType.MODULE, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleViewCommand viewCommand = new ModuleViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ModuleCode cs2103t = new ModuleCode("CS2103T");
        ModuleCode cs2101 = new ModuleCode("CS2101");
        ModuleViewCommand viewCs2103tCommand = new ModuleViewCommand(cs2103t);
        ModuleViewCommand viewCs2101Command = new ModuleViewCommand(cs2101);
        ModuleViewCommand viewFirstCommand = new ModuleViewCommand(INDEX_FIRST);
        ModuleViewCommand viewSecondCommand = new ModuleViewCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(viewCs2103tCommand.equals(viewCs2103tCommand));
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same module code values -> returns true
        ModuleViewCommand viewCs2103tCommandCopy = new ModuleViewCommand(cs2103t);
        assertTrue(viewCs2103tCommand.equals(viewCs2103tCommandCopy));

        // same index values -> returns true
        ModuleViewCommand viewFirstCommandCopy = new ModuleViewCommand(INDEX_FIRST);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewCs2103tCommand.equals(1));
        assertFalse(viewFirstCommand.equals(1));
        assertFalse(viewCs2103tCommand.equals(viewFirstCommand));

        // null -> returns false
        assertFalse(viewCs2103tCommand.equals(null));
        assertFalse(viewFirstCommand.equals(null));

        // different module code -> returns false
        assertFalse(viewCs2103tCommand.equals(viewCs2101Command));

        // different index -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }


    private class ModelStubWithModule extends ModelStub {
        private final ArrayList<Module> moduleList = new ArrayList<>();
        private final ArrayList<Facilitator> facilitatorList = new ArrayList<>();
        private final ArrayList<Task> taskList = new ArrayList<>();
        private Optional<Module> module;

        ModelStubWithModule(Module module, Facilitator facilitator, Task task) {
            requireAllNonNull(module, facilitator);
            this.moduleList.add(module);
            this.facilitatorList.add(facilitator);
            this.taskList.add(task);
            this.module = Optional.empty();
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return moduleList.stream().anyMatch(module::isSameModule);
        }

        @Override
        public Optional<Module> findModule(ModuleCode moduleCode) {
            for (Module module : moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return Optional.of(module);
                }
            }
            return Optional.empty();
        }

        @Override
        public void updateModule(Optional<Module> module) {
            this.module = module;
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFacilitatorListForModule(Predicate<Facilitator> predicate) {
            ModuleCode toCheck;
            if (module.isEmpty()) {
                facilitatorList.clear();
            } else {
                toCheck = module.get().getModuleCode();
                facilitatorList.removeIf(facilitator -> !facilitator.getModuleCodes().contains(toCheck));
            }
        }

        @Override
        public void updateTaskListForModule(Predicate<Task> predicate) {
            ModuleCode toCheck;
            if (module.isEmpty()) {
                facilitatorList.clear();
            } else {
                toCheck = module.get().getModuleCode();
                taskList.removeIf(task -> !task.getModuleCode().equals(toCheck));
            }
        }
    }

    private class ModelStubWithoutModule extends ModelStub {
        private final ArrayList<Module> moduleList = new ArrayList<>();
        private Optional<Module> module;

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return moduleList.stream().anyMatch(module::isSameModule);
        }

        @Override
        public Optional<Module> findModule(ModuleCode moduleCode) {
            for (Module module : moduleList) {
                if (module.getModuleCode().equals(moduleCode)) {
                    return Optional.of(module);
                }
            }
            return Optional.empty();
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            return FXCollections.observableArrayList();
        }

    }
}
