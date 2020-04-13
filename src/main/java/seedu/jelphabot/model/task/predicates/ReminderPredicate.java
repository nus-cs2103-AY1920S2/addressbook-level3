//@@author yapdianhao
package seedu.jelphabot.model.task.predicates;

import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Tests that a {@code Task}'s {@code DateTime} is due within a week from now and has not been done.
 */
public class ReminderPredicate extends TaskIsIncompletePredicate {

    private final LocalDate currDate = LocalDate.now();
    private final LocalTime currTime = LocalTime.now();
    private final LocalDateTime currDateTime = LocalDateTime.now();

    private final ViewTaskList taskList;
    private final List<Reminder> reminderList;

    public ReminderPredicate(ViewTaskList taskList, List<Reminder> reminderList) {
        requireAllNonNull(taskList, reminderList);
        this.taskList = taskList;
        this.reminderList = reminderList;
    }

    @Override
    public boolean test(Task task) {
        int reminderKey = -1;
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).equals(task)) {
                reminderKey = i;
                break;
            }
        }
        Reminder correspondingReminder = null;
        for (Reminder reminder : reminderList) {
            if (reminder.getIndex().getZeroBased() == reminderKey) {
                correspondingReminder = reminder;
                break;
            }
        }
        if (correspondingReminder == null) {
            return false;
        } else {
            return shouldBeReminded(task, correspondingReminder) && super.test(task);
        }
    }

    /**
     * A task should be reminded if it is before the specified day by the reminder, or within the specified hour.
     * @param task
     * @param reminder
     * @return boolean
     */
    public boolean shouldBeReminded(Task task, Reminder reminder) {
        LocalDateTime taskDateTime = task.getDateTime().getDateTime();
        if (taskDateTime.isBefore(currDateTime)) {
            return true;
        }
        if (taskDateTime.minusDays(reminder.getDaysToRemind().getReminderDay())
                .isBefore(currDateTime)) {
            return true;
        } else {
            return taskDateTime.minusHours(reminder.getHoursToRemind().getReminderHour()).isBefore(currDateTime);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof ReminderPredicate)
                          && this.taskList.equals(((ReminderPredicate) other).taskList)
                          && this.reminderList.equals(((ReminderPredicate) other).reminderList);
    }
}
