package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.Parcel;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;
    Predicate<ReturnOrder> PREDICATE_SHOW_ALL_RETURNS = unused -> true;

    /** {@code Predicate} that evaluates true only when all the orders
     *  are delivered.
     */
    Predicate<Order> PREDICATE_SHOW_DONE_ORDERS = Order::isDelivered;
    Predicate<ReturnOrder> PREDICATE_SHOW_DONE_RETURNS = ReturnOrder::isDelivered;

    /** {@code Predicate} that evaluates true only when all the orders
     *  are not delivered.
     */
    Predicate<Order> PREDICATE_SHOW_UNDONE_ORDERS = order -> !order.isDelivered();
    Predicate<ReturnOrder> PREDICATE_SHOW_UNDONE_RETURNS = order -> !order.isDelivered();

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
     * Returns true if a parcel exist.
     * @return boolean representing the parcel existence.
     */
    boolean hasParcel(Parcel parcel);

    /**
     * Returns true if a Order with the same identity as {@code order} exists in the order book.
     * @param order
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
     * The order identity of {@code editedOrder} must not be the same as another existing order in the order book.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Used to mark the given order as delivered.
     * The order must exist in the order book.
     */
    void deliverOrder(Order target);

    /**
     * Returns the user prefs' return order book file path.
     */
    Path getReturnOrderBookFilePath();

    /**
     * Sets the user prefs' return order book file path.
     */
    void setReturnOrderBookFilePath(Path returnOrderBookFilePath);

    /**
     * Replaces return order book data with the data in {@code returnOrderBook}.
     */
    void setReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook);

    /** Returns the ReturnOrderBook */
    ReadOnlyReturnOrderBook getReturnOrderBook();

    /**
     * Returns true if a return order with the same identity as {@code returnOrder} exists in the return order book.
     */
    boolean hasReturnOrder(ReturnOrder returnOrder);

    /**
     * Deletes the given return order.
     * The return order must exist in the return order book.
     */
    void deleteReturnOrder(ReturnOrder target);

    /**
     * Adds the given return order.
     * {@code returnOrder} must not already exist in the return order book.
     */
    void addReturnOrder(ReturnOrder returnOrder);

    /**
     * Delivers the given return order.
     * {@code returnOrder} must exist in the return order book.
     */
    void deliverReturnOrder(ReturnOrder returnOrder);

    /**
     * Replaces the given order {@code target} with {@code editedReturnOrder}.
     * {@code target} must exist in the return order book.
     * The return order identity of {@code editedReturnOrder} must not be the same as another existing return order
     * in the return order book.
     */
    void setReturnOrder(ReturnOrder target, ReturnOrder editedReturnOrder);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    /** Returns an unmodifiable view of the filtered return order list */
    ObservableList<ReturnOrder> getFilteredReturnOrderList();

    /**
     * Updates the filter of the filtered return order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReturnOrderList(Predicate<ReturnOrder> predicate);
}
