package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_CALORIES;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_NAME;
import static seedu.eylah.diettracker.logic.parser.DietCliSyntax.PREFIX_TAG;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.food.Food;

/**
 * Adds a food to the food book.
 */
public class AddCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the food book. "
            + "Parameters: "
            + PREFIX_NAME + " NAME "
            + PREFIX_CALORIES + " CALORIES "
            + PREFIX_TAG + " [TAGS] ";

    public static final String MESSAGE_SUCCESS = "New Food Added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the food book";

    private final Food toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public AddCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
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
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
