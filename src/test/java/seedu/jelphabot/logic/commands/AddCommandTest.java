package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.jelphabot.commons.core.GuiSettings;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.ReadOnlyUserPrefs;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.productivity.ProductivityList;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.summary.SummaryList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.GroupedTaskList;
import seedu.jelphabot.model.task.tasklist.PinnedTaskList;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;
import seedu.jelphabot.testutil.TaskBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = new AddCommand(validTask).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task validTask = new TaskBuilder().build();
        AddCommand addCommand = new AddCommand(validTask);
        ModelStub modelStub = new ModelStubWithTask(validTask);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_TASK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Task alice = new TaskBuilder().withDescription("Alice").build();
        Task bob = new TaskBuilder().withDescription("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotEquals(null, addAliceCommand);

        // different commands -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private static class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getPopUpWindowGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDeletedReminders(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getJelphaBotFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getRemindersFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJelphaBotFilePath(Path jelphaBotFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyJelphaBot getJelphaBot() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateReminderShowsTask() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Task> getTaskListFromJelphaBot() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Reminder> getReminderListFromJelphaBot() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setJelphaBot(ReadOnlyJelphaBot newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTask(Task task) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTask(Task target, Task editedTask) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProductivity(Productivity productivity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ProductivityList getProductivityList() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public ObservableList<Task> getFilteredTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ReminderShowsTask> getReminderShowsTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GroupedTaskList getGroupedTaskList(GroupedTaskList.Category category) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PinnedTaskList getPinnedTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ViewTaskList getLastShownList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Reminder> getFilteredReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredCalendarTaskList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCalendarTaskList(Predicate<Task> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SummaryList getSummaryList() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void setSummary(Summary summary) {
            throw new AssertionError("This method should not be called");
        }
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
        final ArrayList<Task> tasksAdded = new ArrayList<>();
        final ArrayList<Productivity> productivityAdded = new ArrayList<>();
        final ArrayList<Summary> summaryAdded = new ArrayList<>();

        @Override
        public boolean hasTask(Task task) {
            requireNonNull(task);
            return tasksAdded.stream().anyMatch(task::isSameTask);
        }

        @Override
        public void addTask(Task task) {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public void setProductivity(Productivity productivity) {
            requireNonNull(productivity);
            productivityAdded.add(productivity);
        }

        @Override
        public void setSummary(Summary summary) {
            requireNonNull(summary);
            summaryAdded.add(summary);
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            return new FilteredList<Task>(getJelphaBot().getTaskList());
        }

        @Override
        public ReadOnlyJelphaBot getJelphaBot() {
            return new JelphaBot();
        }
    }

}
