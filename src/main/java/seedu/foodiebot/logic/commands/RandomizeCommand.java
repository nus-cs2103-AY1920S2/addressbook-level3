package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.Model;

/** Randomize a food item for the user based on index. */
public class RandomizeCommand extends Command {
    public static final String COMMAND_WORD = "randomize";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generate a random food option.";

    public static final String MESSAGE_SUCCESS = "Here are the choices: ";



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFoodieBot(new FoodieBot());
        return new CommandResult(COMMAND_WORD, MESSAGE_SUCCESS);
    }
}
