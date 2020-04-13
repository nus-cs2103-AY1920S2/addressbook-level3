package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROGRAMMING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_FOURTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_ID_THIRD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.task.NonScheduledTask;
import seedu.address.model.task.ScheduledTask;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {
    public static ScheduledTask scheduledAssignmentTask = new ScheduledTaskBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2103T)
            .withDescription(VALID_TASK_DESCRIPTION_ASSIGNMENT)
            .withTaskDateTime("01/04/2020")
            .withTaskId(getIntegerTaskId(VALID_TASK_ID_FIRST))
            .setDone(false)
            .build();
    public static ScheduledTask scheduledHomeworkTask = new ScheduledTaskBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2101)
            .withDescription(VALID_TASK_DESCRIPTION_HOMEWORK)
            .withTaskDateTime("25/06/2020")
            .withTaskId(getIntegerTaskId(VALID_TASK_ID_SECOND))
            .setDone(false)
            .build();
    public static NonScheduledTask nonScheduledProgrammingTask = new NonScheduledTaskBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2103T)
            .withDescription(VALID_TASK_DESCRIPTION_PROGRAMMING)
            .withTaskId(getIntegerTaskId(VALID_TASK_ID_THIRD))
            .withIsDone(false)
            .build();
    public static NonScheduledTask nonScheduledProgrammingAssignmentTask = new NonScheduledTaskBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2101)
            .withDescription(VALID_TASK_DESCRIPTION_PROGRAMMING
                    + VALID_TASK_DESCRIPTION_ASSIGNMENT)
            .withTaskId(getIntegerTaskId(VALID_TASK_ID_FOURTH))
            .withIsDone(false)
            .build();

    private TypicalTasks() {}

    public static List<Task> getTypicalTasks() {
        return new ArrayList<Task>(Arrays.asList(scheduledAssignmentTask,
                scheduledHomeworkTask, nonScheduledProgrammingTask, nonScheduledProgrammingAssignmentTask));
    }

    public static int getIntegerTaskId(String taskId) {
        return Integer.parseInt(taskId);
    }
}

