package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.Prefixes.PREFIX_GROUP;
import static tatracker.logic.parser.Prefixes.PREFIX_MATRIC;
import static tatracker.logic.parser.Prefixes.PREFIX_MODULE;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
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

    public static final String COMMAND_WORD = CommandWords.STUDENT + " " + CommandWords.DELETE_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student with the given matric number from the given module group.\n"
            + "Parameters:\n"
            + PREFIX_MATRIC + "MATRIC "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GROUP + "GROUP\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_MATRIC + "A0181234G "
            + PREFIX_MODULE + "CS3243 "
            + PREFIX_GROUP + "G06 ";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";
    public static final String MESSAGE_INVALID_MODULE_FORMAT =
            "There is no module with the given module code: %s";
    public static final String MESSAGE_INVALID_GROUP_FORMAT =
            "There is no group in the module (%s) with the given group code: %s";
    public static final String MESSAGE_INVALID_STUDENT_FORMAT =
            "There is no student in the (%s) group (%s) with the given matric number: %s";

    private final Matric toDelete;
    private final Group targetGroup;
    private final Module targetModule;

    public DeleteStudentCommand(Matric matric, Group group, Module module) {
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
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_FORMAT, targetModule.getIdentifier()));
        }

        if (!model.hasGroup(targetGroup, targetModule)) {
            throw new CommandException(String.format(MESSAGE_INVALID_GROUP_FORMAT,
                    targetModule.getIdentifier(),
                    targetGroup.getIdentifier()));
        }

        Module actualModule = model.getModule(targetModule.getIdentifier());
        Group actualGroup = actualModule.getGroup(targetGroup.getIdentifier());

        Student studentToDelete = actualGroup.getStudent(toDelete);

        // TODO: consider replacing has methods with id instead of actual objects
        if (studentToDelete == null) {
            throw new CommandException(String.format(MESSAGE_INVALID_STUDENT_FORMAT,
                    targetModule.getIdentifier(),
                    targetGroup.getIdentifier(),
                    toDelete));
        }

        model.deleteStudent(studentToDelete, targetGroup, targetModule);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete));
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
}
