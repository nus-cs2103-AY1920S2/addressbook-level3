package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.util.action.DoableActionType;

/**
 * Deletes a lesson identified using it's displayed index from Mod Manager.
 */
public class LessonDeleteCommand extends LessonCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CLASS + " " + COMMAND_WORD_DELETE
            + ": Deletes the class identified by the index number used in the displayed class list.\n"
            + "Parameters: INDEX (must be a positive number) " + PREFIX_MODULE_CODE + " MOD_CODE\n"
            + "Example: " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_DELETE
            + " 1 " + PREFIX_MODULE_CODE + " CS2103T\n";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Class: %1$s";

    public static final String MESSAGE_INVALID_MODULE_CODE = "Module code provided is invalid.";

    private final Index targetIndex;
    private ModuleCode moduleCode;

    public LessonDeleteCommand(Index targetIndex, ModuleCode moduleCode) {
        this.targetIndex = targetIndex;
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModuleCode(moduleCode.toString())) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }
        ObservableList<Lesson> lessons = model.getLessonListForModule(moduleCode);
        if (targetIndex.getZeroBased() >= lessons.size()) {
            throw new CommandException(MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Lesson lessonToDelete = lessons.get(targetIndex.getZeroBased());
        updateList(model.findModule(moduleCode).get(), model);
        model.removeLesson(lessonToDelete);
        updateAction(lessonToDelete, null, DoableActionType.DELETE, model);

        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonToDelete),
                CommandType.LESSON);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LessonDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((LessonDeleteCommand) other).targetIndex)); // state check
    }

}
