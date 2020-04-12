package seedu.address.logic.commands.commandAdd;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandDelete.DeleteStudentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelStudent.Student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a student to the address book.
 */
public class AddStudentCommand extends AddCommand {

    public static final String COMMAND_WORD = "add-student";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GENDER + "GENDER "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John AppleSeed "
            + PREFIX_GENDER + "m "
            + PREFIX_TAG + "Old "
            + PREFIX_TAG + "Lazy";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";

    private final Student toAdd;
    private Integer index;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddStudentCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    public AddStudentCommand(Student student, Integer index) {
        requireAllNonNull(student, index);
        this.toAdd = student;
        this.index = index;
    }

    protected void generateOppositeCommand() {
        oppositeCommand = new DeleteStudentCommand(this.toAdd);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);
        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        if (index == null) {
            model.add(toAdd);
        } else {
            model.addAtIndex(toAdd, index);
        }
        EdgeManager.revokeEdgesFromDeleteEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddStudentCommand // instanceof handles nulls
                && toAdd.equals(((AddStudentCommand) other).toAdd));
    }
}
