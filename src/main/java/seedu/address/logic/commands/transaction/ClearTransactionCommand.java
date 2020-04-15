package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;

/**
 * Clears the transaction list.
 */
public class ClearTransactionCommand extends Command {

    public static final String COMMAND_WORD = "cleart";

    public static final String MESSAGE_SUCCESS = "Transaction list has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setInventorySystem(new InventorySystem(), COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

