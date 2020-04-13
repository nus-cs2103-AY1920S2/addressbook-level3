package seedu.jelphabot.model.task.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.jelphabot.testutil.TypicalTasks.TUTORIAL;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalTasks;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.exceptions.DuplicateTaskException;
import seedu.jelphabot.model.task.exceptions.TaskNotFoundException;
import seedu.jelphabot.testutil.TaskBuilder;

// @@author yaojiethng
public class GroupedTaskListTest {

    private final GroupedTaskListStub groupedTaskList = new GroupedTaskListStub();

    @Test
    void isEmpty_emptyList_returnsTrue() {
        assertTrue(groupedTaskList.isEmpty());

        groupedTaskList.setTasks(getTypicalTasks());
        assertFalse(groupedTaskList.isEmpty());

        groupedTaskList.clear();
        assertTrue(groupedTaskList.isEmpty());
    }

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(groupedTaskList.contains(ASSESSMENT));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        groupedTaskList.add(ASSESSMENT);
        assertTrue(groupedTaskList.contains(ASSESSMENT));
    }

    @Test
    public void contains_taskWithSameIdentityFieldsInList_returnsTrue() {
        groupedTaskList.add(ASSESSMENT);
        Task editedTask = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                              .build();
        assertTrue(groupedTaskList.contains(editedTask));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        groupedTaskList.add(ASSESSMENT);
        assertThrows(DuplicateTaskException.class, () -> groupedTaskList.add(ASSESSMENT));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.setTask(null, ASSESSMENT));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.setTask(ASSESSMENT, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> groupedTaskList.setTask(ASSESSMENT, ASSESSMENT));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        groupedTaskList.add(ASSESSMENT);
        groupedTaskList.setTask(ASSESSMENT, ASSESSMENT);
        GroupedTaskList expectedGroupedTaskList =
            new GroupedTaskListStub();
        expectedGroupedTaskList.add(ASSESSMENT);
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        groupedTaskList.add(ASSESSMENT);
        Task editedAlice = new TaskBuilder(ASSESSMENT).withTags(VALID_TAG_GRADED)
                               .build();
        groupedTaskList.setTask(ASSESSMENT, editedAlice);
        GroupedTaskList expectedGroupedTaskList = new GroupedTaskListStub();
        expectedGroupedTaskList.add(editedAlice);
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        groupedTaskList.add(ASSESSMENT);
        groupedTaskList.setTask(ASSESSMENT, TUTORIAL);
        GroupedTaskList expectedGroupedTaskList = new GroupedTaskListStub();
        expectedGroupedTaskList.add(TUTORIAL);
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        groupedTaskList.add(ASSESSMENT);
        groupedTaskList.add(TUTORIAL);
        assertThrows(DuplicateTaskException.class, () -> groupedTaskList.setTask(ASSESSMENT, TUTORIAL));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> groupedTaskList.remove(ASSESSMENT));
    }

    @Test
    public void remove_existingTask_removesTask() {
        groupedTaskList.add(ASSESSMENT);
        groupedTaskList.remove(ASSESSMENT);
        GroupedTaskList expectedGroupedTaskList = new GroupedTaskListStub();
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    @Test
    public void setTasks_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_nullTaskList_replacesOwnListWithProvidedGroupedTaskList() {
        groupedTaskList.add(ASSESSMENT);
        GroupedTaskList expectedGroupedTaskList =
            new GroupedTaskListStub();
        expectedGroupedTaskList.add(ASSIGNMENT);
        groupedTaskList.setTasks(expectedGroupedTaskList);
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    @Test
    public void setTasks_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupedTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        groupedTaskList.add(ASSESSMENT);
        List<Task> taskList = Collections.singletonList(TUTORIAL);
        groupedTaskList.setTasks(taskList);
        GroupedTaskList expectedGroupedTaskList =
            new GroupedTaskListStub();
        expectedGroupedTaskList.add(TUTORIAL);
        assertEquals(expectedGroupedTaskList, groupedTaskList);
    }

    private static class GroupedTaskListStub extends GroupedTaskList {
        protected GroupedTaskListStub() {
            super(FXCollections.observableArrayList(), new PinnedTaskListTest.PinnedTaskListStub());
        }

        @Override
        public Category getCategory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<SubgroupTaskList> getSublists() {
            return subLists;
        }

        @Override
        protected void setTasks(List<Task> taskList) {
            this.tasks.setAll(taskList);
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                       || (other instanceof GroupedTaskListStub // instanceof handles nulls
                               && tasks.equals(((GroupedTaskListStub) other).tasks));
        }
    }
}
