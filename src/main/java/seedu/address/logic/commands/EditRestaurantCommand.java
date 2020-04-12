package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITED;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditRestaurantDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;

/**
 * Edits the details of an existing restaurant in the restaurant book.
 */
public class EditRestaurantCommand extends Command {

    public static final String COMMAND_WORD = "(rt)edit";
    public static final String COMMAND_FUNCTION = "Edits the details of the restaurant identified "
            + "by the index number used in the displayed restaurant list. "
            + "Existing values will be overwritten by the input values.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_RESTAURANT + "RESTAURANT] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "[" + PREFIX_VISITED + "VISITED] "
            + "[" + PREFIX_OPERATING_HOURS + "OPERATING_HOURS] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_CUISINE + "CUISINE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OPERATING_HOURS + "0700:2200 ";

    public static final String MESSAGE_EDIT_RESTAURANT_SUCCESS = "Edited Restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the restaurant book.";

    private final Index index;
    private final EditRestaurantDescriptor editRestaurantDescriptor;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     * @param editRestaurantDescriptor details to edit the restaurant with
     */
    public EditRestaurantCommand(Index index, EditRestaurantDescriptor editRestaurantDescriptor) {
        requireNonNull(index);
        requireNonNull(editRestaurantDescriptor);

        this.index = index;
        this.editRestaurantDescriptor = new EditRestaurantDescriptor(editRestaurantDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(index.getZeroBased());
        Restaurant editedRestaurant = editRestaurantDescriptor.createEditedRestaurant(restaurantToEdit);

        if (!restaurantToEdit.isSameRestaurant(editedRestaurant) && model.hasRestaurant(editedRestaurant)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESTAURANT);
        }


        model.setRestaurant(restaurantToEdit, editedRestaurant);

        return new CommandResult(String.format(MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant),
                false, false, false, false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRestaurantCommand)) {
            return false;
        }

        // state check
        EditRestaurantCommand e = (EditRestaurantCommand) other;
        return index.equals(e.index)
                && editRestaurantDescriptor.equals(e.editRestaurantDescriptor);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
