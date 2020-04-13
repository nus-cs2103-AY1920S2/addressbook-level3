package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_ASSIGNMENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.scheduledAssignmentTask;
import static seedu.address.testutil.TypicalTasks.scheduledHomeworkTask;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.ScheduledTaskBuilder;

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void hasThisTask_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.hasThisTask(null));
    }

    @Test
    public void hasThisTask_taskNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.hasThisTask(scheduledAssignmentTask));
    }

    @Test
    public void hasThisTask_taskInList_returnsTrue() {
        uniqueTaskList.add(scheduledAssignmentTask);
        assertTrue(uniqueTaskList.hasThisTask(scheduledAssignmentTask));
    }

    @Test
    public void hasThisTask_taskWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(scheduledAssignmentTask);
        Task editedTask = new ScheduledTaskBuilder().withModuleCode(VALID_MODULE_CODE_CS2103T)
                .withDescription(VALID_TASK_DESCRIPTION_ASSIGNMENT).withTaskDateTime("01/04/2020").build();
        assertTrue(uniqueTaskList.hasThisTask(editedTask));
    }

    @Test
    public void add_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueTaskList.add(scheduledAssignmentTask);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.add(scheduledAssignmentTask));
    }

    @Test
    public void setTask_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(null, scheduledAssignmentTask));
    }

    @Test
    public void setTask_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTask(scheduledAssignmentTask, null));
    }

    @Test
    public void setTask_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList
                .setTask(scheduledAssignmentTask, scheduledAssignmentTask));
    }

    @Test
    public void setTask_editedTaskIsSameTask_success() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.setTask(scheduledAssignmentTask, scheduledAssignmentTask);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(scheduledAssignmentTask);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasSameIdentity_success() {
        uniqueTaskList.add(scheduledAssignmentTask);
        Task editedTask = new ScheduledTaskBuilder(scheduledAssignmentTask).withTaskDateTime("01/04/2020").build();
        uniqueTaskList.setTask(scheduledAssignmentTask, editedTask);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedTask);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasDifferentIdentity_success() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.setTask(scheduledAssignmentTask, scheduledHomeworkTask);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(scheduledHomeworkTask);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTask_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.add(scheduledHomeworkTask);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTask(scheduledAssignmentTask,
                scheduledHomeworkTask));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(TaskNotFoundException.class, () -> uniqueTaskList.remove(scheduledAssignmentTask));
    }

    @Test
    public void remove_existingFacilitator_removesFacilitator() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.remove(scheduledAssignmentTask);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void removeWithModuleCode_nullModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.removeWithModuleCode(null));
    }

    @Test
    public void removeModuleCode_taskWithModuleCode_removesFacilitator() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.removeWithModuleCode(new ModuleCode(VALID_MODULE_CODE_CS2103T));
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setModuleCode_nullTargetModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList
                .setModuleCode(null, new ModuleCode(VALID_MODULE_CODE_CS2103T)));
    }

    @Test
    public void setModuleCode_nullEditedModuleCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList
                .setModuleCode(new ModuleCode(VALID_MODULE_CODE_CS2103T), null));
    }

    @Test
    public void setModuleCode_noTaskWithModuleCode_success() {
        uniqueTaskList.add(scheduledHomeworkTask);
        uniqueTaskList.setModuleCode(
                new ModuleCode(VALID_MODULE_CODE_CS2103T), new ModuleCode(VALID_MODULE_CODE_CS2101));
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(scheduledHomeworkTask);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setModuleCode_taskWithModuleCodeExist_success() {
        uniqueTaskList.add(scheduledAssignmentTask);
        uniqueTaskList.setModuleCode(
                new ModuleCode(VALID_MODULE_CODE_CS2103T), new ModuleCode(VALID_MODULE_CODE_CS2101));

        Task expectedTask = new ScheduledTaskBuilder(scheduledAssignmentTask).withModuleCode(VALID_MODULE_CODE_CS2101)
                .build();
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(expectedTask);

        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setTasks_nullUniqueTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((UniqueTaskList) null));
    }

    @Test
    public void setTasks_uniqueTaskList_replacesOwnListWithProvidedUniqueTaskList() {
        uniqueTaskList.add(scheduledAssignmentTask);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(scheduledHomeworkTask);
        uniqueTaskList.setTasks(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setFacilitators_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setTasks((List<Task>) null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(scheduledAssignmentTask);
        List<Task> taskList = Collections.singletonList(scheduledHomeworkTask);
        uniqueTaskList.setTasks(taskList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(scheduledHomeworkTask);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setFacilitators_listWithDuplicateFacilitators_throwsDuplicateFacilitatorException() {
        List<Task> listWithDuplicateTasks = Arrays.asList(scheduledAssignmentTask, scheduledAssignmentTask);
        assertThrows(DuplicateTaskException.class, () -> uniqueTaskList.setTasks(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueTaskList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void getTaskList_emptyList_success() {
        ObservableList<Task> internalList = FXCollections.observableArrayList();
        assertEquals(internalList, uniqueTaskList.getTaskList());
    }

    @Test
    public void getTaskList_nonEmptyList_success() {
        ObservableList<Task> internalList = FXCollections.observableArrayList();
        internalList.add(scheduledAssignmentTask);
        uniqueTaskList.add(scheduledAssignmentTask);
        assertEquals(internalList, uniqueTaskList.getTaskList());
    }
}
