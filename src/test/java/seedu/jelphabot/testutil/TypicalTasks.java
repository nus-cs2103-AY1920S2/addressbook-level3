// @@author Clouddoggo

package seedu.jelphabot.testutil;

import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DATETIME_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DATETIME_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_MODULE_CODE_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_STATUS_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task ASSESSMENT =
        new TaskBuilder().withDescription("Online Midterm Assessment")
            .withDateTime("Feb-1-2020 11 00")
            .withModuleCode("MA1101R")
            .withPriority("HIGH")
            .withTags("school").build();
    public static final Task BOOK_REPORT = new TaskBuilder().withDescription("Book Report 1")
                                               .withStatus("COMPLETE")
                                               .withModuleCode("GES1028")
                                               .withDateTime("Feb-1-2020 11 00")
                                               .withTags("project", "school").build();
    public static final Task CLASS = new TaskBuilder()
                                         .withDescription("Yoga class")
                                         .withDateTime("Feb-1-2020 11 00")
                                         .withModuleCode("YG1011").build();
    public static final Task DATE = new TaskBuilder().withDescription("Daniel Meier")
                                        .withDateTime("Feb-1-2020 11 00")
                                        .withModuleCode("DT1101").build();
    public static final Task ERRAND = new TaskBuilder().withDescription("Milk errand")
                                          .withModuleCode("MYB1101")
                                          .withDateTime("Feb-1-2020 11 00").build();
    public static final Task FINALS = new TaskBuilder().withDescription("Open book finals")
                                          .withDateTime("Feb-1-2020 11 00")
                                          .withModuleCode("CS3230")
                                          .withTags("school").build();
    public static final Task GROUP_WORK = new TaskBuilder().withDescription("Group project meeting")
                                              .withDateTime("Feb-1-2020 11 00")
                                              .withModuleCode("CS2103T")
                                              .withTags("project", "school").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task ASSIGNMENT = new TaskBuilder()
                                              .withDescription(VALID_DESCRIPTION_ASSIGNMENT)
                                              .withStatus(VALID_STATUS_ASSIGNMENT)
                                              .withDateTime(VALID_DATETIME_ASSIGNMENT)
                                              .withModuleCode(VALID_MODULE_CODE_ASSIGNMENT)
                                              .withPriority(VALID_PRIORITY_ASSIGNMENT)
                                              .withTags(VALID_TAG_GRADED).build();

    public static final Task TUTORIAL = new TaskBuilder()
                                            .withDescription(VALID_DESCRIPTION_TUTORIAL)
                                            .withDateTime(VALID_DATETIME_TUTORIAL)
                                            .withModuleCode(VALID_MODULE_CODE_TUTORIAL)
                                            .build();

    private TypicalTasks() {
    } // prevents instantiation

    /**
     * Returns an {@code JelphaBot} with all the typical tasks.
     */
    public static JelphaBot getTypicalJelphaBot() {
        JelphaBot ab = new JelphaBot();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        for (Reminder reminder : TypicalReminders.getTypicalReminders()) {
            ab.addReminder(reminder);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(ASSESSMENT, BOOK_REPORT, CLASS, DATE, ERRAND, FINALS, GROUP_WORK));
    }
}
