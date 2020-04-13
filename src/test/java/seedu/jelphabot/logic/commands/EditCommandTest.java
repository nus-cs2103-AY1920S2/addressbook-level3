package seedu.jelphabot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESC_ASSIGNMENT;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.VALID_TAG_GRADED;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.testutil.EditTaskDescriptorBuilder;
import seedu.jelphabot.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and
 * RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new JelphaBot(model.getJelphaBot()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getLastShownList().size());
        Task lastTask = model.getLastShownList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withDescription(VALID_DESCRIPTION_TUTORIAL).withTags(VALID_TAG_GRADED).build();

        EditCommand.EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL)
                .withTags(VALID_TAG_GRADED).build();
        EditCommand editCommand = new EditCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new JelphaBot(model.getJelphaBot()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new JelphaBot(model.getJelphaBot()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TASK,
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL).build()
        );

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new JelphaBot(model.getJelphaBot()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_TASK, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        // edit task in filtered list into a duplicate in task list
        Task taskInList = model.getJelphaBot().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_TASK,
            new EditTaskDescriptorBuilder(taskInList).build()
        );

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getLastShownList().size() + 1);
        EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but
     * smaller than size of task list
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_THIRD_TASK;
        // ensures that outOfBoundIndex is still in bounds of task list list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getJelphaBot().getTaskList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
            new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_TUTORIAL).build()
        );

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TASK, DESC_ASSIGNMENT);

        // same values -> returns true
        EditCommand.EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_ASSIGNMENT);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        /* different index -> returns false */
        assertNotEquals(standardCommand, new EditCommand(INDEX_SECOND_TASK, DESC_ASSIGNMENT));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditCommand(INDEX_FIRST_TASK, DESC_TUTORIAL));
    }

}
