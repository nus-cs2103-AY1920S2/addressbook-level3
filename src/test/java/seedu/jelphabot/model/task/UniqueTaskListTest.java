package seedu.jelphabot.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.jelphabot.testutil.TypicalTasks.TUTORIAL;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.exceptions.DuplicateTaskException;
import seedu.jelphabot.model.task.exceptions.TaskNotFoundException;
import seedu.jelphabot.testutil.TaskBuilder;

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(ASSESSMENT));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueTaskList.add(ASSESSMENT);
        assertTrue(uniqueTaskList.contains(ASSESSMENT));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(ASSESSMENT);
        Task editedAlice = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                               .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(ASSESSMENT);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(ASSESSMENT));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, ASSESSMENT));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(ASSESSMENT, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.setTask(ASSESSMENT, ASSESSMENT));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        uniqueTaskList.add(ASSESSMENT);
        uniqueTaskList.setTask(ASSESSMENT, ASSESSMENT);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(ASSESSMENT);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(ASSESSMENT);
        Task editedAlice = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                               .build();
        uniqueTaskList.setTask(ASSESSMENT, editedAlice);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedAlice);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(ASSESSMENT);
        uniqueTaskList.setTask(ASSESSMENT, TUTORIAL);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TUTORIAL);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(ASSESSMENT);
        uniqueTaskList.add(TUTORIAL);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(ASSESSMENT, TUTORIAL));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(ASSESSMENT));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueTaskList.add(ASSESSMENT);
        uniqueTaskList.remove(ASSESSMENT);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(ASSESSMENT);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(ASSIGNMENT);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(ASSESSMENT);
        List<Task> taskList = Arrays.asList(TUTORIAL);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(TUTORIAL);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(ASSESSMENT, ASSESSMENT);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTasks(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()-> uniqueTaskList
            .asUnmodifiableObservableList()
            .remove(0));
    }
}
