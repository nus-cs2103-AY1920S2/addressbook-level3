package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.restaurant.LocationContainsKeywordsPredicate;
import seedu.address.model.restaurant.RNameContainsKeywordsPredicate;

/**
 * Finds and lists all restaurants in restaurant book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "(rt)find";
    public static final String COMMAND_FUNCTION = "Finds all restaurants whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all restaurants whose names contain "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + COMMAND_WORD + " [n/RESTAURANT_NAME] [l/LOCATION] \n"
            + "Example: " + COMMAND_WORD + " n/McDonalds ";

    private final RNameContainsKeywordsPredicate rNamePredicate;
    private final LocationContainsKeywordsPredicate locationPredicate;

    public FindRestaurantCommand(RNameContainsKeywordsPredicate rNamePredicate,
                       LocationContainsKeywordsPredicate locationPredicate) {
        // we split the different keywords into different predicates
        this.rNamePredicate = rNamePredicate;
        this.locationPredicate = locationPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (rNamePredicate.size() == 0 && locationPredicate.size() != 0) {
            model.updateFilteredRestaurantList(locationPredicate); // 01
        } else if (rNamePredicate.size() != 0 && locationPredicate.size() == 0) {
            model.updateFilteredRestaurantList(rNamePredicate); // 10
        } else if (rNamePredicate.size() != 0 && locationPredicate.size() != 0) {
            model.updateFilteredRestaurantList(rNamePredicate.and(locationPredicate)); // 11
        } // we don't do anything for the case 00

        int numRestaurants = model.getFilteredRestaurantList().size();
        String successMessage = Integer.toString(numRestaurants) + " restaurant(s) listed!";

        // We edit the commandResult so it shows the restaurant panel.
        return new CommandResult(
                String.format(successMessage, model.getFilteredPersonList().size()),
                false, false, false, false, false, false, true, false);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindRestaurantCommand // instanceof handles nulls
                && rNamePredicate.equals(((FindRestaurantCommand) other).rNamePredicate)
                && locationPredicate.equals(((FindRestaurantCommand) other).locationPredicate)); // state check
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
