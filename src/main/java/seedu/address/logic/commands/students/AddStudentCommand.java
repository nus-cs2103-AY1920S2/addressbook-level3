package seedu.address.logic.commands.students;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Adds a student to TAble.
 */
public class AddStudentCommand extends Command {

    public static final String COMMAND_WORD = "addStudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the list of students.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_MATRIC_NUMBER + "MATRICNUMBER "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_MATRIC_NUMBER + "A0111111X "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the list of students";
    public static final String MESSAGE_DUPLICATE_MATRIC_NUMBER =
            "This matric number already exists in TAble.";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email address already exists in TAble.";

    private final Student toAdd;

    /**
     * Creates an AddStudentCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }

        if (model.hasSameMatricNumber(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MATRIC_NUMBER);
        }

        if (model.hasSameEmail(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        model.addStudent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddStudentCommand // instanceof handles nulls
                && toAdd.equals(((AddStudentCommand) other).toAdd));
    }
}
