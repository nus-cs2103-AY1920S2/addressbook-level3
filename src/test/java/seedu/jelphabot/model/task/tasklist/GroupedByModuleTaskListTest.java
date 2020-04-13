package seedu.jelphabot.model.task.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_TUTORIAL;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.ASSIGNMENT;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.TaskBuilder;

// @@author yaojiethng
class GroupedByModuleTaskListTest {

    private final GroupedByModuleTaskList moduleTaskList =
        makeEmptyModuleTaskList();

    private static GroupedByModuleTaskList makeEmptyModuleTaskList() {
        return new GroupedByModuleTaskList(new PinnedTaskListTest.PinnedTaskListStub());
    }

    @Test
    public void add_taskWithDifferentModuleCode_newSublistCreated() {
        moduleTaskList.add(ASSESSMENT);
        assertEquals(moduleTaskList.size(), 1);
        assertEquals(moduleTaskList.getSublists().size(), 1);

        moduleTaskList.add(ASSIGNMENT);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 2);
    }

    @Test
    public void add_taskWithSameModuleCode_addedToSameSublist() {
        moduleTaskList.add(ASSESSMENT);
        assertEquals(moduleTaskList.size(), 1);
        assertEquals(moduleTaskList.getSublists().size(), 1);

        Task differentTask = new TaskBuilder(ASSESSMENT)
                                 .withDescription("A Different Assesment")
                                 .build();

        moduleTaskList.add(differentTask);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 1);
    }

    @Test
    public void setTask_editedTaskToDifferentModuleCode_newSublistCreated() {
        moduleTaskList.add(ASSIGNMENT);
        Task differentTask = new TaskBuilder(ASSIGNMENT)
                                 .withDescription("A different task")
                                 .build();
        moduleTaskList.add(differentTask);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 1);

        Task differentModule = new TaskBuilder(differentTask)
                                   .withModuleCode(VALID_MODULE_CODE_TUTORIAL)
                                   .build();

        moduleTaskList.setTask(differentTask, differentModule);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 2);
    }

    @Test
    public void setTask_editedTaskToSameModuleCode_addedToSameSublist() {
        moduleTaskList.add(ASSIGNMENT);
        moduleTaskList.add(ASSESSMENT);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 2);

        Task differentTask = new TaskBuilder(ASSESSMENT)
                                 .withModuleCode(VALID_MODULE_CODE_ASSIGNMENT)
                                 .build();

        moduleTaskList.setTask(ASSESSMENT, differentTask);
        assertEquals(moduleTaskList.size(), 2);
        assertEquals(moduleTaskList.getSublists().size(), 1);
    }
}
