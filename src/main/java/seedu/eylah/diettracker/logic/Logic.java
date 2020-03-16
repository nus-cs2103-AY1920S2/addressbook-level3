package seedu.eylah.diettracker.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.eylah.commons.core.GuiSettings;
import seedu.eylah.commons.logic.MainLogic;
import seedu.eylah.diettracker.logic.commands.CommandResult;
import seedu.eylah.diettracker.logic.commands.exceptions.CommandException;
import seedu.eylah.diettracker.logic.parser.exceptions.ParseException;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.food.Food;

/**
 * API of the Logic component
 */
public interface Logic extends MainLogic {

    /**
     * Returns the FoodBook.
     *
     * @see seedu.eylah.diettracker.model.Model#getFoodBook()
     */
    ReadOnlyFoodBook getFoodBook();

    /** Returns an unmodifiable view of the filtered list of food */
    ObservableList<Food> getFilteredFoodList();

    /**
     * Returns the user prefs' food book file path.
     */
    Path getFoodBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
