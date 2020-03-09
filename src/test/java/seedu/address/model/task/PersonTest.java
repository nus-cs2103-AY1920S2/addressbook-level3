package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA1521;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.BOB;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.TaskBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> task.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameTask(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameTask(null));

        // different priority -> returns false
        Task editedAlice = new TaskBuilder(ALICE).withPriority(VALID_PRIORITY_TASK2).build();
        assertFalse(ALICE.isSameTask(editedAlice));

        // different name -> returns false
        editedAlice = new TaskBuilder(ALICE).withName(VALID_NAME_TASK2).build();
        assertFalse(ALICE.isSameTask(editedAlice));

        // same name, same priority, different attributes -> returns true
        editedAlice =
                new TaskBuilder(ALICE)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(ALICE.isSameTask(editedAlice));

        // same name, different attributes -> returns true
        editedAlice =
                new TaskBuilder(ALICE)
                        .withPriority(VALID_PRIORITY_TASK2)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(ALICE.isSameTask(editedAlice));

        // same name, same priority, different attributes -> returns true
        editedAlice =
                new TaskBuilder(ALICE)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(ALICE.isSameTask(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task aliceCopy = new TaskBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Task editedAlice = new TaskBuilder(ALICE).withName(VALID_NAME_TASK2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different priority -> returns false
        editedAlice = new TaskBuilder(ALICE).withPriority(VALID_PRIORITY_TASK2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new TaskBuilder(ALICE).withDescription(VALID_DESCRIPTION_TASK2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new TaskBuilder(ALICE).withTags(VALID_TAG_MA1521).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
