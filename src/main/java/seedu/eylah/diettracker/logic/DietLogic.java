package seedu.eylah.diettracker.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.eylah.commons.logic.Logic;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyMyself;
import seedu.eylah.diettracker.model.food.Food;

/**
 * API of the Logic component
 */
public interface DietLogic extends Logic {

    @Override
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FoodBook.
     *
     * @see DietModel#getFoodBook()
     */
    ReadOnlyFoodBook getFoodBook();

    /** Returns an unmodifiable view of the filtered list of food */
    ObservableList<Food> getFilteredFoodList();

    /**
     * Returns the user prefs' food book file path.
     */
    Path getFoodBookFilePath();

    /**
     * Returns the Myself.
     *
     * @see DietModel#getMyself()
     */
    ReadOnlyMyself getMyself();

    /**
     * Returns the user prefs' myself file path.
     */
    Path getMyselfFilePath();

}
