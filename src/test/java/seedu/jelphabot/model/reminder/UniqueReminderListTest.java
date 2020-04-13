//@@author yapdianhao
package seedu.jelphabot.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalReminders.ASSIGNMENT_REMINDER;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.reminder.exceptions.DuplicateReminderException;
import seedu.jelphabot.testutil.ReminderBuilder;

public class UniqueReminderListTest {

    private final UniqueReminderList uniqueReminderList = new UniqueReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(uniqueReminderList.contains(ASSESSMENT_REMINDER));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        uniqueReminderList.add(ASSESSMENT_REMINDER);
        assertTrue(uniqueReminderList.contains(ASSESSMENT_REMINDER));
    }

    @Test
    public void contains_reminderWithSameReminderDayFieldsInList_returnsTrue() {
        uniqueReminderList.add(ASSESSMENT_REMINDER);
        Reminder editedReminder = new ReminderBuilder(ASSESSMENT_REMINDER).withReminderDay("4").build();
        assertTrue(uniqueReminderList.contains(editedReminder));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateTaskException() {
        uniqueReminderList.add(ASSESSMENT_REMINDER);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.add(ASSESSMENT_REMINDER));
    }

    @Test
    public void setReminders_nullUniqueReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((UniqueReminderList) null));
    }

    @Test
    public void setReminders_uniqueReminderList_replacesOwnListWithProvidedUniqueReminderList() {
        uniqueReminderList.add(ASSESSMENT_REMINDER);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(ASSIGNMENT_REMINDER);
        uniqueReminderList.setReminders(expectedUniqueReminderList);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((List<Reminder>) null));
    }

    @Test
    public void setReminders_list_replacesOwnListWithProvidedList() {
        uniqueReminderList.add(ASSESSMENT_REMINDER);
        List<Reminder> reminderList = Arrays.asList(ASSIGNMENT_REMINDER);
        uniqueReminderList.setReminders(reminderList);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(ASSIGNMENT_REMINDER);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_listWithDuplicateReminders_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(ASSESSMENT_REMINDER, ASSESSMENT_REMINDER);
        assertThrows(DuplicateReminderException.class, ()->uniqueReminderList
                                                               .setReminders(listWithDuplicateReminders));
    }

    @Test
    public void setReminders_listWithDuplicateReminderShowsTasks_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(ASSESSMENT_REMINDER, ASSESSMENT_REMINDER);
        assertThrows(DuplicateReminderException.class, ()->uniqueReminderList
                                                               .setReminders(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> uniqueReminderList
                                                                   .asUnmodifiableObservableList()
                                                                   .remove(0));
    }
}
