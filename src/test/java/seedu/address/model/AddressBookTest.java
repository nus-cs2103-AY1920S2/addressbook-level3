package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA1521;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.HOMEWORK10;
import static seedu.address.testutil.TypicalTasks.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Task editedAlice =
                new TaskBuilder(HOMEWORK10)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        List<Task> newPersons = Arrays.asList(HOMEWORK10, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicateTaskException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasTask_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTask(null));
    }

    @Test
    public void hasTask_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasTask(HOMEWORK10));
    }

    @Test
    public void hasTask_personInAddressBook_returnsTrue() {
        addressBook.addTask(HOMEWORK10);
        assertTrue(addressBook.hasTask(HOMEWORK10));
    }

    @Test
    public void hasTask_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addTask(HOMEWORK10);
        Task editedAlice =
                new TaskBuilder(HOMEWORK10)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(addressBook.hasTask(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> addressBook.getTaskList().remove(0));
    }

    /** A stub ReadOnlyAddressBook whose persons list can violate interface constraints. */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }
}
