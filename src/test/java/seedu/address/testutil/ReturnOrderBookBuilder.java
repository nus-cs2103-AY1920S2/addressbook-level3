package seedu.address.testutil;

import seedu.address.model.ReturnOrderBook;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

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
    public ReturnOrderBookBuilder withReturnOrder(ReturnOrder returnOrder) {
        orderBook.addReturnOrder(returnOrder);
        return this;
    }

    public ReturnOrderBook build() {
        return orderBook;
    }
}
