package seedu.address.logic.commands.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CS9000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DAY_AND_TIME_SUNDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_REC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_SEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TYPE_TUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_GEQ1000;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_HOME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalModManager.getTypicalModManager;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.EditLessonDescriptorBuilder;
import seedu.address.testutil.LessonBuilder;

public class LessonEditCommandTest {
    private Model model = new ModelManager(getTypicalModManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalModManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedLessonList_success() {
        Lesson editedLesson = new LessonBuilder().withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withLessonType(VALID_LESSON_TYPE_TUT)
                .withDayAndTime(VALID_DAY_AND_TIME_SUNDAY).withVenue(VALID_VENUE_HOME).build();
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand command = new LessonEditCommand(model.getLessons().get(0).getModuleCode(),
                INDEX_FIRST, descriptor);
        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);
        expectedModel.setLesson(model.getLessons().get(0), editedLesson);
        expectedModel.updateModule(expectedModel.findModule(new ModuleCode(VALID_MODULE_CODE_GEQ1000)));
        expectedModel.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(
                editedLesson.getModuleCode().value));
        expectedModel.updateTaskListForModule(x -> x.getModuleCode().equals(editedLesson.getModuleCode()));
        assertCommandSuccess(command, model, expectedMessage, CommandType.LESSON, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedLessonList_success() {
        Lesson editedLesson = new LessonBuilder().withModuleCode(VALID_MODULE_CODE_GEQ1000)
                .withDayAndTime(VALID_DAY_AND_TIME_SUNDAY).build();
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand command = new LessonEditCommand(model.getLessons().get(0).getModuleCode(),
                INDEX_FIRST, descriptor);
        String expectedMessage = String.format(LessonEditCommand.MESSAGE_EDIT_LESSON_SUCCESS, editedLesson);
        expectedModel.setLesson(model.getLessons().get(0), editedLesson);
        expectedModel.updateModule(expectedModel.findModule(new ModuleCode(VALID_MODULE_CODE_GEQ1000)));
        expectedModel.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(
                editedLesson.getModuleCode().value));
        expectedModel.updateTaskListForModule(x -> x.getModuleCode().equals(editedLesson.getModuleCode()));
        assertCommandSuccess(command, model, expectedMessage, CommandType.LESSON, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedLessonList_throwsException() {
        LessonEditCommand command = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), INDEX_FIRST,
                new LessonEditCommand.EditLessonDescriptor());
        assertCommandFailure(command, model, LessonEditCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_invalidIndexLessonList_throwsException() {
        Index outOfBound = Index.fromOneBased(model.getLessons().size() + 1);
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder()
                .withLessonType(VALID_LESSON_TYPE_REC).build();
        LessonEditCommand command = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T),
                outOfBound, descriptor);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_unchangedLessonLessonList_throwsException() {
        Lesson lesson = model.getLessons().get(INDEX_FIRST.getZeroBased());
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(lesson).build();
        LessonEditCommand command = new LessonEditCommand(model.getLessons().get(INDEX_FIRST.getZeroBased())
                .getModuleCode(), INDEX_FIRST, descriptor);
        assertCommandFailure(command, model, LessonEditCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_moduleDoesNotExistLessonList_throwsException() {
        Lesson editedLesson = new LessonBuilder().withModuleCode(INVALID_MODULE_CODE_CS9000).build();
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand command = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), INDEX_FIRST,
                descriptor);
        assertCommandFailure(command, model, LessonEditCommand.MESSAGE_INVALID_MODULE_CODE);
    }

    @Test
    public void equals() {
        Lesson editedLesson = new LessonBuilder().withModuleCode(VALID_MODULE_CODE_GEQ1000).build();
        LessonEditCommand.EditLessonDescriptor descriptor = new EditLessonDescriptorBuilder(editedLesson).build();
        final LessonEditCommand command = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T),
                INDEX_FIRST, descriptor);

        // same values should return true
        LessonEditCommand.EditLessonDescriptor copy = new EditLessonDescriptorBuilder(editedLesson).build();
        LessonEditCommand commandCopy = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T),
                INDEX_FIRST, copy);
        assertTrue(command.equals(commandCopy));

        // same object should return true
        assertTrue(command.equals(command));

        // different types should return false
        assertFalse(command.equals(1));

        // null should return false
        assertFalse(command.equals(null));

        // different module codes should return false
        LessonEditCommand anotherCommand = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_GEQ1000),
                INDEX_FIRST, descriptor);
        assertFalse(command.equals(anotherCommand));

        // different indices should return false
        anotherCommand = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), INDEX_SECOND, descriptor);
        assertFalse(command.equals(anotherCommand));

        // different descriptor should return false
        anotherCommand = new LessonEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), INDEX_FIRST,
                new EditLessonDescriptorBuilder().withLessonType(VALID_LESSON_TYPE_SEC).build());
        assertFalse(command.equals(anotherCommand));

    }
}
