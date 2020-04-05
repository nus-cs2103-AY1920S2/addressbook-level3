package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECOMMENDED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Restaurant;

/**
 * Adds a remark to an existing person in the address book.
 */
public class AddRestaurantNoteCommand extends Command {

    public static final String COMMAND_WORD = "(rt)addnote";
    public static final String COMMAND_FUNCTION = "Store the food information of the restaurant identified "
            + "by the index number used in the last restaurant listing. "
            + "If there is existing information, input will be added to the "
            + "existing information.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RECOMMENDED + " [RECOMMENDED_FOOD] "
            + PREFIX_GOOD + " [GOOD_FOOD] "
            + PREFIX_BAD + " [BAD_FOOD]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RECOMMENDED + "Chicken chop "
            + PREFIX_GOOD + "Truffle fries "
            + PREFIX_BAD + "Risotto";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Restaurant: %1$s";
    public static final String MESSAGE_EMPTY = "No note added to Restaurant: %1$s";

    private final Index index;
    private final Note recommendedFood;
    private final Note goodFood;
    private final Note badFood;

    /**
     *
     * @param index of the restaurant in the filtered restaurant list to edit the notes
     * @param recommendedFood notes of the restaurant to be updated to
     * @param goodFood notes of the restaurant to be updated to
     * @param badFood notes of the restaurant to be updated to
     */
    public AddRestaurantNoteCommand(Index index, Note recommendedFood, Note goodFood, Note badFood) {
        requireAllNonNull(index, recommendedFood, goodFood, badFood);

        this.index = index;
        this.recommendedFood = recommendedFood;
        this.goodFood = goodFood;
        this.badFood = badFood;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(index.getZeroBased());

        ArrayList<Note> updatedRecFood = new ArrayList<>();
        for (Note i : restaurantToEdit.getRecommendedFood()) {
            updatedRecFood.add(i);
        }

        ArrayList<Note> updatedGoodFood = new ArrayList<>();
        for (Note i : restaurantToEdit.getGoodFood()) {
            updatedGoodFood.add(i);
        }

        ArrayList<Note> updatedBadFood = new ArrayList<>();
        for (Note i : restaurantToEdit.getBadFood()) {
            updatedBadFood.add(i);
        }

        if (!recommendedFood.note.isEmpty()) {
            updatedRecFood.add(recommendedFood);
        }
        if (!goodFood.note.isEmpty()) {
            updatedGoodFood.add(goodFood);
        }
        if (!badFood.note.isEmpty()) {
            updatedBadFood.add(badFood);
        }

        Restaurant editedRestaurant = new Restaurant(restaurantToEdit.getName(), restaurantToEdit.getLocation(),
                restaurantToEdit.getHours(), restaurantToEdit.getPrice(), restaurantToEdit.getCuisine(),
                restaurantToEdit.getRemark(), restaurantToEdit.getVisit(), updatedRecFood,
                updatedGoodFood, updatedBadFood);

        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);

        return new CommandResult(String.format(generateSuccessMessage(editedRestaurant), editedRestaurant),
                false, false, false, false, false, false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Restaurant restaurantToEdit) {
        String message = (!recommendedFood.note.isEmpty() || !goodFood.note.isEmpty()
                || !badFood.note.isEmpty()) ? MESSAGE_ADD_NOTE_SUCCESS : MESSAGE_EMPTY;
        return String.format(message, restaurantToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRestaurantNoteCommand)) {
            return false;
        }

        // state check
        AddRestaurantNoteCommand e = (AddRestaurantNoteCommand) other;
        return index.equals(e.index)
                && recommendedFood.equals(e.recommendedFood)
                && goodFood.equals(e.goodFood)
                && badFood.equals(e.badFood);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
