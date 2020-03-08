package fithelper.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import fithelper.commons.core.GuiSettings;
import fithelper.logic.commands.CommandResult;
import fithelper.logic.commands.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.entry.Entry;

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
     * Returns the FitHelper.
     *
     * @see fithelper.model.Model#getFitHelper()
     */
    ReadOnlyFitHelper getFitHelper();

    /** Returns an unmodifiable view of the filtered list of food entries*/
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered list of sports entries*/
    ObservableList<Entry> getFilteredSportsEntryList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getFitHelperFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
