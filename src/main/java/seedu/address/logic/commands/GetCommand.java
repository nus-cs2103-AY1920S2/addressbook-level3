package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists a person in the address book to the user in detail.
 */
public class GetCommand extends Command {

    public static final String COMMAND_WORD = "ab_get";
    public static final String COMMAND_FUNCTION = "Displays all information relating to the contact.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Listed person requested";

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
        model.getPerson(personToGet, index.getZeroBased());
        model.updateFilteredPerson(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToGet));
    }
}
