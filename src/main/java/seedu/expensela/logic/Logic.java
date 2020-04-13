package seedu.expensela.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.logic.commands.CommandResult;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.Balance;
import seedu.expensela.model.Filter;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ToggleView;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Get Command from command history
     * @param offset
     * @return Command String
     */
    String getCommandFromHistory(int offset);

    /**
     * Add Command to history
     * @param command
     */
    void addToCommandHistory(String command);

    /**
     * Delete command from history
     * @param command
     */
    void deleteFromCommandHistory(String command);

    int getCommandHistorySize();

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ExpenseLa.
     *
     * @see seedu.expensela.model.Model#getExpenseLa()
     */
    ReadOnlyExpenseLa getExpenseLa();

    /** Returns an unmodifiable view of the filtered list of transaction */
    ObservableList<Transaction> getFilteredTransactionList();

    MonthlyData getMonthlyData();

    void setMonthlyData(MonthlyData monthlyData);

    Filter getFilter();

    void setFilter(Filter filter);

    boolean getIsFilterMonth();

    ToggleView getToggleView();

    /**
     * Returns the user prefs' expensela file path.
     */
    Path getExpenseLaFilePath();

    /**
     * Returns the user prefs' expensela file path.
     */
    Path getGlobalDataFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get the total balance of the user
     */
    Balance getTotalBalance();

    /**
     * Set the total balance of the user
     */
    void setTotalBalance(Balance totalBalance);

}
