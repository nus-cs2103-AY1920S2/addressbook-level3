package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.order.Order;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' order book file path.
     */
    Path getOrderBookFilePath();

    /**
     * Sets the user prefs' order book file path.
     */
    void setOrderBookFilePath(Path orderBookFilePath);

    /**
     * Replaces order book data with the data in {@code orderBook}.
     */
    void setOrderBook(ReadOnlyOrderBook orderBook);

    /** Returns the OrderBook */
    ReadOnlyOrderBook getOrderBook();

    /**
     * Returns true if a person with the same identity as {@code order} exists in the order book.
     */
    boolean hasOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the order book.
     */
    void deleteOrder(Order target);

    /**
     * Adds the given order.
     * {@code order} must not already exist in the order book.
     */
    void addOrder(Order order);

    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the order book.
     * The person identity of {@code editedOrder} must not be the same as another existing order in the order book.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Used to mark the given order as delivered.
     * The order must exist in the order book.
     */
    void deliverOrder(Order target);

    /**
     * Used to mark a given order as undelivered.
     * The order must exist in the order book.
     */
    void renewDeliveryStatus(Order target);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);
}
