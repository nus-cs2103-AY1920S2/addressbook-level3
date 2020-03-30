package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Visit;

/**
 * Marks an existing assignment in the scheduler as done.
 */
public class VisitedRestaurantCommand extends Command {
    public static final String COMMAND_WORD = "(rt)visited";
    public static final String COMMAND_FUNCTION = "Updates the visit status of the restaurant identified. ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "by the index number used in the displayed restaurant list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UPDATE_STATUS_SUCCESS =
            "The visit status of %1$s have been updated!";
    public static final String MESSAGE_ALREADY_VISITED =
            "You have already visited this restaurant!";

    private final Index index;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     */
    public VisitedRestaurantCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToUpdate = lastShownList.get(index.getZeroBased());
        Restaurant updatedRestaurant = createUpdatedRestaurant(restaurantToUpdate);

        if (restaurantToUpdate.getVisit().toString().equals(Visit.RESTAURANT_VISITED)) {
            throw new CommandException(MESSAGE_ALREADY_VISITED);
        }

        model.setRestaurant(restaurantToUpdate, updatedRestaurant);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, updatedRestaurant.getName()));
    }

    /**
     * Creates and returns a {@code Assignment} with the details of {@code assignmentToUpdate}.
     */
    private static Restaurant createUpdatedRestaurant(Restaurant restaurantToUpdate) {
        assert restaurantToUpdate != null;

        return new Restaurant(restaurantToUpdate.getName(), restaurantToUpdate.getLocation(),
                restaurantToUpdate.getHours(), restaurantToUpdate.getPrice(),
                restaurantToUpdate.getCuisine(), restaurantToUpdate.getRemark(),
                new Visit(Visit.RESTAURANT_VISITED), restaurantToUpdate.getRecommendedFood(),
                restaurantToUpdate.getGoodFood(), restaurantToUpdate.getBadFood());
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
