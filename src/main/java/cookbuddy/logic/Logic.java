package cookbuddy.logic;

import java.nio.file.Path;

import cookbuddy.commons.core.GuiSettings;
import cookbuddy.logic.commands.CommandResult;
import cookbuddy.logic.commands.exceptions.CommandException;
import cookbuddy.logic.parser.exceptions.ParseException;
import cookbuddy.model.Model;
import cookbuddy.model.ReadOnlyRecipeBook;
import cookbuddy.model.recipe.Recipe;
import javafx.collections.ObservableList;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the RecipeBook.
     *
     * @see Model#getRecipeBook()
     */
    ReadOnlyRecipeBook getRecipeBook();

    /** Returns an unmodifiable view of the filtered list of recipes */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Returns the user prefs' recipe book file path.
     */
    Path getRecipeBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
