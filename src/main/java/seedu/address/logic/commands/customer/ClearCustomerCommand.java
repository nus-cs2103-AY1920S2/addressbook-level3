package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCustomerCommand extends Command {

    public static final String COMMAND_WORD = "clearc";

    public static final String MESSAGE_SUCCESS = "Customer list has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setInventorySystem(new InventorySystem(), COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
