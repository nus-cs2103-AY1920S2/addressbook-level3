package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.eylah.commons.core.index.Index;
import seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException;
import seedu.eylah.expensesplitter.model.Entry;
import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.item.Item;
import seedu.eylah.expensesplitter.model.person.Amount;
import seedu.eylah.expensesplitter.model.person.Person;


/**
 * Deletes an item from the list of available items.
 */
public class DeleteItemCommand extends Command {

    public static final String COMMAND_WORD = "deleteitem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the item identified by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Item: %1$s";

    private final Index targetIndex;

    public DeleteItemCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Entry currentEntry = model.getEntry(targetIndex.getZeroBased());
        Item currentItem = currentEntry.getItem();
        Amount amountPerPerson = currentItem.getAmountPerPerson();
        ArrayList<Person> persons = currentEntry.getPersonsList();
        for (Person person : persons) {
            model.removeAmount(person, amountPerPerson);
        }
        model.deleteEntry(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteItemCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteItemCommand) other).targetIndex)); // state check
    }
}
