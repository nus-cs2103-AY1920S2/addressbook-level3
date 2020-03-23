package seedu.address.testutil;

import seedu.address.model.order.Order;
import seedu.address.model.returnorder.ReturnOrderBook;

/**
 * A utility class to help with building ReturnOrderbook objects.
 * Example usage: <br>
 *     {@code ReturnOrderBook ab = new ReturnOrderBookBuilder().withReturnOrder("John", "Doe").build();}
 */
public class ReturnOrderBookBuilder {

    private ReturnOrderBook orderBook;

    public ReturnOrderBookBuilder() {
        orderBook = new ReturnOrderBook();
    }

    public ReturnOrderBookBuilder(ReturnOrderBook orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Adds a new {@code Order} to the {@code OrderBook} that we are building.
     */
    public ReturnOrderBookBuilder withReturnOrder(Order order) {
        orderBook.addReturnOrder(order);
        return this;
    }

    public ReturnOrderBook build() {
        return orderBook;
    }
}
