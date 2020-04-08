package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_RECOMMENDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECOMMENDED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Restaurant;

/**
 * Edit a note of an existing restaurant in the restaurant book.
 */
public class EditRestaurantNoteCommand extends Command {

    public static final String COMMAND_WORD = "(rt)editnote";
    public static final String COMMAND_FUNCTION = "Edits the food note of the restaurant identified "
            + "by the index number used in the last restaurant listing. "
            + "If there is existing information at the line number, "
            + "the input will added on to the existing information.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX and LINE_NUMBER (must be a positive integer) "
            + "[" + PREFIX_LINE_NUMBER_RECOMMENDED + "LINE_NUMBER " + PREFIX_RECOMMENDED + "INFO] "
            + "[" + PREFIX_LINE_NUMBER_GOOD + "LINE_NUMBER " + PREFIX_GOOD + "INFO] "
            + "[" + PREFIX_LINE_NUMBER_BAD + "LINE_NUMBER " + PREFIX_BAD + "INFO]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE_NUMBER_RECOMMENDED + "2 " + PREFIX_RECOMMENDED + "Lobster pasta "
            + PREFIX_LINE_NUMBER_GOOD + "1 " + PREFIX_GOOD + "Mushroom soup "
            + PREFIX_LINE_NUMBER_BAD + "3 " + PREFIX_BAD + "Salad";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edited notes for restaurant: %1$s";
    public static final String MESSAGE_EMPTY_REC = "No recommended food to be edited is provided.";
    public static final String MESSAGE_EMPTY_GOOD = "No good food to be edited is provided.";
    public static final String MESSAGE_EMPTY_BAD = "No bad food to be edited is provided.";

    private final Index index;
    private final int lineRec;
    private final int lineGood;
    private final int lineBad;
    private final Note recommendedFood;
    private final Note goodFood;
    private final Note badFood;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit the note
     * @param lineRec number of a specific note in the recommended food information stored
     * @param lineGood number of a specific note in the good food information stored
     * @param lineBad number of a specific note in the bad food information stored
     * @param recommendedFood of the restaurant to be updated to
     * @param goodFood of the restaurant to be updated to
     * @param badFood of the restaurant to be updated to
     */
    public EditRestaurantNoteCommand(Index index, int lineRec, int lineGood,
                                     int lineBad, Note recommendedFood, Note goodFood, Note badFood) {
        requireAllNonNull(index, lineRec, lineGood, lineBad, recommendedFood, goodFood, badFood);

        this.index = index;
        this.lineRec = lineRec;
        this.lineGood = lineGood;
        this.lineBad = lineBad;
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

        if (lineRec != -1) { //Checks if recommended food is to be edited
            if (restaurantToEdit.getRecommendedFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_RECOMMENDED_FOOD);
            }
            if (lineRec > restaurantToEdit.getRecommendedFood().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_REC);
            }
        }
        if (lineGood != -1) { //Checks if good food is to be edited
            if (restaurantToEdit.getGoodFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_GOOD_FOOD);
            }
            if (lineGood > restaurantToEdit.getGoodFood().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_GOOD);
            }
        }
        if (lineBad != -1) { //Checks if bad food is to be edited
            if (restaurantToEdit.getBadFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_BAD_FOOD);
            }
            if (lineBad > restaurantToEdit.getBadFood().size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_BAD);
            }
        }

        if (lineRec != -1) {
            restaurantToEdit.getRecommendedFood().set(lineRec - 1, recommendedFood);
        }
        if (lineGood != -1) {
            restaurantToEdit.getGoodFood().set(lineGood - 1, goodFood);
        }
        if (lineBad != -1) {
            restaurantToEdit.getBadFood().set(lineBad - 1, badFood);
        }

        Restaurant editedRestaurant = new Restaurant(restaurantToEdit.getName(), restaurantToEdit.getLocation(),
                restaurantToEdit.getHours(), restaurantToEdit.getPrice(), restaurantToEdit.getCuisine(),
                restaurantToEdit.getRemark(), restaurantToEdit.getVisit(), restaurantToEdit.getRecommendedFood(),
                restaurantToEdit.getGoodFood(), restaurantToEdit.getBadFood());

        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_EDIT_NOTE_SUCCESS, editedRestaurant),
                false, false, false, false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRestaurantNoteCommand)) {
            return false;
        }

        // state check
        EditRestaurantNoteCommand e = (EditRestaurantNoteCommand) other;
        return index.equals(e.index)
                && recommendedFood.equals(e.recommendedFood)
                && lineRec == e.lineRec
                && goodFood.equals(e.goodFood)
                && lineGood == e.lineGood
                && badFood.equals(e.badFood)
                && lineBad == e.lineBad;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
