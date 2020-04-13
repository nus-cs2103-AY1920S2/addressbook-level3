//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalReminderShowsTasks.DEFAULT_REMINDER_SHOWS_TASK;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalReminders.ASSIGNMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.BOOK_REPORT;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.reminder.exceptions.DuplicateReminderShowsTaskException;
import seedu.jelphabot.testutil.ReminderShowsTaskBuilder;

public class UniqueReminderShowsTaskListTest {

    private final UniqueReminderShowsTaskList uniqueReminderShowsTaskList = new UniqueReminderShowsTaskList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderShowsTaskList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(uniqueReminderShowsTaskList.contains(DEFAULT_REMINDER_SHOWS_TASK));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        assertTrue(uniqueReminderShowsTaskList.contains(DEFAULT_REMINDER_SHOWS_TASK));
    }

    @Test
    public void contains_reminderShowsTaskWithSameReminderFieldsInList_returnsTrue() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        ReminderShowsTask editedReminderShowsTask = new ReminderShowsTaskBuilder()
                                                        .withReminder(ASSESSMENT_REMINDER)
                                                        .build();
        assertTrue(uniqueReminderShowsTaskList.contains(editedReminderShowsTask));
    }

    @Test
    public void contains_reminderShowsTaskWithSameTaskFieldsInList_returnsTrue() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        ReminderShowsTask editedReminderShowsTask = new ReminderShowsTaskBuilder()
                                                        .withTask(ASSESSMENT)
                                                        .build();
        assertTrue(uniqueReminderShowsTaskList.contains(editedReminderShowsTask));
    }

    @Test
    public void contains_reminderShowsTaskWithDifferentReminder_returnsFalse() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        ReminderShowsTask editedReminderShowsTask = new ReminderShowsTaskBuilder()
                                                        .withReminder(ASSIGNMENT_REMINDER)
                                                        .build();
        assertFalse(uniqueReminderShowsTaskList.contains(editedReminderShowsTask));
    }

    @Test
    public void contains_reminderShowsTaskWithDifferentTask_returnsFalse() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        ReminderShowsTask editedReminderShowsTask = new ReminderShowsTaskBuilder()
                                                        .withTask(BOOK_REPORT)
                                                        .build();
        assertFalse(uniqueReminderShowsTaskList.contains(editedReminderShowsTask));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderShowsTaskList.add(null));
    }

    @Test
    public void add_duplicateReminderShowsTask_throwsDuplicateTaskException() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        assertThrows(DuplicateReminderShowsTaskException.class, () -> uniqueReminderShowsTaskList
                                                                          .add(DEFAULT_REMINDER_SHOWS_TASK));
    }

    @Test
    public void setReminderShowsTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderShowsTaskList
                                                           .setReminderShowsTasks((List<ReminderShowsTask>) null));
    }

    @Test
    public void setReminderShowsTasks_replacesOwnListWithProvidedList() {
        uniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        List<ReminderShowsTask> reminderShowsTaskList = Arrays.asList(DEFAULT_REMINDER_SHOWS_TASK);
        uniqueReminderShowsTaskList.setReminderShowsTasks(reminderShowsTaskList);
        UniqueReminderShowsTaskList expectedUniqueReminderShowsTaskList = new UniqueReminderShowsTaskList();
        expectedUniqueReminderShowsTaskList.add(DEFAULT_REMINDER_SHOWS_TASK);
        assertEquals(expectedUniqueReminderShowsTaskList, uniqueReminderShowsTaskList);
    }

    @Test
    public void setReminders_listWithDuplicateReminderShowsTasks_throwsDuplicateReminderShowsTaskException() {
        List<ReminderShowsTask> listWithDuplicateReminderShowsTasks =
            Arrays.asList(DEFAULT_REMINDER_SHOWS_TASK, DEFAULT_REMINDER_SHOWS_TASK);
        assertThrows(DuplicateReminderShowsTaskException.class, () -> uniqueReminderShowsTaskList
                                                               .setReminderShowsTasks
                                                                    (listWithDuplicateReminderShowsTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> uniqueReminderShowsTaskList
                                                                   .asUnmodifiableObservableList()
                                                                   .remove(0));
    }

}
