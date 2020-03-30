package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "(rt)list";
    public static final String COMMAND_FUNCTION = "Shows a list of all restaurants in the RestaurantBook.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":  " + COMMAND_FUNCTION + "\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all restaurants";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, false, true, false);
    }
}
