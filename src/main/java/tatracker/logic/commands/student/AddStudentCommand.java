// @@author potatocombat

package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_DUPLICATE_STUDENT;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.student.Student;

/**
 * Adds a student to the TA-Tracker.
 */
public class AddStudentCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.ADD_MODEL,
            "Adds a student into the given module group",
            List.of(MATRIC, MODULE, GROUP, NAME),
            List.of(PHONE, EMAIL, RATING, TAG),
            MATRIC, MODULE, GROUP, NAME, PHONE, EMAIL, RATING, TAG
    );

    public static final String MESSAGE_ADD_STUDENT_SUCCESS = "New student added: %s (%s)\nIn %s [%s]";

    private final Student toAdd;

    private final String targetGroup;
    private final String targetModule;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student, String group, String module) {
        requireNonNull(student);
        requireNonNull(group);
        requireNonNull(module);
        toAdd = student;
        targetGroup = group;
        targetModule = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (!model.hasGroup(targetGroup, targetModule)) {
            throw new CommandException(MESSAGE_INVALID_GROUP_CODE);
        }

        if (model.hasStudent(toAdd.getMatric(), targetGroup, targetModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addStudent(toAdd, targetGroup, targetModule);

        model.updateFilteredGroupList(targetModule);
        model.updateFilteredStudentList(targetGroup, targetModule);

        return new CommandResult(getSuccessMessage(toAdd), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof AddStudentCommand)) {
            return false; // instanceof handles nulls
        }

        AddStudentCommand otherCommand = (AddStudentCommand) other;
        return toAdd.equals(otherCommand.toAdd)
                && targetGroup.equals(otherCommand.targetGroup)
                && targetModule.equals(otherCommand.targetModule);
    }

    private String getSuccessMessage(Student student) {
        return String.format(MESSAGE_ADD_STUDENT_SUCCESS,
                student.getName(),
                student.getMatric(),
                targetModule,
                targetGroup);
    }
}
