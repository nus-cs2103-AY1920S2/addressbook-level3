package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS3230;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROGRAMMING;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.action.DoableAction;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.ScheduledTaskBuilder;

public class TaskAddCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskAddCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new ScheduledTaskBuilder().build();

        CommandResult commandResult = new TaskAddCommand(validTask).execute(modelStub);

        assertEquals(String.format(TaskAddCommand.MESSAGE_SUCCESS, validTask),
                commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validTask), modelStub.taskAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new ScheduledTaskBuilder().build();
        TaskAddCommand taskAddCommand = new TaskAddCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        assertThrows(CommandException.class,
                TaskAddCommand.MESSAGE_DUPLICATE_TASK, () -> taskAddCommand.execute(modelStub));
    }

    @Test
    public void execute_moduleDoesNotExist_throwsCommandException() {
        Task validTask = new ScheduledTaskBuilder().build();
        TaskAddCommand taskAddCommand = new TaskAddCommand(validTask);
        ModelStub modelStub = new TaskAddCommandTest.ModelStubWithoutModule();

        assertThrows(CommandException.class, String.format(
                TaskAddCommand.MESSAGE_MODULE_NOT_EXISTENT, "CS2103T"), () -> taskAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Task taskA = new ScheduledTaskBuilder().build();
        Task taskB = new ScheduledTaskBuilder().withDescription(VALID_TASK_DESCRIPTION_PROGRAMMING)
                .withModuleCode(VALID_MODULE_CODE_CS3230).build();
        TaskAddCommand addTaskACommand = new TaskAddCommand(taskA);
        TaskAddCommand addTaskBCommand = new TaskAddCommand(taskB);

        // same object -> returns true
        assertEquals(addTaskACommand, addTaskACommand);

        // same values -> returns true
        TaskAddCommand addTaskACommandCopy = new TaskAddCommand(taskA);
        assertEquals(addTaskACommand, addTaskACommandCopy);

        // different types -> returns false
        assertNotEquals(1, addTaskACommand);

        // null -> returns false
        assertNotEquals(null, addTaskACommand);

        // different module code -> returns false
        assertNotEquals(addTaskACommand, addTaskBCommand);
    }

    /**
     * A Model stub that contains a single task.
     */
    private class ModelStubWithTask extends ModelStub {
        private final Task task;

        ModelStubWithTask(Task task) {
            requireNonNull(task);
            this.task = task;
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return this.task.isSameTask(task);
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> taskAdded = new ArrayList<>();

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return taskAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            taskAdded.add(task);
        }

        @Override
        public Optional<Module> findModule(ModuleCode moduleCode) {
            Module module = new ModuleBuilder().withModuleCode(VALID_MODULE_CODE_CS2101)
                    .withDescription(VALID_DESCRIPTION_CS2101).build();
            return Optional.of(module);
        }

        @Override
        public void updateTaskListForModule(Predicate<Task> predicate) {
            //empty method body
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
     * A Model stub that does not contain module of the task be to added.
     */
    private class ModelStubWithoutModule extends ModelStub {
        private final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public Optional<Module> findModule(ModuleCode moduleCode) {
            return Optional.empty();
        }

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addAction(DoableAction<?> action) {
            //empty method body
        }
    }
}
