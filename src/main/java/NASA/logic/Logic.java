package NASA.logic;

import NASA.model.ReadOnlyNasaBook;
import javafx.collections.ObservableList;
import NASA.commons.core.GuiSettings;
import NASA.logic.commands.CommandResult;
import NASA.logic.commands.exceptions.CommandException;
import NASA.logic.parser.exceptions.ParseException;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.module.Module;

import java.nio.file.Path;

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
     * Returns the AddressBook.
     *
     * @see NASA.model.Model#getNasaBook()
     */
    ReadOnlyNasaBook getNasaBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' address book file path.
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
