package seedu.address.logic.commands.lesson;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.LessonAction;


/**
 * Represents a lesson command with hidden internal logic and the ability to be executed.
 */
public abstract class LessonCommand extends Command {

    public static final String ADD_FORMAT =
            String.format("%s %s %s MOD_CODE %s CLASS_TYPE %s DAY START_TIME END_TIME [%s VENUE]",
                    COMMAND_GROUP_CLASS, COMMAND_WORD_ADD,
                    PREFIX_MODULE_CODE, PREFIX_TYPE, PREFIX_AT, PREFIX_VENUE);

    public static final String DELETE_FORMAT = String.format("%s %s %s MOD_CODE %s CLASS_TYPE",
            COMMAND_GROUP_CLASS, COMMAND_WORD_DELETE, PREFIX_MODULE_CODE, PREFIX_TYPE);

    public static final String EDIT_FORMAT =
            String.format("%s %s %s MOD_CODE %s CLASS_TYPE [%s DAY START_TIME END_TIME] [%s VENUE] ",
                    COMMAND_GROUP_CLASS, COMMAND_WORD_EDIT,
                    PREFIX_MODULE_CODE, PREFIX_TYPE, PREFIX_AT, PREFIX_VENUE);

    public static final String FIND_FORMAT =
            String.format("%s %s [%s] [%s DAY]", COMMAND_GROUP_CLASS, COMMAND_WORD_FIND, PREFIX_NEXT, PREFIX_AT);

    public static final List<String> ALL_COMMAND_WORDS = List.of(
            COMMAND_WORD_ADD, COMMAND_WORD_EDIT, COMMAND_WORD_DELETE, COMMAND_WORD_FIND);

    public static final List<String> ALL_COMMAND_FORMATS = List.of(
            ADD_FORMAT, DELETE_FORMAT, EDIT_FORMAT, FIND_FORMAT);

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Updates the list for facilitators and tasks for viewing.
     */
    public static void updateList(Module module, Model model) {
        model.updateModule(Optional.of(module));
        ModuleCode moduleCode = module.getModuleCode();
        model.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(moduleCode.value));
        model.updateTaskListForModule(x -> x.getModuleCode().equals(moduleCode));
    }

    /**
     * Updates the list of action for undo and redo commands.
     */
    public static void updateAction(Lesson lesson, Lesson editedLesson, DoableActionType type, Model model) {
        if (editedLesson == null) {
            LessonAction lessonAction = new LessonAction(lesson, type);
            model.addAction(lessonAction);
        } else {
            LessonAction lessonAction = new LessonAction(lesson, editedLesson, type);
            model.addAction(lessonAction);
        }
    }
}
