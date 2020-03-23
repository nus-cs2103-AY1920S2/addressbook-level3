package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.order.Order;
import seedu.address.model.returnOrder.ReadOnlyReturnOrderBook;
import seedu.address.model.returnOrder.ReturnOrderBook;

/**
 * Represents the in-memory model of the order book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final OrderBook orderBook;
    private final ReturnOrderBook returnOrderBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<Order> filteredReturnOrders;

    /**
     * Initializes a ModelManager with the given orderBook and userPrefs.
     */
    public ModelManager(ReadOnlyOrderBook orderBook, ReadOnlyReturnOrderBook returnOrderBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(orderBook, returnOrderBook, userPrefs);

        logger.fine("Initializing with order book: " + orderBook + " and user prefs " + userPrefs);

        this.orderBook = new OrderBook(orderBook);
        this.returnOrderBook = new ReturnOrderBook(returnOrderBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrders = new FilteredList<>(this.orderBook.getOrderList());
        filteredReturnOrders = new FilteredList<>(this.returnOrderBook.getReturnOrderList());
    }

    public ModelManager() {
        this(new OrderBook(), new ReturnOrderBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getOrderBookFilePath() {
        return userPrefs.getOrderBookFilePath();
    }

    @Override
    public Path getReturnOrderBookFilePath() {
        return userPrefs.getReturnOrderBookFilePath();
    }

    @Override
    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        userPrefs.setOrderBookFilePath(orderBookFilePath);
    }

    @Override
    public void setReturnOrderBookFilePath(Path returnOrderBookFilePath) {
        requireNonNull(returnOrderBookFilePath);
        userPrefs.setReturnOrderBookFilePath(returnOrderBookFilePath);
    }

    //=========== OrderBook ================================================================================

    @Override
    public void setOrderBook(ReadOnlyOrderBook orderBook) {
        this.orderBook.resetData(orderBook);
    }

    @Override
    public ReadOnlyOrderBook getOrderBook() {
        return orderBook;
    }

    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orderBook.hasOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        orderBook.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        orderBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);
        orderBook.setOrder(target, editedOrder);
    }

    //=========== ReturnOrderBook ================================================================================

    @Override
    public void setReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) {
        this.returnOrderBook.resetData(returnOrderBook);
    }

    @Override
    public ReadOnlyReturnOrderBook getReturnOrderBook() {
        return returnOrderBook;
    }

    @Override
    public boolean hasReturnOrder(Order returnOrder) {
        requireNonNull(returnOrder);
        return returnOrderBook.hasReturnOrder(returnOrder);
    }

    @Override
    public void deleteReturnOrder(Order target) {
        returnOrderBook.removeReturnOrder(target);
    }

    @Override
    public void addReturnOrder(Order returnOrder) {
        returnOrderBook.addReturnOrder(returnOrder);
        updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setReturnOrder(Order target, Order editedReturnOrder) {
        requireAllNonNull(target, editedReturnOrder);

        returnOrderBook.setReturnOrder(target, editedReturnOrder);
    }

    //=========== Filtered Order List Accessors =============================================================
    @Override
    public void deliverOrder(Order target) {
        orderBook.deliverOrder(target);
    }

    @Override
    public void renewDeliveryStatus(Order target) {
        target.setDeliveryStatus(false);
        orderBook.setDeliveryStatus(target);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedOrderBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== Filtered Return Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedReturnOrderBook}
     */
    @Override
    public ObservableList<Order> getFilteredReturnOrderList() {
        return filteredReturnOrders;
    }

    @Override
    public void updateFilteredReturnOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredReturnOrders.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return orderBook.equals(other.orderBook)
                && returnOrderBook.equals(other.returnOrderBook)
                && userPrefs.equals(other.userPrefs)
                && filteredOrders.equals(other.filteredOrders)
                && filteredReturnOrders.equals(other.filteredReturnOrders);
    }

}
