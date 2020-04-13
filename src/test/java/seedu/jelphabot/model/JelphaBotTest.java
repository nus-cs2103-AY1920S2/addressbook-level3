package seedu.jelphabot.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_BOOK_REPORT_REMINDER_DAY;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.reminder.ReminderShowsTask;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.exceptions.DuplicateTaskException;
import seedu.jelphabot.testutil.ReminderBuilder;
import seedu.jelphabot.testutil.TaskBuilder;

public class JelphaBotTest {

    private final JelphaBot jelphaBot = new JelphaBot();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), jelphaBot.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> jelphaBot.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyJelphaBot_replacesData() {
        JelphaBot newData = getTypicalJelphaBot();
        jelphaBot.resetData(newData);
        assertEquals(newData, jelphaBot);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedAlice = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                .build();
        List<Task> newTasks = Arrays.asList(ASSESSMENT, editedAlice);
        JelphaBotStub newData = new JelphaBotStub(newTasks);

        assertThrows(DuplicateTaskException.class, () -> jelphaBot.resetData(newData));
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> jelphaBot.hasTask(null));
    }

    @Test
    public void hasTask_taskNotInJelphaBot_returnsFalse() {
        assertFalse(jelphaBot.hasTask(ASSESSMENT));
    }

    //@@author yapdianhao
    @Test
    public void hasReminder_reminderNotInJelphaBot_returnsFalse() {
        assertFalse(jelphaBot.hasReminder(ASSESSMENT_REMINDER));
    }
    //@@author

    @Test
    public void hasTask_taskInJelphaBot_returnsTrue() {
        jelphaBot.addTask(ASSESSMENT);
        assertTrue(jelphaBot.hasTask(ASSESSMENT));
    }

    //@@author yapdianhao
    @Test
    public void hasReminder_reminderInJelphaBot_returnsTrue() {
        jelphaBot.addReminder(ASSESSMENT_REMINDER);
        assertTrue(jelphaBot.hasReminder(ASSESSMENT_REMINDER));
    }
    //@@author

    @Test
    public void hasTask_taskWithSameIdentityFieldsInJelphaBot_returnsTrue() {
        jelphaBot.addTask(ASSESSMENT);
        Task editedAlice = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                .build();
        assertTrue(jelphaBot.hasTask(editedAlice));
    }

    //@@author yapdianhao
    @Test
    public void hasReminder_reminderWithSameIdentityFieldsInJelphaBot_returnsTrue() {
        jelphaBot.addReminder(ASSESSMENT_REMINDER);
        Reminder editedReminder = new ReminderBuilder(ASSESSMENT_REMINDER)
                                      .withReminderDay(VALID_BOOK_REPORT_REMINDER_DAY).build();
        assertTrue(jelphaBot.hasReminder(editedReminder));
    }
    //@@author

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> jelphaBot.getTaskList().remove(0));
    }

    //@@author yapdianhao
    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> jelphaBot.getReminderList().remove(0));
    }

    //@@author yapdianhao
    @Test
    public void getReminderShowsTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> jelphaBot
                                                                    .getReminderShowsTaskList().remove(0));
    }
    //@@author

    /**
     * A stub ReadOnlyJelphaBot whose tasks list can violate interface constraints.
     */
    private static class JelphaBotStub implements ReadOnlyJelphaBot {

        private final ObservableList<Task> tasks = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        private final ObservableList<ReminderShowsTask> reminderShowsTasks = FXCollections.observableArrayList();

        JelphaBotStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminders;
        }

        @Override
        public ObservableList<ReminderShowsTask> getReminderShowsTaskList() {
            return reminderShowsTasks;
        }

        @Override
        public void setTasks(List<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public void setReminders(List<Reminder> reminders) {
            this.reminders.setAll(reminders);
        }

    }

}
