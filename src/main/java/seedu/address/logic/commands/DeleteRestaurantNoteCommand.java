package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_BAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_GOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINE_NUMBER_RECOMMENDED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Note;
import seedu.address.model.restaurant.Restaurant;

/**
 * Deletes a note of an existing restaurant in the restaurant book.
 */
public class DeleteRestaurantNoteCommand extends Command {
    public static final String COMMAND_WORD = "(rt)deletenote";
    public static final String COMMAND_FUNCTION = "Deletes the food note of the restaurant identified "
            + "by the index number used in the last restaurant listing.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX and LINE_NUMBER (must be a positive integer) "
            + "[" + PREFIX_LINE_NUMBER_RECOMMENDED + "LINE_NUMBER] "
            + "[" + PREFIX_LINE_NUMBER_GOOD + "LINE_NUMBER] "
            + "[" + PREFIX_LINE_NUMBER_BAD + "LINE_NUMBER]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINE_NUMBER_RECOMMENDED + "2 "
            + PREFIX_LINE_NUMBER_GOOD + "1 "
            + PREFIX_LINE_NUMBER_BAD + "3";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted notes for restaurant: %1$s";

    private final Index index;
    private final ArrayList<Integer> lineRec;
    private final ArrayList<Integer> lineGood;
    private final ArrayList<Integer> lineBad;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit the note
     * @param lineRec number of a specific note in the recommended food information stored
     * @param lineGood number of a specific note in the good food information stored
     * @param lineBad number of a specific note in the bad food information stored
     */
    public DeleteRestaurantNoteCommand(Index index, ArrayList<Integer> lineRec, ArrayList<Integer> lineGood,
                                       ArrayList<Integer> lineBad) {
        requireAllNonNull(index, lineRec, lineGood, lineBad);

        this.index = index;
        this.lineRec = lineRec;
        this.lineGood = lineGood;
        this.lineBad = lineBad;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToEdit = lastShownList.get(index.getZeroBased());
        ArrayList<Note> recNotes = new ArrayList<>();
        ArrayList<Note> goodNotes = new ArrayList<>();
        ArrayList<Note> badNotes = new ArrayList<>();

        if (lineRec.size() != 0) {
            if (restaurantToEdit.getRecommendedFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_RECOMMENDED_FOOD);
            }
            for (Integer i : lineRec) {
                if (i > restaurantToEdit.getRecommendedFood().size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_REC);
                }
                recNotes.add(restaurantToEdit.getRecommendedFood().get(i - 1));
            }
        }

        if (lineGood.size() != 0) {
            if (restaurantToEdit.getGoodFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_GOOD_FOOD);
            }
            for (Integer i : lineGood) {
                if (i > restaurantToEdit.getGoodFood().size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_GOOD);
                }
                goodNotes.add(restaurantToEdit.getGoodFood().get(i - 1));
            }
        }

        if (lineBad.size() != 0) {
            if (restaurantToEdit.getBadFood().size() == 0) {
                throw new CommandException(Messages.MESSAGE_NO_BAD_FOOD);
            }
            for (Integer i : lineBad) {
                if (i > restaurantToEdit.getBadFood().size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_LINE_NUMBER_BAD);
                }
                badNotes.add(restaurantToEdit.getBadFood().get(i - 1));
            }
        }

        for (Note n : recNotes) {
            restaurantToEdit.getRecommendedFood().remove(n);
        }
        for (Note n : goodNotes) {
            restaurantToEdit.getGoodFood().remove(n);
        }
        for (Note n : badNotes) {
            restaurantToEdit.getBadFood().remove(n);
        }

        Restaurant editedRestaurant = new Restaurant(restaurantToEdit.getName(), restaurantToEdit.getLocation(),
                restaurantToEdit.getHours(), restaurantToEdit.getPrice(), restaurantToEdit.getCuisine(),
                restaurantToEdit.getRemark(), restaurantToEdit.getVisit(), restaurantToEdit.getRecommendedFood(),
                restaurantToEdit.getGoodFood(), restaurantToEdit.getBadFood());

        model.setRestaurant(restaurantToEdit, editedRestaurant);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_NOTE_SUCCESS, editedRestaurant),
                false, false, false, false, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRestaurantNoteCommand)) {
            return false;
        }

        // state check
        DeleteRestaurantNoteCommand e = (DeleteRestaurantNoteCommand) other;
        return index.equals(e.index)
                && lineRec == e.lineRec
                && lineGood == e.lineGood
                && lineBad == e.lineBad;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
