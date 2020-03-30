package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITED;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Adds a new assignment to the Schoolwork Tracker.
 */
public class AddRestaurantCommand extends Command {
    public static final String COMMAND_WORD = "(rt)add";
    public static final String COMMAND_FUNCTION = "Adds a restaurant to the restaurant book.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + " Parameters: "
            + PREFIX_RESTAURANT + "RESTAURANT "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_VISITED + "VISITED "
            + "[" + PREFIX_OPERATING_HOURS + "OPERATING_HOURS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_CUISINE + "CUISINE] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_RESTAURANT + "Ameens "
            + PREFIX_LOCATION + "Clementi "
            + PREFIX_OPERATING_HOURS + "0900:2300 "
            + PREFIX_PRICE + "$ "
            + PREFIX_CUISINE + "Indian "
            + PREFIX_VISITED + "Yes";

    public static final String MESSAGE_SUCCESS = "New restaurant added: %1$s";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the restaurant book";

    private final Restaurant toAdd;

    /**
     * Creates an AddRestaurantCommand to add the specified {@code Restaurant}
     */
    public AddRestaurantCommand(Restaurant restaurant) {
        requireNonNull(restaurant);
        toAdd = restaurant;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasRestaurant(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }

        model.addRestaurant(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false,
                false, false, false, false, true, false);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
