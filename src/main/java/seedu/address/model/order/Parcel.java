package seedu.address.model.order;

/**
 * The parcel class is used to abstract both orders and returns in the return book.
 */
public interface Parcel {
    TransactionId getTid();
    Name getName();
    Phone getPhone();
    Email getEmail();
    Address getAddress();
    TimeStamp getTimestamp();
    Warehouse getWarehouse();
    boolean isDelivered();
}
