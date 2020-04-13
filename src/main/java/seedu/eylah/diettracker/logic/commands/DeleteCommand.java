package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.eylah.commons.core.Messages;
import seedu.eylah.commons.core.index.Index;
import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.food.Food;

/**
 * Deletes a food identified using it's displayed index from the food book.
 */
public class DeleteCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the food identified by the index number used in the displayed food list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FOOD_SUCCESS = "Deleted Food: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);
        List<Food> lastShownList = model.getFilteredFoodList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        Food foodToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteFood(foodToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_FOOD_SUCCESS, foodToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
