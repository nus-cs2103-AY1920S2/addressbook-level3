//@@author yapdianhao
package seedu.jelphabot.model.task.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

public class ReminderPredicateTest {

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void equals() {
        ReminderPredicate reminderPredicate = new ReminderPredicate(
            model.getLastShownList(), model.getFilteredReminderList());
        ReminderPredicate reminderPredicateCopy = new ReminderPredicate(
            model.getLastShownList(), model.getFilteredReminderList());
        assertEquals(reminderPredicate, reminderPredicateCopy);

        assertNotEquals(reminderPredicate, null);

        List<Reminder> emptyReminderList = new ArrayList<Reminder>();
        ReminderPredicate emptyReminderPredicate = new ReminderPredicate(model.getLastShownList(), emptyReminderList);

        assertNotEquals(reminderPredicate, emptyReminderPredicate);

        emptyReminderList.add(ASSESSMENT_REMINDER);

        reminderPredicateCopy = new ReminderPredicate(model.getLastShownList(), emptyReminderList);
        assertEquals(reminderPredicateCopy, reminderPredicate);
    }

    @Test
    public void test_taskHasToBeReminded_returnsTrue() {
        ReminderPredicate reminderPredicate = new ReminderPredicate(
            model.getLastShownList(), model.getFilteredReminderList());
        assertTrue(reminderPredicate.test(ASSESSMENT));
    }

    @Test
    public void test_completedTaskNoNeedToBeReminded_returnsFalse() {
        ReminderPredicate reminderPredicate = new ReminderPredicate(
            model.getLastShownList(), model.getFilteredReminderList());
        Task completedTask = new TaskBuilder().withStatus("COMPLETE").build();
        assertFalse(reminderPredicate.test(completedTask));
    }

    @Test
    public void test_invalidTaskNoNeedToBeReminded_returnsTrue() {
        ReminderPredicate reminderPredicate = new ReminderPredicate(
            model.getLastShownList(), model.getFilteredReminderList());
        Task taskNotInList = new TaskBuilder().build();
        assertFalse(reminderPredicate.test(taskNotInList));
    }
}
