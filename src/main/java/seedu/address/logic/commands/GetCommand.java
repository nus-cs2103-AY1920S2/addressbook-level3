package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonExistPredicate;

/**
 * Lists a person in the address book to the user in detail.
 */
public class GetCommand extends Command {

    public static final String COMMAND_WORD = "(ab)get";
    public static final String COMMAND_FUNCTION = "Displays all information relating to the contact.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index index;

    /**
     * @param index of the person in the filtered person list to retrieve information
     */
    public GetCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToGet = lastShownList.get(index.getZeroBased());

        PersonExistPredicate personExistPredicate = new PersonExistPredicate(personToGet, model);
        model.updateFilteredPersonListResult(personExistPredicate, true);

        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonListResult().size()), false, false, true, false, false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetCommand // instanceof handles nulls
                && index.equals(((GetCommand) other).index)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
