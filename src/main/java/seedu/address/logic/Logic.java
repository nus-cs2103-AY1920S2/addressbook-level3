package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.autocomplete.Autocomplete;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.history.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyFitBiz;
import seedu.address.model.client.Client;
import seedu.address.model.schedule.ScheduleDay;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FitBiz.
     *
     * @see seedu.address.model.Model#getFitBiz()
     */
    ReadOnlyFitBiz getFitBiz();

    /**
     * Returns an unmodifiable view of the filtered list of clients
     */
    ObservableList<Client> getFilteredClientList();

    /**
     * @author @Dban1
     * Returns all schedules across all clients according to the week day.
     *
     * @return
     */
    ObservableList<ScheduleDay> getScheduleDayList();

    /**
     * Returns the client required by {@code ViewCommand} from {@code ModelManager}.
     *
     * @author @yonggiee
     */
    Client getClientInView();

    /**
     * Returns true if there is a client in {@code clientInView} in
     * {@code ModelManager}.
     *
     * @author @yonggiee
     */
    Boolean hasClientInView();

    /**
     * Returns the user prefs' FitBiz file path.
     */
    Path getFitBizFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Opens the provided {@code url} in the user's default web browser.
     *
     * @param url website url to open
     */
    public void openUrlInDefaultWebBrowser(String url);

    /**
     * Returns the {@code CommandHistory} object.
     */
    CommandHistory getCommandHistory();

    /**
     * Returns the {@code Autocomplete} object.
     */
    Autocomplete getAutocomplete();

}
