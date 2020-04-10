package seedu.delino.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.delino.commons.core.GuiSettings;
import seedu.delino.logic.commands.CommandResult;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.exceptions.ParseException;
import seedu.delino.model.ReadOnlyOrderBook;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

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
     * Returns the OrderBook.
     *
     * @see seedu.delino.model.Model#getOrderBook()
     */
    ReadOnlyOrderBook getOrderBook();

    /**
     * Returns the ReturnOrderBook.
     *
     * @see seedu.delino.model.Model#getReturnOrderBook()
     */
    ReadOnlyReturnOrderBook getReturnOrderBook();

    /** Returns an unmodifiable view of the filtered list of orders */
    ObservableList<Order> getFilteredOrderList();

    /** Returns an unmodifiable view of the filtered list of return orders */
    ObservableList<ReturnOrder> getFilteredReturnOrderList();

    /**
     * Returns the user prefs' order book file path.
     */
    Path getOrderBookFilePath();

    /**
     * Returns the user prefs' return order book file path.
     */
    Path getReturnOrderBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get start up messages when Delino start up.
     */
    List<String> getStartUpMessages();
}
