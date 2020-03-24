package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.model.Model;
import seedu.eylah.diettracker.model.food.Food;

/**
 * Sets the Mode of the Diet Tracker depending on user preferences. The 3 modes available are
 * -l for weight loss, -g for weight gain, and -m for maintaining weight.
 */
public class ModeCommand extends Command {

    public static final String COMMAND_WORD = "mode";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the mode in which you want to use the diet app "
            + "for tracking. "
            + "Parameters: [-l] [-g] [-m]";

    public static final String MESSAGE_SUCCESS = "New Food Added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the food book";

    private final Food toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public ModeCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFood(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FOOD);
        }

        model.addFood(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModeCommand // instanceof handles nulls
                && toAdd.equals(((ModeCommand) other).toAdd));
    }
}
