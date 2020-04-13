package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandDelete.DeleteAssignmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.manager.EdgeManager;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;

/**
 * Adds a ASSIGNMENT to the address book.
 */
public class AddAssignmentCommand extends AddCommand {

    public static final String COMMAND_WORD = "add-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to the address book. "
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_DEADLINE + "DEADLINE "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Adversarial Search Assignment 2 "
        + PREFIX_DEADLINE + "2020-12-30 "
        + PREFIX_TAG + "AI "
        + PREFIX_TAG + "Difficult ";

    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";

    private final Assignment toAdd;

    private Integer index;

    /**
     * Creates an AddCommand to add the specified {@code Assignment}
     */
    public AddAssignmentCommand(Assignment asgmt) {
        requireNonNull(asgmt);
        toAdd = asgmt;
    }

    public AddAssignmentCommand(Assignment asgmt, Integer index) {
        requireAllNonNull(asgmt, index);
        this.toAdd = asgmt;
        this.index = index;
    }

    protected void generateOppositeCommand() throws CommandException {
        oppositeCommand = new DeleteAssignmentCommand(this.toAdd);
    }

    @Override
    public CommandResult executeUndoableCommand(Model model) throws CommandException {
        requireNonNull(model);

        if (model.has(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
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
            || (other instanceof AddAssignmentCommand // instanceof handles nulls
            && toAdd.equals(((AddAssignmentCommand) other).toAdd));
    }
}
