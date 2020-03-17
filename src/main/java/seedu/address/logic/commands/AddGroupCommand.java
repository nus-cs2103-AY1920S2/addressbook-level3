package seedu.address.logic.commands;

// import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds a student to the address book.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = String.format("%s %s", CommandWords.GROUP, CommandWords.ADD_MODEL);

    /* Example message usage. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a group into TA-Tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in TA-Tracker.";

    // private final Group toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    // public AddGroupCommand(Group group) {
    //     requireNonNull(group);
    //     toAdd = group;
    // }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // requireNonNull(model);
        //
        // if (model.hasPerson(toAdd)) {
        //     throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        // }
        //
        // model.addPerson(toAdd);
        // return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        throw new CommandException("Add group command not yet implemented!");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        // || (other instanceof AddGroupCommand // instanceof handles nulls
        // && toAdd.equals(((Group) other).toAdd));
    }
}
