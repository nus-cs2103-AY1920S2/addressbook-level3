package tatracker.logic.commands.student;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;

import static java.util.Objects.requireNonNull;
import static tatracker.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tatracker.logic.parser.CliSyntax.PREFIX_GROUP;
import static tatracker.logic.parser.CliSyntax.PREFIX_MATRIC;
import static tatracker.logic.parser.CliSyntax.PREFIX_MODULE;
import static tatracker.logic.parser.CliSyntax.PREFIX_NAME;
import static tatracker.logic.parser.CliSyntax.PREFIX_PHONE;
import static tatracker.logic.parser.CliSyntax.PREFIX_RATING;
import static tatracker.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Adds a student to the TA-Tracker.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = CommandWords.STUDENT + " " + CommandWords.ADD_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a student to the into the given module group.\n"
            + "Parameters:\n"
            + PREFIX_MATRIC + "MATRIC "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_GROUP + "GROUP "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_MATRIC + "A0181234G "
            + PREFIX_MODULE + "CS3243 "
            + PREFIX_GROUP + "G06 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_RATING + "3 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New student added: %s\n To Module: %s\n To Group: %s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the TA-Tracker";
    public static final String MESSAGE_INVALID_MODULE_FORMAT = "There is no module with the given module code: %s";
    public static final String MESSAGE_INVALID_GROUP_FORMAT =
            "There is no group in the module (%s) with the given group code: %s";

    private final Student toAdd;

    private final Group targetGroup;
    private final Module targetModule;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student, Group group, Module module) {
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
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE_FORMAT, targetModule.getIdentifier()));
        }

        if (!model.hasGroup(targetGroup, targetModule)) {
            throw new CommandException(String.format(MESSAGE_INVALID_GROUP_FORMAT,
                            targetModule.getIdentifier(),
                            targetGroup.getIdentifier()));
        }

        if (model.hasStudent(toAdd, targetGroup, targetModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        model.addStudent(toAdd, targetGroup, targetModule);

        model.updateFilteredGroupList(targetModule.getIdentifier());
        model.updateFilteredStudentList(targetGroup.getIdentifier(), targetModule.getIdentifier());

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd, targetModule, targetGroup));
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
}
