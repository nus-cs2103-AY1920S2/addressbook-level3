package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Inventory;
import seedu.address.model.Model;

/**
 * Removes all ingredients from the inventory
 */
public class InventoryClearCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All ingredients from inventory cleared!";
    public static final String MESSAGE_SUCCESS_EMPTY = "Your inventory is empty.";
    public static final String MESSAGE_USAGE = COMMAND_CATEGORY + " " + COMMAND_WORD
            + ": removes all ingredients from your inventory.\n"
            + "\nParameters: "
            + COMMAND_CATEGORY + " " + COMMAND_WORD;

    /**
     * Creates a InventoryClearCommand
     */
    public InventoryClearCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isEmpty = model.getInventory().getIngredientList().size() == 0;
        model.setInventory(new Inventory());
        return isEmpty ? new CommandResult(MESSAGE_SUCCESS_EMPTY) : new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof InventoryClearCommand;
    }

}
