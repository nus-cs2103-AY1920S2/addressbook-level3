package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "(rt)delete";
    public static final String COMMAND_FUNCTION = "Deletes the restaurant identified by the "
            + "index number used in the displayed restaurant list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_RESTAURANT_SUCCESS = "Deleted Restaurant: %1$s";

    private final Index targetIndex;

    public DeleteRestaurantCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRestaurant(restaurantToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_RESTAURANT_SUCCESS, restaurantToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRestaurantCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRestaurantCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
