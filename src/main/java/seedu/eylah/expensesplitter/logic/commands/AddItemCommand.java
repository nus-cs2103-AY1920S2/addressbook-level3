package seedu.eylah.expensesplitter.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import seedu.eylah.expensesplitter.logic.commands.exceptions.CommandException;
import seedu.eylah.expensesplitter.model.Entry;
import seedu.eylah.expensesplitter.model.Model;
import seedu.eylah.expensesplitter.model.item.Item;


/**
 * Used to add items to the receipt.
 */
public class AddItemCommand extends Command {

    public static final String COMMAND_WORD = "additem";

    public static final String MESSAGE_SUCCESS = "The item %1$s has been added.";

    private Entry toBeAdded;

    /**
     * Creates an AddItemCommand to add the specified {@code Item}
     *
     * @param item Item to be added.
     * @param persons String array of persons to be added.
     */
    public AddItemCommand(Item item, String ... persons) {
        requireAllNonNull(item, persons);
        toBeAdded = new Entry(item, persons);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addEntry(toBeAdded);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBeAdded));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddItemCommand // instanceof handles nulls
                && toBeAdded.equals(((AddItemCommand) other).toBeAdded));
    }

}
