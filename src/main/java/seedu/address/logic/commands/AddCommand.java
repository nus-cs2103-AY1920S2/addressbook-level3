package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.course.module.Module;

/**
 * Adds a profile to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a personal to the module. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_SEMESTER + "SEMESTER "
            + "(" + PREFIX_TASK + "TASK) "
            + "(" + PREFIX_DEADLINE + "DEADLINE) "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_SEMESTER + "4"
            + "(" + PREFIX_TASK + "assignment) "
            + "(" + PREFIX_DEADLINE + "2020-03-16 23:59) ";

    private static final String MESSAGE_SUCCESS = "New Personal Object added: %1$s";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Profile}
     */
    public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    public AddCommand(Module module, boolean isUpdated) {
        requireNonNull(module);
        toAdd = module;
        //MESSAGE_SUCCESS = "Existing module updated: %1$s";
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
