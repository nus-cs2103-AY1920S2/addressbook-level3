package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TASK2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA1521;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.ALICE;
import static seedu.address.testutil.TypicalTasks.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        assertTrue(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        Task editedAlice =
                new TaskBuilder(ALICE)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setTask(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setTask(ALICE, ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(ALICE);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTaskList.add(ALICE);
        Task editedAlice =
                new TaskBuilder(ALICE)
                        .withDescription(VALID_DESCRIPTION_TASK2)
                        .withTags(VALID_TAG_MA1521)
                        .build();
        uniqueTaskList.setTask(ALICE, editedAlice);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setTask(ALICE, BOB);
        UniqueTaskList expectedUniquePersonList = new UniqueTaskList();
        expectedUniquePersonList.add(BOB);
        assertEquals(expectedUniquePersonList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.add(BOB);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.remove(ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTaskList.add(ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(BOB);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(ALICE);
        List<Task> taskList = Collections.singletonList(BOB);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(BOB);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Task> listWithDuplicateTask = Arrays.asList(ALICE, ALICE);
        assertThrows(
                DuplicateTaskException.class, () -> uniqueTaskList.setTasks(listWithDuplicateTask));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }
}
