package nasa.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.logic.parser.exceptions.ParseException;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.module.Module;

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
     * Returns the NasaBook.
     *
     * @see nasa.model.Model#getNasaBook()
     */
    ReadOnlyNasaBook getNasaBook();

    /** Returns an unmodifiable view of the filtered list of modules */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' NASA file path.
     */
    Path getNasaBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
