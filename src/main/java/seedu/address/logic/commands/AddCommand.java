package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.course.module.PersonalModule;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a personal module to the module. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 ";

    public static final String MESSAGE_SUCCESS = "New PersonalModule added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final PersonalModule toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(PersonalModule personalModule) {
        requireNonNull(personalModule);
        toAdd = personalModule;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

//        if (model.hasPerson(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
