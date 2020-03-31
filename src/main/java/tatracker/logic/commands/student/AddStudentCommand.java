package tatracker.logic.commands.student;

import static java.util.Objects.requireNonNull;
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
import tatracker.logic.commands.CommandWords;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.parser.Prefix;
import tatracker.logic.parser.PrefixDictionary;
import tatracker.model.Model;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;

/**
 * Adds a student to the TA-Tracker.
 */
public class AddStudentCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.STUDENT,
            CommandWords.ADD_MODEL,
            "Adds a student into the given module group.",
            List.of(MATRIC, MODULE, GROUP, NAME),
            List.of(PHONE, EMAIL, RATING, TAG),
            MATRIC, MODULE, GROUP, NAME, PHONE, EMAIL, RATING, TAG
    );

    public static final String COMMAND_WORD = CommandWords.STUDENT + " " + CommandWords.ADD_MODEL;

    public static final List<Prefix> PARAMETERS = List.of(MATRIC, MODULE, GROUP, NAME);
    public static final List<Prefix> OPTIONALS = List.of(PHONE, EMAIL, RATING, TAG);

    public static final String INFO = "Adds a student into the given module group.";
    public static final String USAGE = PrefixDictionary.getPrefixesWithInfo(PARAMETERS, OPTIONALS);
    public static final String EXAMPLE = PrefixDictionary.getPrefixesWithExamples(
            MATRIC, MODULE, GROUP, NAME, PHONE, EMAIL, RATING, TAG);

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a student to the into the given module group.\n"
            + "Parameters:\n"
            + MATRIC + "MATRIC "
            + MODULE + "MODULE "
            + GROUP + "GROUP "
            + NAME + "NAME "
            + "[" + PHONE + "PHONE] "
            + "[" + EMAIL + "EMAIL] "
            + "[" + RATING + "RATING] "
            + "[" + TAG + "TAG]...\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + MATRIC + "A0181234G "
            + MODULE + "CS3243 "
            + GROUP + "G06 "
            + NAME + "John Doe "
            + PHONE + "98765432 "
            + EMAIL + "johnd@example.com "
            + RATING + "3 "
            + TAG + "friends "
            + TAG + "owesMoney";

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
