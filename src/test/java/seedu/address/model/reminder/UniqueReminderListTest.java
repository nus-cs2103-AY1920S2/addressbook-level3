package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.getReminder1;
import static seedu.address.testutil.TypicalReminders.getReminder2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;

public class UniqueReminderListTest {
    private Reminder reminder1 = getReminder1();
    private Reminder reminder2 = getReminder2();
    private UniqueReminderList uniqueReminderList = new UniqueReminderList();

    @Test
    public void contains_emptyReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnFalse() {
        assertFalse(uniqueReminderList.contains(reminder1));
    }

    @Test
    public void contains_reminderInList_returnTrue() {
        uniqueReminderList.add(reminder1);
        assertTrue(uniqueReminderList.contains(reminder1));
    }

    @Test
    public void contains_reminderWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReminderList.add(reminder1);
        Reminder editedReminder1 = new ReminderBuilder(reminder1).withDone("true")
                .build();
        assertTrue(uniqueReminderList.contains(editedReminder1));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateReminderException() {
        uniqueReminderList.add(reminder1);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.add(reminder1));
    }

    @Test
    public void setReminder_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(null, reminder1));
    }

    @Test
    public void setReminder_nullEditedReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminder(reminder1, null));
    }

    @Test
    public void setReminder_targetReminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.setReminder(reminder1, reminder1));
    }

    @Test
    public void setReminder_editedReminderIsSameReminder_success() {
        uniqueReminderList.add(reminder1);
        uniqueReminderList.setReminder(reminder1, reminder1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(reminder1);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasSameIdentity_success() {
        uniqueReminderList.add(reminder1);
        Reminder editedReminder1 = new ReminderBuilder(reminder1).withDone("true")
                .build();
        uniqueReminderList.setReminder(reminder1, editedReminder1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(editedReminder1);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasDifferentIdentity_success() {
        uniqueReminderList.add(reminder1);
        uniqueReminderList.setReminder(reminder1, reminder2);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(reminder2);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminder_editedReminderHasNonUniqueIdentity_throwsDuplicateReminderException() {
        uniqueReminderList.add(reminder1);
        uniqueReminderList.add(reminder2);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.setReminder(reminder1, reminder2));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.remove(null));
    }

    @Test
    public void remove_reminderDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.remove(reminder1));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        uniqueReminderList.add(reminder1);
        uniqueReminderList.remove(reminder1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullUniqueReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((UniqueReminderList) null));
    }

    @Test
    public void setReminders_uniqueReminderList_replacesOwnListWithProvidedUniqueReminderList() {
        uniqueReminderList.add(reminder1);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(reminder2);
        uniqueReminderList.setReminders(expectedUniqueReminderList);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((List<Reminder>) null));
    }

    @Test
    public void setReminders_list_replacesOwnListWithProvidedList() {
        uniqueReminderList.add(reminder1);
        List<Reminder> reminderList = Collections.singletonList(reminder2);
        uniqueReminderList.setReminders(reminderList);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(reminder2);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_listWithDuplicateReminders_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(reminder1, reminder1);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList
                .setReminders(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueReminderList.asUnmodifiableObservableList().remove(0));
    }
}
