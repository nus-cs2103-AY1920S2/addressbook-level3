package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.predicates.DescriptionContainsKeywordsPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsCompletedPredicate;
import seedu.jelphabot.model.task.predicates.TaskIsIncompletePredicate;
import seedu.jelphabot.model.task.tasklist.GroupedTaskList;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;
import seedu.jelphabot.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_ASSIGNMENT = "Individual Assignment 1";
    public static final String VALID_DESCRIPTION_TUTORIAL = "Tutorial 3";
    public static final String VALID_STATUS_ASSIGNMENT = "INCOMPLETE";
    public static final String VALID_STATUS_TUTORIAL = "COMPLETE";
    public static final String VALID_DATETIME_ASSIGNMENT = "Jan-12-2020 22 00";
    public static final String VALID_DATETIME_TUTORIAL = "Mar-2-2019 23 59";
    public static final String VALID_MODULE_CODE_ASSIGNMENT = "CS2103T";
    public static final String VALID_MODULE_CODE_TUTORIAL = "CS2101";
    public static final String VALID_PRIORITY_ASSIGNMENT = "1";
    public static final String VALID_PRIORITY_TUTORIAL = "-1";
    public static final String VALID_TAG_GRADED = "graded";
    public static final String VALID_TAG_PROJECT = "project";
    public static final String VALID_ASSESSMENT_REMINDER_INDEX = "1";
    public static final String VALID_ASSESSMENT_REMINDER_HOUR = "1";
    public static final String VALID_ASSESSMENT_REMINDER_DAY = "1";
    public static final String VALID_BOOK_REPORT_REMINDER_INDEX = "2";
    public static final String VALID_BOOK_REPORT_REMINDER_HOUR = "2";
    public static final String VALID_BOOK_REPORT_REMINDER_DAY = "2";
    public static final String VALID_ASSIGNMENT_REMINDER_INDEX = "3";

    public static final String DESCRIPTION_DESC_ASSIGNMENT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ASSIGNMENT;
    public static final String DESCRIPTION_DESC_TUTORIAL = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_TUTORIAL;
    public static final String DATETIME_DESC_ASSIGNMENT = " " + PREFIX_DATETIME + VALID_DATETIME_ASSIGNMENT;
    public static final String DATETIME_DESC_TUTORIAL = " " + PREFIX_DATETIME + VALID_DATETIME_TUTORIAL;
    public static final String MODULE_CODE_DESC_ASSIGNMENT = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_ASSIGNMENT;
    public static final String MODULE_CODE_DESC_TUTORIAL = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_TUTORIAL;
    public static final String PRIORITY_DESC_ASSIGNMENT = " " + PREFIX_PRIORITY + VALID_PRIORITY_ASSIGNMENT;
    public static final String PRIORITY_DESC_TUTORIAL = " " + PREFIX_PRIORITY + VALID_PRIORITY_TUTORIAL;
    public static final String TAG_DESC_GRADED = " " + PREFIX_TAG + VALID_TAG_GRADED;
    public static final String TAG_DESC_PROJECT = " " + PREFIX_TAG + VALID_TAG_PROJECT;

    // '!&' not allowed in description
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "!Indivual &ssignment 1";
    // Inconsistent format + out of range
    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + "22-10/2020 33 59";
    // prefix requires at least 2 characters
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "C2103T";
    // priority only allows 1, 0, -1
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "-2";
    // empty tag not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "*";
    // reminder day negative
    public static final String INVALID_REMINDER_DAY = "-1";
    //reminder hour > 24
    public static final String INVALID_REMINDER_HOUR = "30";

    public static final String NEGATIVE_INDEX = "-1";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTaskDescriptor DESC_ASSIGNMENT;
    public static final EditCommand.EditTaskDescriptor DESC_TUTORIAL;

    static {
        DESC_ASSIGNMENT = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_ASSIGNMENT)
                .withDateTime(VALID_DATETIME_ASSIGNMENT)
                .withModuleCode(VALID_MODULE_CODE_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withTags(VALID_TAG_GRADED).build();
        DESC_TUTORIAL = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withDateTime(VALID_DATETIME_TUTORIAL)
                .withModuleCode(VALID_MODULE_CODE_TUTORIAL)
                .withPriority(VALID_PRIORITY_TUTORIAL)
                .withTags(VALID_TAG_PROJECT).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult}
     * <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to
     * {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that
     * takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the task list, filtered task list and selected task in
     * {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        JelphaBot expectedJelphaBot = new JelphaBot(actualModel.getJelphaBot());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedJelphaBot, actualModel.getJelphaBot());
        assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given
     * {@code targetIndex} in the {@code model}'s task list.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getLastShownList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getDescription().fullDescription.split("\\s+");
        model.updateFilteredTaskList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given
     * {@code targetIndex} in the {@code model}'s task list.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex, GroupedTaskList.Category category) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getGroupedTaskList(category).get(targetIndex.getZeroBased());
        final String[] splitName = task.getDescription().fullDescription.split("\\s+");
        model.updateFilteredTaskList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only tasks that are
     * of the specified {@code status}.
     */
    public static void showTasksWithSpecifiedStatus(Model model, Status status) {
        requireNonNull(model);
        ViewTaskList taskList = model.getLastShownList();
        //List<Task> taskList = model.getFilteredTaskList();
        List<Task> tasksWithPredicate = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            if (t.getStatus() == status) {
                tasksWithPredicate.add(t);
            }
        }
        // for (Task t: taskList) {
        //     if (t.getStatus() == status) {
        //         tasksWithPredicate.add(t);
        //     }
        // }

        Predicate<Task> predicate;

        if (status == Status.COMPLETE) {
            predicate = new TaskIsCompletedPredicate();
        } else {
            predicate = new TaskIsIncompletePredicate();
        }

        model.updateFilteredTaskList(predicate);

        assertEquals(tasksWithPredicate.size(), model.getFilteredTaskList().size());

    }
}
