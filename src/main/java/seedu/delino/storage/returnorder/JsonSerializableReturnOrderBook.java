package seedu.delino.storage.returnorder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.delino.commons.exceptions.IllegalValueException;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.ReturnOrderBook;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author Exeexe93
/**
 * An Immutable OrderBook that is serializable to JSON format.
 */
@JsonRootName(value = "ReturnBook")
class JsonSerializableReturnOrderBook {
    public static final String MESSAGE_DUPLICATE_RETURN_ORDER = "Return order list contains duplicate return order(s).";
    private final List<JsonAdaptedReturnOrder> returnOrders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableOrderBook} with the given orders.
     */
    @JsonCreator
    public JsonSerializableReturnOrderBook(@JsonProperty("returnOrders") List<JsonAdaptedReturnOrder> returnOrders) {
        this.returnOrders.addAll(returnOrders);
    }

    /**
     * Converts a given {@code ReadOnlyReturnOrderBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReturnOrderBook}.
     */
    public JsonSerializableReturnOrderBook(ReadOnlyReturnOrderBook source) {
        returnOrders.addAll(source.getReturnOrderList().stream().map(JsonAdaptedReturnOrder::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this return order book into the model's {@code ReturnOrderBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReturnOrderBook toModelType() throws IllegalValueException {
        ReturnOrderBook returnOrderBook = new ReturnOrderBook();
        for (JsonAdaptedReturnOrder jsonAdaptedReturnOrder : returnOrders) {
            ReturnOrder returnOrder = jsonAdaptedReturnOrder.toModelType();
            if (returnOrderBook.hasReturnOrder(returnOrder)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RETURN_ORDER);
            }
            returnOrderBook.addReturnOrder(returnOrder);
        }
        return returnOrderBook;
    };
}
