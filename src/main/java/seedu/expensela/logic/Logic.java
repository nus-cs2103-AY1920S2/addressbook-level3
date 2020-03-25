package seedu.expensela.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.expensela.commons.core.GuiSettings;
import seedu.expensela.logic.commands.CommandResult;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.monthlydata.MonthlyData;
import seedu.expensela.model.transaction.Transaction;

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
     * Returns the ExpenseLa.
     *
     * @see seedu.expensela.model.Model#getExpenseLa()
     */
    ReadOnlyExpenseLa getExpenseLa();

    /** Returns an unmodifiable view of the filtered list of transaction */
    ObservableList<Transaction> getUnfilteredTransactionList();

    MonthlyData getMonthlyData();

    /**
     * Returns the user prefs' expensela file path.
     */
    Path getExpenseLaFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
