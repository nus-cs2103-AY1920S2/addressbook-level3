package seedu.foodiebot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.logic.parser.CliSyntax.PREFIX_FROM;

import seedu.foodiebot.model.FoodieBot;
import seedu.foodiebot.model.Model;

/** Get the directions to the canteen through a given location specified with prefix: /f. */
public class GoToCanteenCommand extends Command {
    public static final String COMMAND_WORD = "goto";


    public static final String MESSAGE_USAGE =
        COMMAND_WORD
            + ": Get the directions to the canteen. "
            + "Parameters: "
            + "CANTEEN_NAME "
            + "["
            + PREFIX_FROM
            + "NEAREST_BLOCK_NAME]...\n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + "deck "
            + PREFIX_FROM
            + "com1 ";

    public static final String MESSAGE_SUCCESS = "";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFoodieBot(new FoodieBot());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
