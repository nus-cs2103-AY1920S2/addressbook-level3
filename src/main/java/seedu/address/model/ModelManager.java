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

/**
 * Represents the in-memory model of the order book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final OrderBook orderBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Order> filteredOrders;

    /**
     * Initializes a ModelManager with the given orderBook and userPrefs.
     */
    public ModelManager(ReadOnlyOrderBook orderBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(orderBook, userPrefs);

        logger.fine("Initializing with order book: " + orderBook + " and user prefs " + userPrefs);

        this.orderBook = new OrderBook(orderBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrders = new FilteredList<>(this.orderBook.getOrderList());
    }

    public ModelManager() {
        this(new OrderBook(), new UserPrefs());
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
    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        userPrefs.setOrderBookFilePath(orderBookFilePath);
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
        updateFilteredOrderList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);
        orderBook.setOrder(target, editedOrder);
    }

    @Override
    public void deliverOrder(Order target) {
        orderBook.deliverOrder(target);
    }

    @Override
    public void renewDeliveryStatus(Order target) {
        target.setDeliveryStatus(false);
        orderBook.setDeliveryStatus(target);
    }

    //=========== Filtered Person List Accessors =============================================================

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
                && userPrefs.equals(other.userPrefs)
                && filteredOrders.equals(other.filteredOrders);
    }

}
