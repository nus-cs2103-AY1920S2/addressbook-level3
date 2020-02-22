package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOODWEIGHT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Food;

/**
 * Adds a person to the address book.
 */
public class AddFoodCommand extends Command {

    public static final String COMMAND_WORD = "addfood";

    public static final String MESSAGE_SUCCESS = "New food added: %1$s";
    public static final String MESSAGE_DUPLICATE_FOOD = "This food already exists in the list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a food to the list. "
            + "Parameters: "
            + PREFIX_FOODNAME + "NAME "
            + PREFIX_FOODTIME + "PHONE "
            + PREFIX_FOODLOCATION + "EMAIL "
            + PREFIX_FOODWEIGHT + "ADDRESS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FOODNAME + "Chicken rice "
            + PREFIX_FOODTIME + "tomorrow "
            + PREFIX_FOODLOCATION + "utown "
            + PREFIX_FOODWEIGHT + "200g ";

    private final Food toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Food}
     */
    public AddFoodCommand(Food food) {
        requireNonNull(food);
        toAdd = food;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addFood(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
