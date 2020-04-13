// @@author potatocombat

package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_GROUP_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_MODULE_CODE;
import static tatracker.commons.core.Messages.MESSAGE_INVALID_STUDENT;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Matric;
import tatracker.model.student.Student;

/**
 * Deletes a student identified using it's displayed index from the TA-Tracker.
 */
public class DeleteStudentCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.DELETE_MODEL,
            "Deletes the student with the given matric number from the given module group",
            List.of(MATRIC, MODULE, GROUP),
            List.of(),
            MATRIC, MODULE, GROUP
    );

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted student: %s (%s)\nIn %s [%s]";

    private final Matric toDelete;
    private final String targetGroup;
    private final String targetModule;

    public DeleteStudentCommand(Matric matric, String group, String module) {
        requireNonNull(matric);
        requireNonNull(group);
        requireNonNull(module);
        this.toDelete = matric;
        this.targetGroup = group;
        this.targetModule = module;
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

        Module actualModule = model.getModule(targetModule);
        Group actualGroup = actualModule.getGroup(targetGroup);

        Student studentToDelete = actualGroup.getStudent(toDelete);

        if (studentToDelete == null) {
            throw new CommandException(MESSAGE_INVALID_STUDENT);
        }

        model.deleteStudent(studentToDelete, targetGroup, targetModule);

        model.updateFilteredGroupList(targetModule);
        model.updateFilteredStudentList(targetGroup, targetModule);

        return new CommandResult(getSuccessMessage(studentToDelete), Action.GOTO_STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false; // instanceof handles nulls
        }

        DeleteStudentCommand otherCommand = (DeleteStudentCommand) other;
        return toDelete.equals(otherCommand.toDelete)
                && targetGroup.equals(otherCommand.targetGroup)
                && targetModule.equals(otherCommand.targetModule);
    }

    private String getSuccessMessage(Student student) {
        return String.format(MESSAGE_DELETE_STUDENT_SUCCESS,
                student.getName(),
                student.getMatric(),
                targetModule,
                targetGroup);
    }
}
