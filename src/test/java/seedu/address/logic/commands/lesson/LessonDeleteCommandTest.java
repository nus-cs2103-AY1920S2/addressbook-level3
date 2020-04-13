package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CS9000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.lesson.LessonDeleteCommand.MESSAGE_INVALID_MODULE_CODE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.ModuleCode;

public class LessonDeleteCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_invalidModuleCode_throwsException() {
        LessonDeleteCommand command = new LessonDeleteCommand(INDEX_FIRST, new ModuleCode(INVALID_MODULE_CODE_CS9000));
        assertCommandFailure(command, model, MESSAGE_INVALID_MODULE_CODE);
    }

    @Test
    public void execute_invalidIndexLessonList_throwsCommandException() {
        Index outOfBound = Index.fromOneBased(model.getLessons().size() + 1);
        LessonDeleteCommand command = new LessonDeleteCommand(outOfBound, new ModuleCode(VALID_MODULE_CODE_CS2103T));
        assertCommandFailure(command, model, MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validModuleCodeLessonList_success() throws CommandException {
        Lesson lessonToDelete = model.getLessons().get(INDEX_FIRST.getZeroBased());
        LessonDeleteCommand command = new LessonDeleteCommand(INDEX_FIRST, lessonToDelete.getModuleCode());
        String expectedMessage = String.format(LessonDeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                lessonToDelete);
        expectedModel.removeLesson(lessonToDelete);
        CommandResult result = command.execute(model);
        assertEquals(model.getLessons(), expectedModel.getLessons());
        assertEquals(result.getFeedbackToUser(), expectedMessage);
    }

    @Test
    public void execute_invalidModuleCodeLessonList_throwsCommandException() {
        LessonDeleteCommand command = new LessonDeleteCommand(INDEX_FIRST, new ModuleCode(INVALID_MODULE_CODE_CS9000));
        assertCommandFailure(command, model, MESSAGE_INVALID_MODULE_CODE);
    }

    @Test
    public void equals() {
        LessonDeleteCommand deleteFirstCommand = new LessonDeleteCommand(INDEX_FIRST,
                new ModuleCode(VALID_MODULE_CODE_CS2103T));
        LessonDeleteCommand deleteSecondCommand = new LessonDeleteCommand(INDEX_SECOND,
                new ModuleCode(VALID_MODULE_CODE_CS2103T));

        // same object should return true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values should return true
        LessonDeleteCommand deleteFirstCommandCopy = new LessonDeleteCommand(INDEX_FIRST,
                new ModuleCode(VALID_MODULE_CODE_CS2103T));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different type should return false
        assertFalse(deleteFirstCommand.equals(1));

        // null should return false
        assertFalse(deleteFirstCommand.equals(null));

        // different command should return false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
