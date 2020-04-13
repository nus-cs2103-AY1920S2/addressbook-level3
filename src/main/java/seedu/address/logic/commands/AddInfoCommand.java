package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonExistPredicate;
import seedu.address.model.person.Remark;

/**
 * Adds a remark to an existing person in the address book.
 */
public class AddInfoCommand extends Command {

    public static final String COMMAND_WORD = "(ab)addnote";
    public static final String COMMAND_FUNCTION = "Stores the additional information of the person identified "
            + "by the index number used in the last person listing. "
            + "If there is existing information, input will be added as new information "
            + "on new line.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + " INFO...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_EMPTY = "No remark added to Person: %1$s";

    private final Index index;
    private final ArrayList<Remark> remarks;
    private ArrayList<Remark> newRemarks;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remarks of the person to be updated to
     */
    public AddInfoCommand(Index index, ArrayList<Remark> remarks) {
        requireAllNonNull(index, remarks);

        this.index = index;
        this.remarks = remarks;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        ArrayList<Remark> updatedRemarks = new ArrayList<>();
        for (Remark i : personToEdit.getRemark()) {
            updatedRemarks.add(i);
        }

        if (remarks.size() != 0) {
            for (int i = 0; i < remarks.size(); i++) {
                updatedRemarks.add(remarks.get(i));
            }
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), updatedRemarks,
                personToEdit.getBirthday(), personToEdit.getOrganization(), personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);

        PersonExistPredicate personExistPredicate = new PersonExistPredicate(editedPerson, model);
        model.updateFilteredPersonListResult(personExistPredicate);

        return new CommandResult(generateSuccessMessage(editedPerson),
                false, false, true, false, false, false, false, false);
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = (remarks.size() != 0) ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_EMPTY;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddInfoCommand)) {
            return false;
        }

        // state check
        AddInfoCommand e = (AddInfoCommand) other;
        return index.equals(e.index)
                && remarks.equals(e.remarks);
    }

    /**
     * Returns an unmodifiable remark set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code remarks} is null.
     */
    public Optional<ArrayList<Remark>> getRemarks() {
        return (newRemarks != null) ? Optional.of(newRemarks) : Optional.empty();
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
